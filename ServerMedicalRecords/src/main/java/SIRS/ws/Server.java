package SIRS.ws;
import javax.jws.*;

@WebService
public interface Server {
	 @WebMethod byte[] createSessionKeyDH(byte[] parameters);
	 @WebMethod byte[] login(byte[] message);
	 @WebMethod byte[] getRegistries(byte[] message);
	 @WebMethod byte[] getRegistryByDate(byte[] message);
	 @WebMethod byte[] getRegistryBySpeciality(byte[] message);	 
}
 