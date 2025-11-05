package org.example.apiauth.usecase.port.out;


import org.example.apiauth.adapter.in.web.dto.LoginResDto;
import org.example.apiauth.usecase.port.in.LoginCommand;

public interface AuthUseCase {
    LoginResDto login(LoginCommand dto);
}
