package SIRS.WorkStation.Services;

import java.io.IOException;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import sirs.ws.Server;
import SIRS.CryptoTools.CipherFunctions;
import SIRS.CryptoTools.FunctionsXML;
import SIRS.CryptoTools.RequestsXML;
import SIRS.WorkStation.App;

public class AddMR {

	Scanner input = new Scanner(System.in);
	int numberOfSpecialities = 3;
	SecondaryFunctions functions = new SecondaryFunctions();
	String patient, entry;
	int speciality = -1;
	boolean invalid = true;
	String op;
	RequestsXML request = new RequestsXML();
	Document rDoc = null;
	String user;
	Server port = null;
	int docId;
	byte[] docCipher = null;
	byte[] result = null;
	String toReturn = null;
	
	
	public AddMR(Server p, String username){
		user = username;
		port = p;
		docId = Integer.parseInt(username); 
	}
	
	public boolean showOptions() {
		
		functions.writeToScreen("\n\n  [Add medical record]\n\n");
		while(speciality == -1){
	 		speciality = functions.showSpecialities();
	 	}
 		if(speciality == 0){
 			return false;
 		}
	 	functions.writeToScreen("Patient's name: ");
		patient = input.nextLine();
		
	 	functions.writeToScreen("Medical record entry: ");
		entry = input.nextLine();
		while(invalid){
			functions.writeToScreen("Do you want to cancel (press n) or send (press y)?");
			op = input.nextLine();
			if(op.equalsIgnoreCase("y") || op.equalsIgnoreCase("n")) invalid=false;
			else{ 
				functions.writeToScreen("Invalid option!\n");
				invalid = true;
			}
		}
		if(op.equalsIgnoreCase("y")) {
			
			rDoc = request.createDoc();
			rDoc = request.setPatient(rDoc, patient);
			rDoc = request.setDoctor(rDoc, user);
			rDoc = request.setDate(rDoc, functions.getCurrentDate());
			rDoc = request.setSpeciality(rDoc, functions.getSpeciality(speciality));
			rDoc = request.setTimestamp(rDoc, functions.getCurrentTime());
			rDoc = request.setEntry(rDoc, entry);
			byte[] iv0 = CipherFunctions.ivGenerator();
			docCipher = CipherFunctions.cipher(FunctionsXML.XMLtoBytes(rDoc), App.key,iv0);
			try{
				result = port.addRegistryReq(docId, docCipher, iv0);
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				return false;
			}
	 		
	 		toReturn = (new RequestsXML()).getEntry(FunctionsXML.BytesToXML(CipherFunctions.decipher(result, App.key, iv0)));
			
	 		if(toReturn.equals("true"))functions.writeToScreen("SEND WITH SUCCESS\n");
	 		else functions.writeToScreen("CANCELED\n");
			//functions.writeToScreen("ENVIADO\n");
			//return false;
	
		}
		else functions.writeToScreen("CANCELED\n");
		return false;

	}
	
	
}
