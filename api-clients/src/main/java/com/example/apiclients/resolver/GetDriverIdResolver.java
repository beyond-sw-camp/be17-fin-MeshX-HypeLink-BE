package com.example.apiclients.resolver;

import MeshX.common.BaseResponse;
import com.example.apiclients.annotation.GetDriverId;
import com.example.apiclients.client.AuthApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class GetDriverIdResolver implements HandlerMethodArgumentResolver {

    private final AuthApiClient authApiClient;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(GetDriverId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                   NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        String email = webRequest.getHeader("Member-Email");

        if (email == null) {
            return null;
        }

        BaseResponse<Integer> response = authApiClient.getDriverIdByEmail(email);
        return response.getData();
    }
}
