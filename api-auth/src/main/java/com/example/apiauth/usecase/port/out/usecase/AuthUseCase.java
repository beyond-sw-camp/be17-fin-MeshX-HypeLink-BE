package com.example.apiauth.usecase.port.out.usecase;


import com.example.apiauth.adapter.in.web.dto.LoginResDto;
import com.example.apiauth.domain.model.value.AuthTokens;
import com.example.apiauth.usecase.port.in.LoginCommand;
import com.example.apiauth.usecase.port.in.RegisterCommand;
import com.example.apiauth.usecase.port.in.ReissueTokenCommand;

public interface AuthUseCase {
    LoginResDto login(LoginCommand dto);

    AuthTokens reissue(ReissueTokenCommand reissueTokenCommand);

    void logout(String accessToken);

    void register(RegisterCommand command);
}
