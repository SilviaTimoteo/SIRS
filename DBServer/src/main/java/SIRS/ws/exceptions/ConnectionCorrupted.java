package SIRS.ws.exceptions;

public class ConnectionCorrupted extends RuntimeException{
	   private static final long serialVersionUID = 1L;
	   @Override
	    public String getMessage() {
			return "Connection Untrusted \n Authentication Failed \n";
	    }

}
