package MeshX.HypeLink.direct_store.payment.config;

import io.portone.sdk.server.PortOneClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortOneConfig {

    @Value("${portone.api.secret}")
    private String apiSecret;

    @Value("${portone.store-id}")
    private String storeId;

    @Bean
    public PortOneClient portOneClient() {
        return new PortOneClient(apiSecret, "https://api.portone.io", storeId);
    }

    public String getStoreId() {
        return storeId;
    }
}