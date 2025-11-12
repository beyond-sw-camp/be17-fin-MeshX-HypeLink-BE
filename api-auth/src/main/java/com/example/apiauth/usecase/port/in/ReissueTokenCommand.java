package com.example.apiauth.usecase.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReissueTokenCommand {
    private String refreshToken;
}
