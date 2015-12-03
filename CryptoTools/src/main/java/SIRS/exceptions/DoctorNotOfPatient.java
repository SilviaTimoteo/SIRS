package SIRS.exceptions;

public class DoctorNotOfPatient extends RuntimeException {

		   private static final long serialVersionUID = 1L;
		   @Override
		    public String getMessage() {
				return ">>>> The doctor is not one of the patients doctors\n";
		    }

}
