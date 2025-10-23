package MeshX.HypeLink.head_office.item.service;

import MeshX.HypeLink.head_office.item.model.dto.response.ColorInfoListRes;
import MeshX.HypeLink.head_office.item.model.entity.Color;
import MeshX.HypeLink.head_office.item.repository.ColorJpaRepositoryVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorService {
    private final ColorJpaRepositoryVerify colorRepository;

    public ColorInfoListRes findAll() {
        List<Color> all = colorRepository.findAll();
        return ColorInfoListRes.toDto(all);
    }
}
