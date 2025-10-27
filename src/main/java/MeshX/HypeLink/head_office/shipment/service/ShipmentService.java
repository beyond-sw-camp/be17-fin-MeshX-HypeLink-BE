package MeshX.HypeLink.head_office.shipment.service;

import MeshX.HypeLink.auth.model.entity.Driver;
import MeshX.HypeLink.auth.repository.DriverJpaRepositoryVerify;
import MeshX.HypeLink.head_office.shipment.model.dto.CreateParcelReqDto;
import MeshX.HypeLink.head_office.shipment.model.dto.ShipmentAssignmentReqDto;
import MeshX.HypeLink.head_office.shipment.model.dto.ShipmentInfoDto;
import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import MeshX.HypeLink.head_office.shipment.model.entity.ShipmentStatus;
import MeshX.HypeLink.head_office.shipment.repository.ShipmentJpaRepositoryVerify;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShipmentService {
    private final ShipmentJpaRepositoryVerify shipmentJpaRepositoryVerify;
    private final DriverJpaRepositoryVerify driverJpaRepositoryVerify;

    public Parcel create(CreateParcelReqDto dto) {
        UUID uuid = UUID.randomUUID();
        long l = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits();
        String invoiceNo = String.format("%012d", Math.abs(l) % 1_000_000_000_000L);
        return dto.toEntity(invoiceNo);
    }

    @Transactional
    public void connecting(ShipmentAssignmentReqDto dto) {
        Shipment shipment = shipmentJpaRepositoryVerify.findById(dto.getShipmentId());
        Driver driver = driverJpaRepositoryVerify.findById(dto.getDriverId());

        shipment.updateDriver(driver);
        shipment.updateShipmentStatus(ShipmentStatus.DRIVER_ASSIGNED);
        shipmentJpaRepositoryVerify.save(shipment);
    }

    public List<ShipmentInfoDto> getUnassignedParcels() {
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
