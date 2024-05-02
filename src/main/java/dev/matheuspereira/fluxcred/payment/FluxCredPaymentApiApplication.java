package dev.matheuspereira.fluxcred.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@ComponentScan(basePackages = "dev.matheuspereira.fluxcred.payment")
@EnableJpaRepositories(basePackages = "dev.matheuspereira.fluxcred.payment.infrastructure.jpa")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class FluxCredPaymentApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(FluxCredPaymentApiApplication.class, args);
  }
}
