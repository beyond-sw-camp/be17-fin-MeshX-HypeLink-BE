package com.example.apiclients.resolver;

import com.example.apiclients.annotation.GetStoreId;
import MeshX.common.BaseResponse;
import com.example.apiclients.client.AuthApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class GetStoreIdResolver implements HandlerMethodArgumentResolver {

    private final AuthApiClient authApiClient;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(GetStoreId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                   NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        String email = webRequest.getHeader("Member-Email");

        if (email == null) {
            return null;
        }

        BaseResponse<Integer> response = authApiClient.getStoreIdByEmail(email);
        return response.getData();
    }
}
