package SIRS.ws;
import javax.jws.*;

@WebService
public interface Server {
	 @WebMethod byte[] login(int userID, byte[] message);
	 @WebMethod byte[] getRegistries(int userID, byte[] message);
	 @WebMethod byte[] getRegistryByDate(int userID, byte[] message);
	 @WebMethod byte[] getRegistryBySpeciality(int userID, byte[] message);
	 @WebMethod byte[] sendChallenge(int userID, byte[]message);
	 @WebMethod byte[] checkChallenge(int userID,byte[] message);
	 @WebMethod byte[] addRegistryReq(int userID, byte[] message);
	 @WebMethod byte[] logon(int userID);
}
 