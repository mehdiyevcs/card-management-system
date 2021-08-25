package az.company.card.error;

import az.company.card.error.exception.CommonException;
import az.company.card.error.exception.InvalidInputException;
import az.company.card.error.exception.NotFoundException;
import az.company.card.error.model.ErrorResponse;
import az.company.card.util.MessageSourceUtil;
import az.company.card.util.WebUtil;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author MehdiyevCS on 23.08.21
 */
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    @Autowired
    private WebUtil webUtil;

    @Autowired
    private MessageSourceUtil messageSourceUtil;

    public ErrorHandler() {

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidInputException.class})
    public ErrorResponse handleInvalidInputException(InvalidInputException ex) {
        String message = this.messageSourceUtil.getMessage(ex.getMessage(), ex.messageArguments());
        this.addErrorLog(HttpStatus.BAD_REQUEST.value(), message, "InvalidInputException");
        return new ErrorResponse(this.webUtil.getRequestId(), HttpStatus.BAD_REQUEST.value(), message);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
        String message = this.messageSourceUtil.getMessage(ex.getMessage());
        this.addErrorLog(HttpStatus.NOT_FOUND.value(), message, "NotFoundException");
        return new ErrorResponse(this.webUtil.getRequestId(), HttpStatus.NOT_FOUND.value(), message);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({CommonException.class})
    public ErrorResponse handleCommonException(CommonException ex) {
        this.addErrorLog(ex.getCode(), ex.getMessage(), (Throwable) ex);
        return new ErrorResponse(this.webUtil.getRequestId(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ErrorResponse handleAll(Exception ex) {
        this.addErrorLog(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), (Throwable) ex);
        var errMsg = "Unexpected internal server error occurs";
        return new ErrorResponse(this.webUtil.getRequestId(), HttpStatus.INTERNAL_SERVER_ERROR, errMsg);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        this.addErrorLog(HttpStatus.BAD_REQUEST, ex.getMessage(), "MethodArgumentTypeMismatchException");
        return new ErrorResponse(this.webUtil.getRequestId(), HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
        Optional<String> violations = ex.getConstraintViolations().stream().map(v -> {
            var var10000 = v.getPropertyPath();
            return var10000 + ": " + this.messageSourceUtil.getMessage(v.getMessage());
        }).collect(Collectors.collectingAndThen(Collectors.joining("; "), Optional::ofNullable));
        String errMsg = violations.orElse(ex.getMessage());
        this.addErrorLog(HttpStatus.BAD_REQUEST, errMsg, "ConstraintViolationException");
        return new ErrorResponse(this.webUtil.getRequestId(), HttpStatus.BAD_REQUEST, errMsg);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Optional<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(x -> String.format("'%s' %s", x.getField(),
                        this.messageSourceUtil.getMessage(x.getDefaultMessage())))
                .collect(Collectors.collectingAndThen(Collectors.joining("; "), Optional::ofNullable));
        var errLogMessage = String.join(System.lineSeparator(),
                (CharSequence) errors.orElse(ex.getMessage()), "Request body: " + this.webUtil.getRequestBody());
        this.addErrorLog(status, errLogMessage, "MethodArgumentNotValidException");
        var errorResponse = new ErrorResponse(this.webUtil.getRequestId(),
                status, errors.orElse(ex.getMessage()));
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        this.addErrorLog(status, error, "MissingServletRequestParameterException");
        var errorResponse = new ErrorResponse(this.webUtil.getRequestId(), status, error);
        return new ResponseEntity(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        var errLogMessage = String.join(System.lineSeparator(),
                ex.getMessage(), "Request body: " + this.webUtil.getRequestBody());
        this.addErrorLog(status, errLogMessage, "HttpMessageNotReadableException");
        var errorResponse = new ErrorResponse(this.webUtil.getRequestId(), status, ex.getMessage());
        return new ResponseEntity(errorResponse, headers, status);
    }

    protected void addErrorLog(HttpStatus httpStatus, String errorMessage, Throwable ex) {
        this.addErrorLog(httpStatus.value(), errorMessage, ex);
    }

    protected void addErrorLog(HttpStatus httpStatus, String errorMessage, String exceptionType) {
        this.addErrorLog(httpStatus.value(), errorMessage, exceptionType);
    }

    protected void addErrorLog(Integer errorCode, String errorMessage, Throwable ex) {
        log.error("[Error] | Code: {} | Type: {} | Path: {} | Elapsed time: {} ms | Message: {}",
                errorCode, ex.getClass().getTypeName(), this.webUtil.getRequestUri(),
                StructuredArguments.v("elapsed_time", this.webUtil.getElapsedTime()), errorMessage, ex);
    }

    protected void addErrorLog(Integer errorCode, String errorMessage, String exceptionType) {
        log.error("[Error] | Code: {} | Type: {} | Path: {} | Elapsed time: {} ms | Message: {}",
                errorCode, exceptionType, this.webUtil.getRequestUri(),
                StructuredArguments.v("elapsed_time", this.webUtil.getElapsedTime()), errorMessage);
    }
}
