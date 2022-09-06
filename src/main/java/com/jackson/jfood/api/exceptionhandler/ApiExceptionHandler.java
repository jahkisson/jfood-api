package com.jackson.jfood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.jackson.jfood.domain.exception.BusinessException;
import com.jackson.jfood.domain.exception.EntityIsBeingUsedException;
import com.jackson.jfood.domain.exception.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String MESSAGE_GENERIC_ERROR_USER_FRIENDLY = "Ocorreu um erro interno inesperado no sistema. "
			+ "Tente novamente e caso o problema persistir, entre em contato com o administrador do sistema";

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		ex.printStackTrace();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ApiError apiError = createApiErrorBuilder(status, ApiErrorType.SYSTEM_ERROR, MESSAGE_GENERIC_ERROR_USER_FRIENDLY)
				.userMessage(MESSAGE_GENERIC_ERROR_USER_FRIENDLY)
				.build();
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String detail = String.format("O recurso '%s', que você tentou acessar, não existe.", ex.getRequestURL());
		ApiError apiError = createApiErrorBuilder(status, ApiErrorType.RESOURCE_NOT_FOUND, detail).build();
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException)ex, headers, status, request);
		}
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. "
				+ "Corrija e informe um valor compatível com o tipo %s.", 
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		ApiError apiError = createApiErrorBuilder(status, ApiErrorType.INVALID_PARAMETER, detail).build();
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof UnrecognizedPropertyException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
		}
		String detail = "O corpo da requisição está inválido, verifique a sintaxe";
		ApiError apiError = createApiErrorBuilder(status, ApiErrorType.UNREADABLE_BODY, detail).build();
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String path = joinPath(ex.getPath());
		String detail = String.format("A propriedade '%s' não foi reconhecida no modelo. "
				+ "Corrija ou remova a propriedade e tente novamente.", 
				path);
		ApiError apiError = createApiErrorBuilder(status, ApiErrorType.UNKOWN_PROPERTIES, detail)
				.userMessage(MESSAGE_GENERIC_ERROR_USER_FRIENDLY)
				.build();
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String path = joinPath(ex.getPath());
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. "
				+ "Corrija e informe um valor compatível com o tipo %s", 
				path, 
				ex.getValue(), 
				ex.getTargetType().getSimpleName());
		ApiError apiError = createApiErrorBuilder(status, ApiErrorType.UNREADABLE_BODY, detail)
				.userMessage(MESSAGE_GENERIC_ERROR_USER_FRIENDLY)
				.build();
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ApiError apiError = createApiErrorBuilder(status, ApiErrorType.RESOURCE_NOT_FOUND, ex.getMessage()).build();
		
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntityIsBeingUsedException.class)
	public ResponseEntity<?> handleEntityIsBeingUsed(EntityIsBeingUsedException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		ApiError apiError = createApiErrorBuilder(status, ApiErrorType.ENTITY_IN_USE, ex.getMessage()).build();
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ApiError apiError = createApiErrorBuilder(status, ApiErrorType.BUSINESS_ERROR, ex.getMessage()).build();
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null || body instanceof String) {
			final String message = body != null ? (String)body : status.getReasonPhrase();
			body = ApiError.builder()
					.status(status.value())
					.title(message)
					.timestamp(LocalDateTime.now())
					.userMessage(MESSAGE_GENERIC_ERROR_USER_FRIENDLY)
					.build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private String joinPath(List<Reference> references) {
		return references.stream().map(x -> x.getFieldName()).collect(Collectors.joining("."));
	}
	
	private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, ApiErrorType apiErrorType, String detail) {
		return ApiError.builder()
				.status(status.value())
				.type(apiErrorType.getUri())
				.title(apiErrorType.getTitle())
				.detail(detail)
				.timestamp(LocalDateTime.now());
	}
}
