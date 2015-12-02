package SIRS.exceptions;

public class DoctorDoesntExist extends RuntimeException {

		   private static final long serialVersionUID = 1L;
		   @Override
		    public String getMessage() {
				return ">>>> The doctor doesn't exist\n";
		    }

}
