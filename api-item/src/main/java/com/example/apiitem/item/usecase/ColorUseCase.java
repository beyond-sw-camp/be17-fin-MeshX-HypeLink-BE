package com.example.apiitem.item.usecase;

import MeshX.common.UseCase;
import com.example.apiitem.item.domain.Color;
import com.example.apiitem.item.usecase.port.in.ColorWebPort;
import com.example.apiitem.item.usecase.port.out.ColorPersistencePort;
import com.example.apiitem.item.usecase.port.out.response.ColorInfoListDto;
import com.example.apiitem.util.ColorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ColorUseCase implements ColorWebPort {
    private final ColorPersistencePort colorPersistencePort;

    @Override
    public ColorInfoListDto findAll() {
        List<Color> colorList = colorPersistencePort.findAll();

        return ColorMapper.toDto(colorList);
    }
}
