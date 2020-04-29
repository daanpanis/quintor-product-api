package nl.quintor.dpanis.productapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductExistsException extends RuntimeException {

    public ProductExistsException() {
    }

    public ProductExistsException(String message) {
        super(message);
    }

    public ProductExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductExistsException(Throwable cause) {
        super(cause);
    }

    public ProductExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
