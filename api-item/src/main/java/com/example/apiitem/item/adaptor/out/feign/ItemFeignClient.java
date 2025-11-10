package com.example.apiitem.item.adaptor.out.feign;

import com.example.apiitem.config.FeignGlobalConfig;
import com.example.apiitem.item.adaptor.out.feign.dto.SaveItemReq;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="itemClient", url = "${feign.item}", configuration = FeignGlobalConfig.class)
public interface ItemFeignClient {
    Logger log = LoggerFactory.getLogger(ItemFeignClient.class); // Logger 선언 가능

    @CircuitBreaker(name = "ITEM_CIRCUIT", fallbackMethod = "saveItem")
    @PostMapping("/save")
    String saveItem(@RequestBody SaveItemReq dto);

    // 자바 8 에서 추가된 기능 : 인터페이스에 default로 메소드 구현 가능
    default String fallbackSaveItem(Throwable cause) {
        log.error("예외 처리 : {}", cause.toString());
        return "동기화에 실패했습니다.";
    }
}
