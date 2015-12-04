package SIRS.WorkStation.Services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import sirs.ws.Server;
import SIRS.CryptoTools.CipherFunctions;
import SIRS.CryptoTools.FunctionsXML;
import SIRS.CryptoTools.RequestsXML;
import SIRS.WorkStation.App;

public class AskMR {

	int option;
	String patient;
	int speciality = -1;
	boolean noBack = true;
	String data = null;
	Scanner input = new Scanner(System.in);
	SecondaryFunctions functions = new SecondaryFunctions();
	String user;
	RequestsXML request = new RequestsXML();
	Document rDoc = null;
	Server port = null;
	int docId;
	byte[] docCipher = null;
	byte[] result = null;
	String toReturn = null;
	
	
	public AskMR(Server p, String username){
		user = username;
		port = p;
		docId = Integer.parseInt(username); 
	}
	
	public boolean showOptions(){ 
			functions.writeToScreen("\n [Medical Records Query]\n\n");
			functions.writeToScreen("Choose one of the options:\n\n");
			functions.writeToScreen("1) All records\n");
			functions.writeToScreen("2) By specialty\n");
			functions.writeToScreen("3) Before date X\n");
			functions.writeToScreen("4) After date X\n");
			functions.writeToScreen("0) Go back\n");
	
			try{
			option = input.nextInt();
			input.nextLine();
			}catch(InputMismatchException e){
				option = -1;
			}
			switch(option){
			 	case 1:
					functions.writeToScreen("\n [Medical Records Query]\n");

			 		functions.writeToScreen("\n  [All records]\n\n");
			 		functions.writeToScreen("Patient's name: ");
			 		patient = input.nextLine();
			 		
			 		rDoc = request.createDoc();
					rDoc = request.setPatient(rDoc, patient);
					rDoc = request.setDoctor(rDoc, user);
					rDoc = request.setTimestamp(rDoc, functions.getCurrentTime());
			 		byte[] iv0 = CipherFunctions.ivGenerator();
					docCipher = CipherFunctions.cipher(FunctionsXML.XMLtoBytes(rDoc), App.key,iv0);
					try{
						result = port.getRegistries(docId, docCipher,iv0);
					}
					catch(Exception e){
						System.out.println(e.getMessage());
						return true; 
					}	 		
			 		
			 		toReturn = (new RequestsXML()).getEntry(FunctionsXML.BytesToXML(CipherFunctions.decipher(result, App.key,iv0)));
			 		
					functions.writeToScreen(toReturn);
			
//					functions.writeToScreen("\n Pedido Enviado\n\n");

					break;
			 	case 2:
					functions.writeToScreen("\n [Medical Records Query]\n");

					functions.writeToScreen("\n  [By specialty]\n\n");
					while(speciality == -1){
				 		speciality = functions.showSpecialities();
				 	}
			 		if(speciality == 0){
			 			return true;
			 		}
				 	functions.writeToScreen("Patient's name: ");
					patient = input.nextLine();
					
					rDoc = request.createDoc();
					rDoc = request.setPatient(rDoc, patient);
					rDoc = request.setDoctor(rDoc, user);
					rDoc = request.setSpeciality(rDoc, functions.getSpeciality(speciality));
					rDoc = request.setTimestamp(rDoc, functions.getCurrentTime());
					
			 		byte[] iv1 = CipherFunctions.ivGenerator();
					docCipher = CipherFunctions.cipher(FunctionsXML.XMLtoBytes(rDoc), App.key, iv1);
					try{
						result = port.getRegistryBySpeciality(docId, docCipher, iv1);
					}
					catch(Exception e){
						System.out.println(e.getMessage());
						return true; 
					}
			 		
			 		toReturn = (new RequestsXML()).getEntry(FunctionsXML.BytesToXML(CipherFunctions.decipher(result, App.key,iv1)));
			 		
					functions.writeToScreen(toReturn);
					
//					functions.writeToScreen("\n Pedido Enviado\n\n");

					break;
			 	case 3:
					functions.writeToScreen("\n [Medical Records Query]\n");

			 		functions.writeToScreen("\n  [Before date X]\n\n");
					while(data==null){
						functions.writeToScreen("Insert date (yyyy-MM-dd):\n");
						data = functions.getDate();
					}
				 	functions.writeToScreen("Patient's name: ");
					patient = input.nextLine();
					
					rDoc = request.createDoc();
					rDoc = request.setPatient(rDoc, patient);
					rDoc = request.setDoctor(rDoc, user);
					rDoc = request.setDate(rDoc, data);
					rDoc = request.setTimestamp(rDoc, functions.getCurrentTime());
					rDoc = request.setBeforeAfter(rDoc, "B");
			 		byte[] iv2 = CipherFunctions.ivGenerator();
					docCipher = CipherFunctions.cipher(FunctionsXML.XMLtoBytes(rDoc), App.key,iv2);
					try{
						result = port.getRegistryByDate(docId, docCipher, iv2);
					}
					catch(Exception e){
						System.out.println(e.getMessage());
						return true; 
					}
			 		toReturn = (new RequestsXML()).getEntry(FunctionsXML.BytesToXML(CipherFunctions.decipher(result, App.key, iv2)));
			 		
					functions.writeToScreen(toReturn);
					
//					functions.writeToScreen("\n Pedido Enviado\n\n");
					
					break;
			 	case 4:
					functions.writeToScreen("\n [Medical Records Query]\n");

			 		functions.writeToScreen("\n  [After date X]\n\n");
					while(data==null){
				 		functions.writeToScreen("Insert date (yyyy-MM-dd): ");
						data = functions.getDate();
					}
				 	functions.writeToScreen("Patient's name: ");
					patient = input.nextLine();
					
					rDoc = request.createDoc();
					rDoc = request.setPatient(rDoc, patient);
					rDoc = request.setDoctor(rDoc, user);
					rDoc = request.setDate(rDoc, data);
					rDoc = request.setTimestamp(rDoc, functions.getCurrentTime());
					rDoc = request.setBeforeAfter(rDoc, "A");
					
			 		byte[] iv3 = CipherFunctions.ivGenerator();

					docCipher = CipherFunctions.cipher(FunctionsXML.XMLtoBytes(rDoc), App.key, iv3);
					try{
						result = port.getRegistryByDate(docId, docCipher, iv3);
					}
					catch(Exception e){
						System.out.println(e.getMessage());
						return true; 
					}
			 		
			 		
			 		toReturn = (new RequestsXML()).getEntry(FunctionsXML.BytesToXML(CipherFunctions.decipher(result, App.key, iv3)));
			 		
					functions.writeToScreen(toReturn);
					
//					functions.writeToScreen("\n Pedido Enviado\n\n");
					
					break;
			 	case 0:

					break;
				default:
					functions.writeToScreen("\n Invalid option!\n");
					
					return true;
			}
			return false;

	}
	
}
