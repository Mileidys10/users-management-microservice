package com.jcaa.usersmanagement.infrastructure.config;

import com.jcaa.usersmanagement.infrastructure.adapter.email.BrevoConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BrevoSpringConfig")
class BrevoSpringConfigTest {

  private final BrevoSpringConfig config = new BrevoSpringConfig();

  @Test
  @DisplayName("brevoConfig creates valid configuration and logs fingerprint")
  void shouldCreateBrevoConfig() {
    final BrevoConfig brevoConfig =
        config.brevoConfig(
            "https://api.brevo.com",
            "xkeysib-test-1234567890",
            "test@example.com",
            "Test Sender",
            Duration.ofSeconds(2),
            Duration.ofSeconds(5),
            3,
            Duration.ofMillis(500),
            2.0,
            50,
            10,
            5,
            Duration.ofSeconds(30));

    assertThat(brevoConfig).isNotNull();
    assertThat(brevoConfig.baseUrl()).isEqualTo("https://api.brevo.com");
    assertThat(brevoConfig.apiKey()).isEqualTo("xkeysib-test-1234567890");
    assertThat(brevoConfig.fromAddress()).isEqualTo("test@example.com");
    assertThat(brevoConfig.fromName()).isEqualTo("Test Sender");
  }

  @Test
  @DisplayName("brevoRestClient builds configured RestClient")
  void shouldCreateRestClient() {
    final BrevoConfig brevoConfig =
        new BrevoConfig(
            "https://api.brevo.com",
            "xkeysib-test-1234567890",
            "test@example.com",
            "Test Sender",
            Duration.ofSeconds(2),
            Duration.ofSeconds(5),
            3,
            Duration.ofMillis(500),
            2.0,
            50,
            10,
            5,
            Duration.ofSeconds(30));

    final RestClient restClient = config.brevoRestClient(brevoConfig);

    assertThat(restClient).isNotNull();
  }
}
