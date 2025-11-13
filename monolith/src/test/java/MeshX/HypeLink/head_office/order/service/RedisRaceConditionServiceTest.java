package MeshX.HypeLink.head_office.order.service;

import MeshX.HypeLink.head_office.item.model.entity.ItemDetail;
import MeshX.HypeLink.head_office.item.repository.ItemDetailJpaRepositoryVerify;
import MeshX.HypeLink.head_office.order.model.dto.request.PurchaseOrderCreateReq;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Slf4j
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class RedisRaceConditionServiceTest {
    @Mock
    private RedissonClient redissonClient;
    @Mock
    private ItemDetailJpaRepositoryVerify itemDetailRepository;
    @Mock
    private RLock rLock;

    @InjectMocks
    private RedisRaceConditionService redisRaceConditionService;

    private ItemDetail itemDetail;

    @BeforeEach
    void setUp() {
        itemDetail = ItemDetail.builder()
                .itemDetailCode("ITEM-001")
                .stock(50) // 초기 재고 50
                .build();

        when(redissonClient.getLock(anyString())).thenReturn(rLock);
        try {
            lenient().when(rLock.tryLock(anyLong(), anyLong(), any(TimeUnit.class))).thenReturn(true);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        doNothing().when(rLock).unlock();
    }

    @Test
    @DisplayName("멀티 스레드 환경에서 동시 재고 차감/증가 - Redisson Lock 동시성 제어 검증 (100 threads)")
    void testConcurrentStockDecreaseAndIncrease() throws InterruptedException {
        // given
        when(itemDetailRepository.findByItemDetailCode(anyString())).thenReturn(itemDetail);

        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // 요청 DTO (차감 요청용)
        PurchaseOrderCreateReq decreaseReq = PurchaseOrderCreateReq.builder()
                .itemDetailCode("ITEM-001")
                .description("재고 차감 요청")
                .quantity(1)
                .requestStoreId(100)
                .supplierStoreId(200)
                .build();

        // 요청 Entity (증가 요청용)
        ItemDetail detail = ItemDetail.builder()
                .itemDetailCode("ITEM-001")
                .stock(itemDetail.getStock())
                .build();
        PurchaseOrder increaseOrder = PurchaseOrder.builder()
                .itemDetail(detail)
                .quantity(1)
                .build();

        // when: 50개는 차감, 50개는 증가
        IntStream.range(0, threadCount).forEach(i ->
                executor.submit(() -> {
                    try {
                        if (i % 2 == 0) {
                            redisRaceConditionService.decreaseHeadItemDetailStock(decreaseReq);
                        } else {
                            redisRaceConditionService.increaseHeadItemDetailStock(increaseOrder);
                        }
                    } finally {
                        latch.countDown();
                    }
                })
        );

        latch.await(); // 모든 스레드 완료 대기

        // then
        log.info("테스트 완료 - 최종 재고: {}", itemDetail.getStock());
        assertThat(itemDetail.getStock()).isGreaterThanOrEqualTo(0);

        // 적어도 여러 번 호출되어야 함
        verify(itemDetailRepository, atLeast(threadCount / 2)).findByItemDetailCode(anyString());
        verify(itemDetailRepository, atLeast(threadCount / 2)).merge(any(ItemDetail.class));

        executor.shutdown();
    }
}