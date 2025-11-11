package com.example.apiitem.util;

import MeshX.common.BaseResponse;
import MeshX.common.exception.BaseException;
import MeshX.common.exception.ErrorResponse;
import MeshX.common.exception.ExceptionType;
import com.example.apiitem.common.ItemException;
import com.example.apiitem.common.ItemExceptionType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class FeignCustomDecoder implements Decoder {
    private final ObjectMapper objectMapper;

    @Override
    public Object decode(Response response, Type type) throws IOException {
        String body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));

        // JSON이 비어 있으면 그냥 null 반환
        if (body == null || body.isBlank()) {
            return null;
        }

        // 일단 ErrorResponse 구조인지 판단
        JsonNode root = objectMapper.readTree(body);

        // 실패 구조일 경우 (code 존재)
        if (root.has("code")) {
            ErrorResponse error = objectMapper.treeToValue(root, ErrorResponse.class);
            throw new ItemException(ItemExceptionType.builder()
                    .message(error.getMessage())
                    .build());
        }

        // 성공 구조일 경우
        BaseResponse<?> base = objectMapper.treeToValue(root, BaseResponse.class);

        // result(data) 부분만 지정된 타입으로 역직렬화해서 반환
        String dataJson = objectMapper.writeValueAsString(base.getData());
        return objectMapper.readValue(dataJson, objectMapper.constructType(type));
    }
}
