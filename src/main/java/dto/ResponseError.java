package dto;

import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ResponseError {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;

    private String error;
    private Collection<FieldError> errors;

    public ResponseError(String error, Collection<FieldError> errors) {
        this.error = error;
        this.errors = errors;
    }

    public static<T> ResponseError createFromValidation(Set<ConstraintViolation<T>> violations) {

        List<FieldError> list = violations.stream().map(cv -> new FieldError(cv.getPropertyPath().toString(), cv.getMessage())).toList();
        String message = "Validation error: " + list.toString();
        return new ResponseError(message, list);

    }

    public Response withStatusCode(int code) {

        return Response.status(code).entity(this).build();


    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Collection<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(Collection<FieldError> errors) {
        this.errors = errors;
    }
}
