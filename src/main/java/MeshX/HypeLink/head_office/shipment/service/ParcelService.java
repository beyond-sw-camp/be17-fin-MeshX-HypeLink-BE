package MeshX.HypeLink.head_office.shipment.service;

import MeshX.HypeLink.auth.model.entity.Member;
import MeshX.HypeLink.auth.model.entity.Store;
import MeshX.HypeLink.auth.repository.StoreJpaRepositoryVerify;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrder;
import MeshX.HypeLink.head_office.order.model.entity.PurchaseOrderState;
import MeshX.HypeLink.head_office.order.repository.PurchaseOrderJpaRepositoryVerify;
import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import MeshX.HypeLink.head_office.shipment.model.entity.ParcelItem;
import MeshX.HypeLink.head_office.shipment.repository.ParcelItemJpaRepositoryVerify;
import MeshX.HypeLink.head_office.shipment.repository.ParcelJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParcelService {
    private final PurchaseOrderJpaRepositoryVerify purchaseOrderRepository;
    private final ParcelJpaRepositoryVerify parcelRepository;
    private final ParcelItemJpaRepositoryVerify parcelItemRepository;

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
//    @Scheduled(cron = "0 * * * * *")
    public void saveParcel() {
        log.info("[Batch] Parcel 생성 작업 시작");

        // REQUESTED 상태인 모든 발주 조회
        List<PurchaseOrder> requestedOrders = purchaseOrderRepository.findAllByPurchaseOrderState(PurchaseOrderState.REQUESTED);

        if (requestedOrders.isEmpty()) {
            log.info("[Batch] 생성할 발주 없음");
            return;
        }

        // (requester, supplier) 기준으로 그룹화
        Map<String, List<PurchaseOrder>> grouped = requestedOrders.stream()
                .collect(Collectors.groupingBy(po -> generateKey(po.getRequester(), po.getSupplier())));

        // 각 그룹별로 Parcel + ParcelItem 생성
        for (Map.Entry<String, List<PurchaseOrder>> entry : grouped.entrySet()) {
            List<PurchaseOrder> groupOrders = entry.getValue();
            if (groupOrders.isEmpty()) continue;

            Member requester = groupOrders.get(0).getRequester();
            Member supplier = groupOrders.get(0).getSupplier();

            if(requester.getId() == 1) {
                continue;
            }

            // Parcel 생성
            Parcel parcel = Parcel.builder()
                    .trackingNumber(generateTrackingNumber())
                    .requester(requester)
                    .supplier(supplier)
                    .build();

            Parcel save = parcelRepository.save(parcel);

            // ParcelItem 생성
            for (PurchaseOrder order : groupOrders) {
                ParcelItem item = ParcelItem.builder()
                        .parcel(save)
                        .purchaseOrder(order)
                        .build();
                parcelItemRepository.save(item);
            }
        }

        log.info("[Batch] Parcel 생성 완료: 총 {}개 묶음", grouped.size());
    }

    private String generateKey(Member requester, Member supplier) {
        return requester.getId() + "-" + supplier.getId();
    }

    private String generateTrackingNumber() {
        return "TRK-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }
}
