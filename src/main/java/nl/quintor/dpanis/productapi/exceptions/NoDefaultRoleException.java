package nl.quintor.dpanis.productapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "No default role is specified for new users")
public class NoDefaultRoleException extends RuntimeException {

    public NoDefaultRoleException() {
    }

    public NoDefaultRoleException(String message) {
        super(message);
    }

    public NoDefaultRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDefaultRoleException(Throwable cause) {
        super(cause);
    }

    public NoDefaultRoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
