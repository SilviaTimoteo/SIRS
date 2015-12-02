package SIRS.ws;
import java.security.Key;

import javax.jws.*;
import sirs.ws.*;
@WebService(endpointInterface="SIRS.ws.Server")
public class ServerImpl implements Server {
	Key keyServer =ServerMain.keySessionDB;
	ServerDB port = ConnectionServerDB.getPortServerDB();
	public byte[] login(int userID, byte[] message) {
		
		return null;
	}

	public byte[] getRegistries(int userID, byte[] message) {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] getRegistryByDate(int userID, byte[] message) {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] getRegistryBySpeciality(int userID, byte[] message) {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] sendChallenge(int userID, byte[] message) {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] checkChallenge(int userID, byte[] message) {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] logon(int userID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
