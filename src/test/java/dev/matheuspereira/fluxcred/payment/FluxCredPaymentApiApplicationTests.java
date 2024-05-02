package dev.matheuspereira.fluxcred.payment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = {FluxCredPaymentApiApplication.class})
class FluxCredPaymentApiApplicationTests {

  @Test
  void contextLoads() {}
}
