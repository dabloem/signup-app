package org.company.service;

/**
 * If the Signup Request is not found in the caches, a SingupRequestNotFoundException will be thrown.
 *
 * Different clients should have their solution for handling this exception.
 *
 * @author hantsy
 */
public class SignupRequestNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -5519389339321081199L;

    public SignupRequestNotFoundException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public SignupRequestNotFoundException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public SignupRequestNotFoundException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public SignupRequestNotFoundException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}
