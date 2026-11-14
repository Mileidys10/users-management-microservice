package com.jcaa.usersmanagement.infrastructure.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ConfigurationException")
class ConfigurationExceptionTest {

  @Test
  @DisplayName("becauseLoadFailed creates exception with cause and standard message")
  void shouldCreateExceptionWithCause() {
    final Throwable cause = new RuntimeException("Underlying file error");
    final ConfigurationException exception = ConfigurationException.becauseLoadFailed(cause);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Failed to load the application configuration.");
    assertThat(exception.getCause()).isSameAs(cause);
  }
}
