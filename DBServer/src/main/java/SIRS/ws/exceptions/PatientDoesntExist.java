package SIRS.ws.exceptions;

public class PatientDoesntExist extends RuntimeException {

		   private static final long serialVersionUID = 1L;
		   @Override
		    public String getMessage() {
				return ">>>> The patient doesn't exist\n";
		    }

}
