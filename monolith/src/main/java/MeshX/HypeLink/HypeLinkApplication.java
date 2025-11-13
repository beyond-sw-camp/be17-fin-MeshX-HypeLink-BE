package MeshX.HypeLink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@EnableFeignClients(basePackages = "com.example.apiclients.client")
@ComponentScan(basePackages = {"MeshX.HypeLink", "com.example.apiclients"})
@SpringBootApplication
public class HypeLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(HypeLinkApplication.class, args);
	}

}
