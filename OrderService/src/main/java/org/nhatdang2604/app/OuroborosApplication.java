package org.nhatdang2604.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages="org.nhatdang2604")
@EnableJpaRepositories("org.nhatdang2604.repositories")
@EntityScan("org.nhatdang2604.entities")
public class OuroborosApplication {

    public static void main(String[] args) {
        SpringApplication.run(OuroborosApplication.class, args);
    }

}
