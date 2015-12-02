package SIRS.ws.exceptions;

public class EmergencyDoctor extends RuntimeException {

		   private static final long serialVersionUID = 1L;
		   @Override
		    public String getMessage() {
				return "The doctor is not in emergency service, so he can't access all records\n";
		    }

}
