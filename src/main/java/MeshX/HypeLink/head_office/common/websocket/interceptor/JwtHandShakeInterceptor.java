package MeshX.HypeLink.head_office.common.websocket.interceptor;

import MeshX.HypeLink.auth.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtHandShakeInterceptor implements HandshakeInterceptor {

    private final JwtUtils jwtUtils;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                    WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        try {
            String token = null;

            String query = request.getURI().getQuery();
            if (query != null && query.contains("token=")) {
                String[] params = query.split("&");
                for (String param : params) {
                    if (param.startsWith("token=")) {
                        token = param.substring(6);
                        break;
                    }
                }
            }

            if (token == null) {
                List<String> authHeaders = request.getHeaders().get("Authorization");
                if (authHeaders != null && !authHeaders.isEmpty()) {
                    String authHeader = authHeaders.get(0);
                    if (authHeader.startsWith("Bearer ")) {
                        token = authHeader.substring(7);
                    }
                }
            }

            if (token != null) {
                jwtUtils.validateToken(token);

                Authentication authentication = jwtUtils.getAuthentication(token);
                attributes.put("auth", authentication);

                return true;
            }

            return false;

        } catch (Exception e) {
            log.error("WebSocket 핸드셰이크 중 오류 발생", e);
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        if (exception != null) {
            log.error("WebSocket 핸드셰이크 후 오류", exception);
        }
    }
}
