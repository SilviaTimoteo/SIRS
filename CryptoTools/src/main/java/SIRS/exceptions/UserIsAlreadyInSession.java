package SIRS.exceptions;

public class UserIsAlreadyInSession extends RuntimeException {

	   private static final long serialVersionUID = 1L;
	   @Override
	    public String getMessage() {
			return ">>>> The user is already in a session\n";
	    }
}
