package SIRS.ws;
import javax.jws.*;

import SIRS.exceptions.*;



@WebService
public interface ServerDB {
	@WebMethod byte[] sendChallenge(byte[]message);
	@WebMethod byte[] checkChallenge(byte[] message) throws ConnectionCorrupted;
	@WebMethod byte[] loginDB(byte[] message);
	@WebMethod byte[] getRegistriesDB(byte[] message) throws  DoctorDoesntExist, PatientDoesntExist, EmergencyDoctor, InvalidTimestamp;
	@WebMethod byte[] getRegistryByDateDB(byte[] message) throws DoctorDoesntExist, PatientDoesntExist, DoctorNotOfPatient, DoctorSpecialty, InvalidTimestamp;
	@WebMethod byte[] getRegistryBySpecialityDB(byte[] message) throws DoctorDoesntExist, PatientDoesntExist,  DoctorNotOfPatient, DoctorSpecialty, InvalidTimestamp ;
	@WebMethod byte[] generateSessionKeyDoctor(int doctorID, byte[] message) throws DoctorDoesntExist ;
	@WebMethod byte[] addRegistry (byte [] message)throws DoctorDoesntExist, PatientDoesntExist,  DoctorNotOfPatient, DoctorSpecialty, InvalidTimestamp;
}
