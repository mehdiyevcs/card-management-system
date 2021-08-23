package az.company.card.error;

import az.company.card.error.exception.CommonException;
import az.company.card.error.exception.InvalidInputException;
import az.company.card.error.exception.NotFoundException;
import az.company.card.error.model.ErrorResponse;
import az.company.card.util.MessageSourceUtil;
import az.company.card.util.WebUtil;
import lombok.AllArgsConstructor;
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
import javax.validation.Path;
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

    public ErrorHandler(){}

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
        this.addErrorLog((Integer)ex.getCode(), ex.getMessage(), (Throwable)ex);
        return new ErrorResponse(this.webUtil.getRequestId(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ErrorResponse handleAll(Exception ex) {
        this.addErrorLog((HttpStatus)HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), (Throwable)ex);
        String errMsg = "Unexpected internal server error occurs";
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
        Optional<String> violations = (Optional)ex.getConstraintViolations().stream().map((v) -> {
            Path var10000 = v.getPropertyPath();
            return var10000 + ": " + this.messageSourceUtil.getMessage(v.getMessage());
        }).collect(Collectors.collectingAndThen(Collectors.joining("; "), Optional::ofNullable));
        String errMsg = (String)violations.orElse(ex.getMessage());
        this.addErrorLog(HttpStatus.BAD_REQUEST, errMsg, "ConstraintViolationException");
        return new ErrorResponse(this.webUtil.getRequestId(), HttpStatus.BAD_REQUEST, errMsg);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Optional<String> errors = (Optional)ex.getBindingResult().getFieldErrors().stream().map((x) -> {
            return String.format("'%s' %s", x.getField(), this.messageSourceUtil.getMessage(x.getDefaultMessage()));
        }).collect(Collectors.collectingAndThen(Collectors.joining("; "), Optional::ofNullable));
        String errLogMessage = String.join(System.lineSeparator(), (CharSequence)errors.orElse(ex.getMessage()), "Request body: " + this.webUtil.getRequestBody());
        this.addErrorLog(status, errLogMessage, "MethodArgumentNotValidException");
        ErrorResponse errorResponse = new ErrorResponse(this.webUtil.getRequestId(), status, (String)errors.orElse(ex.getMessage()));
        return new ResponseEntity(errorResponse, headers, status);
    }

    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        this.addErrorLog(status, error, "MissingServletRequestParameterException");
        ErrorResponse errorResponse = new ErrorResponse(this.webUtil.getRequestId(), status, error);
        return new ResponseEntity(errorResponse, headers, status);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errLogMessage = String.join(System.lineSeparator(), ex.getMessage(), "Request body: " + this.webUtil.getRequestBody());
        String error = "Request not readable";
        this.addErrorLog(status, errLogMessage, "HttpMessageNotReadableException");
        ErrorResponse errorResponse = new ErrorResponse(this.webUtil.getRequestId(), status, ex.getMessage());
        return new ResponseEntity(errorResponse, headers, status);
    }

    protected void addErrorLog(HttpStatus httpStatus, String errorMessage, Throwable ex) {
        this.addErrorLog(httpStatus.value(), errorMessage, ex);
    }

    protected void addErrorLog(HttpStatus httpStatus, String errorMessage, String exceptionType) {
        this.addErrorLog(httpStatus.value(), errorMessage, exceptionType);
    }

    protected void addErrorLog(Integer errorCode, String errorMessage, Throwable ex) {
        log.error("[Error] | Code: {} | Type: {} | Path: {} | Elapsed time: {} ms | Message: {}", new Object[]{errorCode, ex.getClass().getTypeName(), this.webUtil.getRequestUri(), StructuredArguments.v("elapsed_time", this.webUtil.getElapsedTime()), errorMessage, ex});
    }

    protected void addErrorLog(Integer errorCode, String errorMessage, String exceptionType) {
        log.error("[Error] | Code: {} | Type: {} | Path: {} | Elapsed time: {} ms | Message: {}", new Object[]{errorCode, exceptionType, this.webUtil.getRequestUri(), StructuredArguments.v("elapsed_time", this.webUtil.getElapsedTime()), errorMessage});
    }
}
