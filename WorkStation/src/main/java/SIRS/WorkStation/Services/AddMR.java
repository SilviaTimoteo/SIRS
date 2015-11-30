package SIRS.WorkStation.Services;

import java.io.IOException;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import SIRS.CryptoTools.RequestsXML;

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
	
	public AddMR(String username){
		user = username;
	}
	
	public boolean showOptions() {
		
		functions.writeToScreen("\n\n  [Adição de registo]\n\n");
		while(speciality == -1){
	 		speciality = functions.showSpecialities();
	 	}
 		if(speciality == 0){
 			return false;
 		}
	 	functions.writeToScreen("Nome do paciente: ");
		patient = input.nextLine();
		
	 	functions.writeToScreen("Entrada do registo: ");
		entry = input.nextLine();
		while(invalid){
			functions.writeToScreen("Deseja cancelar (prima n) ou enviar (prima y)?");
			op = input.nextLine();
			if(op.equalsIgnoreCase("y") || op.equalsIgnoreCase("n")) invalid=false;
			else{ 
				functions.writeToScreen("OPÇÃO INVÁLIDA\n");
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
			functions.writeToScreen("ENVIADO\n");
	
	//----------IMPRIMIR O DOC CRIADO, APAGAR DEPOIS ESTE CODIGO-------------------------//	    
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
	        try {
				xmlOutput.output(rDoc, System.out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}       
	//-----------------------------------------------------------------------------------//
			
		}
		else functions.writeToScreen("CANCELED\n");
		return false;

	}
	
	
}
