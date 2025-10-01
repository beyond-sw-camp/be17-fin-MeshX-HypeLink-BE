package MeshX.HypeLink.head_office.common.websocket;

import MeshX.HypeLink.head_office.common.websocket.interceptor.AuthChannelInterceptor;
import MeshX.HypeLink.head_office.common.websocket.interceptor.JwtHandShakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//    private final JwtHandShakeInterceptor jwtHandShakeInterceptor;
//    private final AuthChannelInterceptor authChannelInterceptor;

    @Bean(name = "customMessageBrokerScheduler")
    public TaskScheduler messageBrokerTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("ws-heartbeat-");
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.initialize();
        return scheduler;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue") // Topic 설정
                .setTaskScheduler(messageBrokerTaskScheduler())
                .setHeartbeatValue(new long[]{10000, 10000});
        // 하트비트 연결 및 10초 주기로 연결 확인
        registry.setApplicationDestinationPrefixes("/hypelink"); // 클라이언트가 보내야 할 시작 URI
        registry.setUserDestinationPrefix("/user");
        // 사용자별 메세지 라우팅
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // Http로 처음에 WebSocket HandShake할 때, EndPoint 주소를 의미함.
//                .addInterceptors(jwtHandShakeInterceptor)
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(authChannelInterceptor);
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(128 * 1024); // 128KB
        registration.setSendBufferSizeLimit(512 * 1024); // 512KB
        registration.setSendTimeLimit(1000); // 전송시간 제한 1초
    }
}
