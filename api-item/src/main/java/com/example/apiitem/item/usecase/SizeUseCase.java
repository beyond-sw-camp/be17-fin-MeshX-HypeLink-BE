package com.example.apiitem.item.usecase;

import MeshX.common.UseCase;
import com.example.apiitem.item.domain.Size;
import com.example.apiitem.item.usecase.port.in.SizeWebPort;
import com.example.apiitem.item.usecase.port.out.SizePersistencePort;
import com.example.apiitem.item.usecase.port.out.response.SizeInfoListDto;
import com.example.apiitem.util.SizeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SizeUseCase implements SizeWebPort {
    private final SizePersistencePort sizePersistencePort;

    @Override
    public SizeInfoListDto findAll() {
        List<Size> sizeList = sizePersistencePort.findAll();

        return SizeMapper.toDto(sizeList);
    }
}
