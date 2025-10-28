package MeshX.HypeLink.head_office.shipment.service;

import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.auth.repository.DriverJpaRepositoryVerify;
import MeshX.HypeLink.head_office.shipment.model.dto.ShipmentAssignmentReqDto;
import MeshX.HypeLink.head_office.shipment.model.dto.ShipmentInfoDto;
import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import MeshX.HypeLink.head_office.shipment.model.entity.ShipmentStatus;
import MeshX.HypeLink.head_office.shipment.repository.ParcelJpaRepositoryVerify;
import MeshX.HypeLink.head_office.shipment.repository.ShipmentJpaRepositoryVerify;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShipmentService {
    private final ShipmentJpaRepositoryVerify shipmentJpaRepositoryVerify;
    private final ParcelJpaRepositoryVerify parcelJpaRepositoryVerify;
    private final DriverJpaRepositoryVerify driverJpaRepositoryVerify;

    @Transactional
    public void connecting(ShipmentAssignmentReqDto dto) {
        Shipment shipment = shipmentJpaRepositoryVerify.findById(dto.getShipmentId());
        Driver driver = driverJpaRepositoryVerify.findById(dto.getDriverId());

        shipment.updateDriver(driver);
        shipment.updateShipmentStatus(ShipmentStatus.DRIVER_ASSIGNED);
        shipmentJpaRepositoryVerify.save(shipment);
    }

    public List<ShipmentInfoDto> getUnassignedParcels() {
        // driver가 null인 Shipment 조회 (배정 안 된 배송)
        return shipmentJpaRepositoryVerify.findByDriverIsNull()
                .stream()
                .map(ShipmentInfoDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ShipmentInfoDto> getAssignedParcels() {

        List<ShipmentStatus> statuses = Arrays.asList(
                ShipmentStatus.DRIVER_ASSIGNED,
                ShipmentStatus.IN_PROGRESS
        );

        return shipmentJpaRepositoryVerify.findByShipmentStatusIn(statuses)
                .stream()
                .map(ShipmentInfoDto::fromEntity)
                .collect(Collectors.toList());
    }
}
