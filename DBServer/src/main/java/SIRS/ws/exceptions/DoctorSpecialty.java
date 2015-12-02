package SIRS.ws.exceptions;

public class DoctorSpecialty extends RuntimeException {

		   private static final long serialVersionUID = 1L;
		   @Override
		    public String getMessage() {
				return ">>>> The doctor is not of the wanted specialty\n";
		    }

}
