package com.crispy.crispy_be_challenge_ejona_aliaj.exception;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        super.handleExceptionInternal(ex, body, headers, status, request);
        String url = StringUtils.delete(request.getDescription(false), "uri=");
        ApiError error;
        if (body instanceof ValidationError) {
            error = (ApiError) body;
            error.setInstance(url);
        } else {
            error = new ApiError(status.value(), ex.getMessage(), url);
        }

        this.logger.error("API error", ex);
        return this.buildResponseEntity(error, headers, status);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError error, HttpHeaders existing, HttpStatus status) {
        return new ResponseEntity<>(error, this.setErrorContentType(existing), status);
    }

    private HttpHeaders setErrorContentType(HttpHeaders existing) {
        HttpHeaders headers = existing == null ? new HttpHeaders() : existing;
        headers.setContentType(MediaType.parseMediaType("application/problem+json"));
        return headers;
    }

    @ExceptionHandler({ApiValidationException.class})
    protected ResponseEntity<Object> handleApiValidationException(ApiValidationException ex, WebRequest request) {
        return this.createValidationError(ex, this.setErrorContentType(null), request, ex.getInvalidParams());
    }

    @ExceptionHandler({AuthorizationException.class})
    public ResponseEntity<Object> handleAuthorizationException(AuthorizationException ex, WebRequest request) {
        return this.handleExceptionInternal(ex, this.extractStatusFromAnnotation(ex), request);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return this.handleExceptionInternal(ex, this.extractStatusFromAnnotation(ex), request);
    }

    @ExceptionHandler({JWTVerifyException.class})
    public ResponseEntity<Object> handleJwtException(JWTVerifyException ex, WebRequest request) {
        return this.handleExceptionInternal(ex, this.extractStatusFromAnnotation(ex), request);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        return this.handleExceptionInternal(ex, this.extractStatusFromAnnotation(ex), request);
    }

    private ResponseEntity<Object> createValidationError(ApiValidationException ex, HttpHeaders headers, WebRequest request, List<ValidationMessage> list) {
        return this.createValidationErrorEntity(ex, headers, request, list, ex.getMessage());
    }

    private ResponseEntity<Object> createValidationErrorEntity(Exception ex, HttpHeaders headers, WebRequest request, List<ValidationMessage> list, String title) {
        ValidationError validationError = new ValidationError();
        validationError.setInvalidParams(list);
        validationError.setTitle(title);
        validationError.setStatus(HttpStatus.BAD_REQUEST.value());
        return this.handleExceptionInternal(ex, validationError, headers, HttpStatus.BAD_REQUEST, request);
    }

    private HttpStatus extractStatusFromAnnotation(Exception ex) {
        ResponseStatus respStatusAnnotation = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (respStatusAnnotation != null) {
            status = respStatusAnnotation.value();
        }

        return status;
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, HttpStatus status, WebRequest request) {
        return this.handleExceptionInternal(ex, null, this.setErrorContentType(null), status, request);
    }

}
