package uz.davrbank.officialorder.exception.handler;

/**
 * Several static custom error messages
 *
 * @author Abduvohid Isroilov
 */
public class ApiErrorMessages {

    public static final String BAD_REQUEST = "Invalid request. Please check request once more. ";
    public static final String NOT_FOUND = "Could not find requesting data. ";
    public static final String INTERNAL_SERVER_ERROR = "Error occurs while exchange data with database. Please try again later, after checking given error details. ";
    public static final String UNSUPPORTED_MEDIA_TYPE = "Error occurs cause coming unsupported media type. Please send shown body only. ";
    public static final String METHOD_NOT_ALLOWED = "Used not valid http method. Please check it. ";
    public static final String CREATE_DIRECTORY_ERROR = "Could not create the directory where the uploaded files will be stored. ";

}
