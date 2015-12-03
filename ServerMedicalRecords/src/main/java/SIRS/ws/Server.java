package SIRS.ws;
import javax.jws.*;

import SIRS.exceptions.ConnectionCorrupted;
import SIRS.exceptions.DoctorDoesntExist;
import SIRS.exceptions.DoctorNotOfPatient;
import SIRS.exceptions.DoctorSpecialty;
import SIRS.exceptions.EmergencyDoctor;
import SIRS.exceptions.InvalidTimestamp;
import SIRS.exceptions.PatientDoesntExist;

@WebService
public interface Server {
	 @WebMethod byte[] login(int userID, byte[] message) throws DoctorDoesntExist;
	 @WebMethod byte[] getRegistries(int userID, byte[] message) throws  DoctorDoesntExist, PatientDoesntExist, EmergencyDoctor, InvalidTimestamp;
	 @WebMethod byte[] getRegistryByDate(int userID, byte[] message) throws  DoctorDoesntExist, PatientDoesntExist, EmergencyDoctor, InvalidTimestamp;
	 @WebMethod byte[] getRegistryBySpeciality(int userID, byte[] message) throws DoctorDoesntExist, PatientDoesntExist, DoctorNotOfPatient, DoctorSpecialty, InvalidTimestamp ;
	 @WebMethod byte[] sendChallenge(int userID, byte[]message);
	 @WebMethod byte[] checkChallenge(int userID,byte[] message) throws ConnectionCorrupted;
	 @WebMethod byte[] addRegistryReq(int userID, byte[] message)throws DoctorDoesntExist, PatientDoesntExist,  DoctorNotOfPatient, DoctorSpecialty, InvalidTimestamp;
	 @WebMethod byte[] logout(int userID);
}
 