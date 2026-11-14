package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.advice;

import com.jcaa.usersmanagement.domain.exception.DomainException;
import com.jcaa.usersmanagement.domain.exception.InvalidCredentialsException;
import com.jcaa.usersmanagement.domain.exception.UserAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.ApiErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler handler;

  @BeforeEach
  void setUp() {
    handler = new GlobalExceptionHandler();
  }

  @Test
  @DisplayName("handleUserNotFound should return 404 with error message")
  void shouldHandleUserNotFound() {
    final UserNotFoundException exception = UserNotFoundException.becauseIdWasNotFound("user-123");

    final ApiErrorResponse response = handler.handleUserNotFound(exception);

    assertThat(response.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    assertThat(response.message()).isEqualTo(exception.getMessage());
  }

  @Test
  @DisplayName("handleUserAlreadyExists should return 409 with error message")
  void shouldHandleUserAlreadyExists() {
    final UserAlreadyExistsException exception =
        UserAlreadyExistsException.becauseEmailAlreadyExists("user@example.com");

    final ApiErrorResponse response = handler.handleUserAlreadyExists(exception);

    assertThat(response.status()).isEqualTo(HttpStatus.CONFLICT.value());
    assertThat(response.message()).isEqualTo(exception.getMessage());
  }

  @Test
  @DisplayName("handleInvalidCredentials should return 401 with error message")
  void shouldHandleInvalidCredentials() {
    final InvalidCredentialsException exception =
        InvalidCredentialsException.becauseCredentialsAreInvalid();

    final ApiErrorResponse response = handler.handleInvalidCredentials(exception);

    assertThat(response.status()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    assertThat(response.message()).isEqualTo(exception.getMessage());
  }

  @Test
  @DisplayName("handleConstraintViolation should return 400 with formatted violations")
  void shouldHandleConstraintViolation() {
    @SuppressWarnings("unchecked")
    final ConstraintViolation<Object> violation = mock(ConstraintViolation.class);
    final Path path = mock(Path.class);
    when(path.toString()).thenReturn("email");
    when(violation.getPropertyPath()).thenReturn(path);
    when(violation.getMessage()).thenReturn("must be valid");

    final ConstraintViolationException exception =
        new ConstraintViolationException(Set.of(violation));

    final ApiErrorResponse response = handler.handleConstraintViolation(exception);

    assertThat(response.status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.message()).contains("email: must be valid");
  }

  @Test
  @DisplayName("handleMethodArgumentNotValid should return 400 with formatted field errors")
  void shouldHandleMethodArgumentNotValid() {
    final MethodParameter parameter = mock(MethodParameter.class);
    final BindingResult bindingResult = mock(BindingResult.class);
    final FieldError fieldError = new FieldError("user", "name", "Name is mandatory");
    when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

    final MethodArgumentNotValidException exception =
        new MethodArgumentNotValidException(parameter, bindingResult);

    final ApiErrorResponse response = handler.handleMethodArgumentNotValid(exception);

    assertThat(response.status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.message()).isEqualTo("name: Name is mandatory");
  }

  @Test
  @DisplayName("handleDomain should return 400 with exception message")
  void shouldHandleDomainException() {
    final DomainException exception = new DomainException("Domain validation failed") {};

    final ApiErrorResponse response = handler.handleDomain(exception);

    assertThat(response.status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.message()).isEqualTo("Domain validation failed");
  }

  @Test
  @DisplayName("handlePersistence should return 500 with generic persistence error message")
  void shouldHandlePersistenceException() {
    final PersistenceException exception =
        PersistenceException.becauseSaveFailed("user-123", new RuntimeException("DB error"));

    final ApiErrorResponse response = handler.handlePersistence(exception);

    assertThat(response.status()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    assertThat(response.message()).isEqualTo("Error de persistencia.");
  }

  @Test
  @DisplayName("handleGeneral should return 500 with generic server error message")
  void shouldHandleGeneralException() {
    final Exception exception = new RuntimeException("Unexpected error");

    final ApiErrorResponse response = handler.handleGeneral(exception);

    assertThat(response.status()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    assertThat(response.message()).isEqualTo("Error interno del servidor.");
  }
}
