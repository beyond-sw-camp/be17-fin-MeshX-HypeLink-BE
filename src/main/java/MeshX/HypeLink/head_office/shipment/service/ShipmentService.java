package MeshX.HypeLink.head_office.shipment.service;

import MeshX.HypeLink.head_office.shipment.model.dto.ConnectingReqDto;
import MeshX.HypeLink.head_office.shipment.model.dto.CreateParcelReqDto;
import MeshX.HypeLink.head_office.shipment.model.entity.Parcel;
import MeshX.HypeLink.head_office.shipment.model.entity.Shipment;
import MeshX.HypeLink.head_office.shipment.repository.ShipmentJpaRepositoryVerify;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ShipmentService {
    private final ShipmentJpaRepositoryVerify shipmentJpaRepositoryVerify;

    public Parcel create(CreateParcelReqDto dto) {
        UUID uuid = UUID.randomUUID();
        long l = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits();
        String invoiceNo = String.format("%012d", Math.abs(l) % 1_000_000_000_000L);
        return dto.toEntity(invoiceNo);
    }

    public Shipment connetcting(ConnectingReqDto dto) {
        //dto.getTrackingNumber() 여기서 송장 번호 하나하나 해서 parcel 하나씩 받아서 list 만들어서 넣기
        return dto.toEntity(null);
    }

    public List<Shipment> getUnassignedParcels() {
        return shipmentJpaRepositoryVerify.findByDriverIsNull();
    }
}
