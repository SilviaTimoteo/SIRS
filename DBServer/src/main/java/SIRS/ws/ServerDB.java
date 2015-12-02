package SIRS.ws;
import javax.jws.*;

import SIRS.ws.exceptions.ConnectionCorrupted;

@WebService
public interface ServerDB {
	@WebMethod byte[] sendChallenge(byte[]message);
	@WebMethod byte[] checkChallenge(byte[] message) throws ConnectionCorrupted;
	@WebMethod byte[] loginDB(byte[] message);
	@WebMethod byte[] getRegistriesDB(byte[] message);
	@WebMethod byte[] getRegistryByDateDB(byte[] message);
	@WebMethod byte[] getRegistryBySpecialityDB(byte[] message);
	@WebMethod byte[] generateSessionKeyDoctor(int doctorID, byte[] message);
}
