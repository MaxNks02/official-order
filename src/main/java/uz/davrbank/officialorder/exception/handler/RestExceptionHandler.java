package uz.davrbank.officialorder.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.davrbank.officialorder.exception.BadRequestException;
import uz.davrbank.officialorder.exception.CustomNotFoundException;
import uz.davrbank.officialorder.exception.DatabaseException;

import java.util.Arrays;
import java.util.Objects;

/**
 * Controller class for several exception handlers for handling exceptions
 *
 * @author Abduvohid Isroilov
 */
@SuppressWarnings({"NullableProblems", "PlaceholderCountMatchesArgumentCount"})
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);


    /**
     * Handle HttpRequestMethodNotSupportedException. This one triggers when coming http method is not acceptable.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ErrorResults object
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getLocalizedMessage(), "Method not allowed exception: ");
        String error = ApiErrorMessages.METHOD_NOT_ALLOWED + " ( " + ex.getMethod() + " ) ";
        return buildResponseEntity(new ErrorResults(error, 405), HttpStatus.METHOD_NOT_ALLOWED);

    }

    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ErrorResults object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ApiErrorMessages.UNSUPPORTED_MEDIA_TYPE + " ( " + Arrays.toString(ex.getSupportedMediaTypes().toArray()) + " ) ";
        return buildResponseEntity(new ErrorResults(error, 41500), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * Handle MissingPathVariableException. This one triggers when necessary path variables is missed.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ErrorResults object
     */
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ApiErrorMessages.BAD_REQUEST + " ( " + ex.getParameter() + " ) ";
        return buildResponseEntity(new ErrorResults(error, 40400), HttpStatus.BAD_REQUEST);
    }


    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ErrorResults object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ApiErrorMessages.BAD_REQUEST + " ( " + ex.getParameterType() + " : " + ex.getParameterName() + " ) ";
        return buildResponseEntity(new ErrorResults(error, 40401), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle TypeMismatchException. Triggered when demanded type is not coming.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ErrorResults object
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ApiErrorMessages.BAD_REQUEST + " Required type is ( " + ex.getRequiredType() + " ) ";
        return buildResponseEntity(new ErrorResults(error, 40402), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ErrorResults object
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Field not valid", ex.getFieldError());
        return buildResponseEntity(new ErrorResults(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(), 40403), HttpStatus.BAD_REQUEST);
    }

    /**
     * Custom bad request exception for any kind of bad requests.
     *
     * @param ex BadRequestException
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        logger.error(ex.getMessage(), "Bad request exception");
        return buildResponseEntity(new ErrorResults(ex.getMessage(), 40000), HttpStatus.BAD_REQUEST);
    }

    /**
     * Custom not found exception for not found operations.
     *
     * @param ex CustomNotFoundException
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler(CustomNotFoundException.class)
    protected ResponseEntity<Object> handleCustomNotFoundException(CustomNotFoundException ex) {
        logger.error(ex.getLocalizedMessage(), "Not found exception");
        return buildResponseEntity(new ErrorResults(ex.getMessage(), 40404), HttpStatus.NOT_FOUND);
    }

    /**
     * Database exception class for errors while exchanging data with database.
     *
     * @param ex DatabaseException
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler(DatabaseException.class)
    protected ResponseEntity<Object> handleDatabaseException(DatabaseException ex) {
        logger.error(ex.getLocalizedMessage(), "Database exception");
        return buildResponseEntity(new ErrorResults(ex.getMessage(), 50001), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResults errorResults, HttpStatus httpStatus) {
        return new ResponseEntity<>(errorResults, httpStatus);
    }


}

