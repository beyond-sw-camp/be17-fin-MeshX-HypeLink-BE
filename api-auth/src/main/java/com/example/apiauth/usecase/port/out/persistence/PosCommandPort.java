package com.example.apiauth.usecase.port.out.persistence;

import com.example.apiauth.domain.model.Pos;

public interface PosCommandPort {

    Pos save(Pos pos);

    void delete(Integer id);
}
