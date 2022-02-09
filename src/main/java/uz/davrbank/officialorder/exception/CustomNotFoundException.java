package uz.davrbank.officialorder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.davrbank.officialorder.exception.handler.ApiErrorMessages;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ApiErrorMessages.NOT_FOUND)
public class CustomNotFoundException extends BaseException {

    public CustomNotFoundException() {
        this(ApiErrorMessages.NOT_FOUND);
    }

    public CustomNotFoundException(String s) {
        super(s);
    }

}
