package uz.davrbank.officialorder.exception;

/**
 * Base exception class,parent class for other custom exception classes
 *
 * @author Abduvohid Isroilov
 */
public class BaseException extends RuntimeException {

    public BaseException(String s) {
        super(s);
    }

    public BaseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
