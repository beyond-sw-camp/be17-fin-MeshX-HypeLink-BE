package com.example.apiauth.usecase.port.out.usecase;


import com.example.apiauth.adapter.in.web.dto.LoginResDto;
import com.example.apiauth.usecase.port.in.LoginCommand;

public interface AuthUseCase {
    LoginResDto login(LoginCommand dto);
}
