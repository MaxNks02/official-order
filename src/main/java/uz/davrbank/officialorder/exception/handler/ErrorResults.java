package uz.davrbank.officialorder.exception.handler;

import java.io.Serializable;

/**
 * Error results class for the error responses
 *
 * @author Abduvohid Isroilov
 */
public class ErrorResults implements Serializable {

    private static final long serialVersionUID = -8457257838264102466L;

    private String errorMessage;
    private Integer errorCode;

    public ErrorResults(String errorMessage, Integer errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}