package SIRS.exceptions;

public class InvalidTimestamp extends RuntimeException {

		   private static final long serialVersionUID = 1L;
		   @Override
		    public String getMessage() {
				return ">>>> TIMEOUT: the timestamp sent is invalid\n";
		    }

}
