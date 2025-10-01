package MeshX.HypeLink.head_office.common.websocket.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.Map;

public class AuthChannelInterceptor  implements ChannelInterceptor {
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//        if(StompCommand.CONNECT.equals(accessor.getCommand())) {
//            Map<String, Object> attributes = accessor.getSessionAttributes();
//            if(attributes!=null) {
//                Authentication authentication = (Authentication) attributes.get("auth");
//                if(authentication!=null) {
//                    accessor.setUser(authentication);
//                }
//            }
//        }
//        return message;
//    }
}
