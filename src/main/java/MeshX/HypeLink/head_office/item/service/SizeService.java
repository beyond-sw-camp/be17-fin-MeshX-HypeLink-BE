package MeshX.HypeLink.head_office.item.service;

import MeshX.HypeLink.head_office.item.model.dto.response.SizeInfoListRes;
import MeshX.HypeLink.head_office.item.model.entity.Size;
import MeshX.HypeLink.head_office.item.repository.SizeJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeService {
    private final SizeJpaRepositoryVerify sizeRepository;

    public SizeInfoListRes findAll() {
        List<Size> all = sizeRepository.findAll();
        return SizeInfoListRes.toDto(all);
    }
}
