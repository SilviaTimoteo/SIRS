package SIRS.ws;
import javax.jws.*;

import SIRS.exceptions.*;



@WebService
public interface ServerDB {
	@WebMethod byte[] sendChallenge(byte[]message, byte[] iv);
	@WebMethod byte[] checkChallenge(byte[] message, byte[] iv) throws ConnectionCorrupted;
	@WebMethod byte[] loginDB(byte[] message, byte[] iv);
	@WebMethod byte[] getRegistriesDB(byte[] message, byte[] iv) throws  DoctorDoesntExist, PatientDoesntExist, EmergencyDoctor, InvalidTimestamp;
	@WebMethod byte[] getRegistryByDateDB(byte[] message, byte[] iv) throws DoctorDoesntExist, PatientDoesntExist, DoctorNotOfPatient, DoctorSpecialty, InvalidTimestamp;
	@WebMethod byte[] getRegistryBySpecialityDB(byte[] message, byte[] iv) throws DoctorDoesntExist, PatientDoesntExist,  DoctorNotOfPatient, DoctorSpecialty, InvalidTimestamp ;
	@WebMethod byte[] generateSessionKeyDoctor(int doctorID, byte[] message, byte[] iv) throws DoctorDoesntExist ;
	@WebMethod byte[] addRegistry (byte [] message,byte[] iv)throws DoctorDoesntExist, PatientDoesntExist,  DoctorNotOfPatient, DoctorSpecialty, InvalidTimestamp;
}
