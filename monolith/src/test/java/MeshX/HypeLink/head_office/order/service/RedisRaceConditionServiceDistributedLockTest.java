package MeshX.HypeLink.head_office.order.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RedisClusterStockBalanceTest {

    private RedissonClient redissonClient;
    private final Map<String, AtomicInteger> stockMap = new ConcurrentHashMap<>();

    @BeforeAll
    void setUpRedisClient() {
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress(
                        "redis://192.0.11.160:6379",
                        "redis://192.0.11.161:6379",
                        "redis://192.0.11.162:6379"
                )
                .setPassword("qwer1234")
                .setScanInterval(2000)
                .setCheckSlotsCoverage(false)
                .setMasterConnectionPoolSize(32)
                .setSlaveConnectionPoolSize(32)
                .setConnectTimeout(5000)
                .setTimeout(10000);

        redissonClient = Redisson.create(config);

        // 초기 재고 100개씩 세팅
        IntStream.rangeClosed(1, 10)
                .forEach(i -> stockMap.put("ITEM-" + i, new AtomicInteger(100)));
    }

    @AfterAll
    void shutdown() {
        if (redissonClient != null) redissonClient.shutdown();
    }

    @Test
    @DisplayName("Redis 클러스터 분산락 검증 - 50개 차감 + 50개 증가 후 재고 유지")
    void testStockBalanceWithLock() throws InterruptedException {
        int totalThreads = 1000;
        int itemCount = 10;
        int perItemThreads = totalThreads / itemCount;
        int half = perItemThreads / 2; // 50 감소 + 50 증가

        ExecutorService executor = Executors.newFixedThreadPool(totalThreads);
        CountDownLatch latch = new CountDownLatch(totalThreads);

        long start = System.currentTimeMillis();

        stockMap.keySet().forEach(code -> {
            // 50 감소
            IntStream.range(0, half).forEach(i ->
                    executor.submit(() -> {
                        processStockChange(code, -1);
                        latch.countDown();
                    })
            );
            // 50 증가
            IntStream.range(0, half).forEach(i ->
                    executor.submit(() -> {
                        processStockChange(code, +1);
                        latch.countDown();
                    })
            );
        });

        latch.await();
        long elapsed = System.currentTimeMillis() - start;
        executor.shutdown();

        // 모든 ITEM의 재고가 100개 그대로 유지되어야 함
        stockMap.forEach((code, stock) -> {
            log.info("ItemCode: {}, 최종 재고: {}", code, stock.get());
            assertThat(stock.get()).isEqualTo(100);
        });

        log.info("전체 처리 시간: {} ms", elapsed);
    }

    private void processStockChange(String code, int delta) {
        String lockKey = "lock:item_code:" + code;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock(10, TimeUnit.SECONDS); // 락 점유
            AtomicInteger stock = stockMap.get(code);
            int before = stock.get();
            int after = before + delta;

            if (after < 0) {
                log.warn("[{}] 재고 부족! {} -> {}", code, before, after);
                return;
            }

            stock.set(after);
            log.debug("[{}] 재고 변경: {} → {}", code, before, after);
        } catch (Exception e) {
            log.error("락 처리 중 오류 [{}]", code, e);
        } finally {
            try {
                lock.unlock();
            } catch (Exception ignored) {}
        }
    }
}
