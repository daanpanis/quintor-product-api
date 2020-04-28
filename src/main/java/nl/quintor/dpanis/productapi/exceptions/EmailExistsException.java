package nl.quintor.dpanis.productapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Email already exists")
public class EmailExistsException extends RuntimeException {

    public EmailExistsException() {
    }

    public EmailExistsException(String message) {
        super(message);
    }

    public EmailExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailExistsException(Throwable cause) {
        super(cause);
    }

    public EmailExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
