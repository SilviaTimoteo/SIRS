package SIRS.ws;
import javax.jws.*;

@WebService
public interface ServerDB {
	 @WebMethod byte[] createSessionKeyDHDB(byte[] parameters);
	 @WebMethod byte[] loginDB(byte[] message);
	 @WebMethod byte[] getRegistriesDB(byte[] message);
	 @WebMethod byte[] getRegistryByDateDB(byte[] message);
	 @WebMethod byte[] getRegistryBySpecialityDB(byte[] message);
}
