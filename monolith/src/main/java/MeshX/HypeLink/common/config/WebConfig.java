package MeshX.HypeLink.common.config;

import MeshX.HypeLink.common.resolver.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CurrentMemberIdResolver currentMemberIdResolver;
    private final CurrentEmailResolver currentEmailResolver;
    private final CurrentStoreIdResolver currentStoreIdResolver;
    private final CurrentPosIdResolver currentPosIdResolver;
    private final CurrentDriverIdResolver currentDriverIdResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentMemberIdResolver);
        resolvers.add(currentEmailResolver);
        resolvers.add(currentStoreIdResolver);
        resolvers.add(currentPosIdResolver);
        resolvers.add(currentDriverIdResolver);
    }
}
