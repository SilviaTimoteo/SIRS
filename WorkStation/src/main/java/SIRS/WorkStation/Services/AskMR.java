package SIRS.WorkStation.Services;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import SIRS.CryptoTools.RequestsXML;

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
	
	public AskMR(String username){
		user = username;
	}
	
	public boolean showOptions(){ 
			functions.writeToScreen("\n [Pedido de Registo]\n\n");
			functions.writeToScreen("Escolha uma das opções:\n\n");
			functions.writeToScreen("1) Todos os registos\n");
			functions.writeToScreen("2) Por especialidade\n");
			functions.writeToScreen("3) Antes da data X\n");
			functions.writeToScreen("4) Depois da data X\n");
			functions.writeToScreen("0) Voltar atrás\n");
	
			try{
			option = input.nextInt();
			input.nextLine();
			}catch(InputMismatchException e){
				option = -1;
			}
			switch(option){
			 	case 1:
					functions.writeToScreen("\n [Pedido de Registo]\n");

			 		functions.writeToScreen("\n  [Todos os registos]\n\n");
			 		functions.writeToScreen("Nome do paciente: ");
			 		patient = input.nextLine();
			 		
			 		rDoc = request.createDoc();
					rDoc = request.setPatient(rDoc, patient);
					rDoc = request.setDoctor(rDoc, user);
					rDoc = request.setTimestamp(rDoc, functions.getCurrentTime());
			
					functions.writeToScreen("\n Pedido Enviado\n\n");

					break;
			 	case 2:
					functions.writeToScreen("\n [Pedido de Registo]\n");

					functions.writeToScreen("\n  [Por especialidade]\n\n");
					while(speciality == -1){
				 		speciality = functions.showSpecialities();
				 	}
			 		if(speciality == 0){
			 			return true;
			 		}
				 	functions.writeToScreen("Nome do paciente: ");
					patient = input.nextLine();
					
					rDoc = request.createDoc();
					rDoc = request.setPatient(rDoc, patient);
					rDoc = request.setDoctor(rDoc, user);
					rDoc = request.setSpeciality(rDoc, functions.getSpeciality(speciality));
					rDoc = request.setTimestamp(rDoc, functions.getCurrentTime());
					
					
					functions.writeToScreen("\n Pedido Enviado\n\n");

					break;
			 	case 3:
					functions.writeToScreen("\n [Pedido de Registo]\n");

			 		functions.writeToScreen("\n  [Antes da data X]\n\n");
					while(data==null){
						functions.writeToScreen("Insira a data (dd/MM/aaaa):\n");
						data = functions.getDate();
					}
				 	functions.writeToScreen("Nome do paciente: ");
					patient = input.nextLine();
					
					rDoc = request.createDoc();
					rDoc = request.setPatient(rDoc, patient);
					rDoc = request.setDoctor(rDoc, user);
					rDoc = request.setDate(rDoc, data);
					rDoc = request.setTimestamp(rDoc, functions.getCurrentTime());
					rDoc = request.setBeforeAfter(rDoc, "B");
					
					functions.writeToScreen("\n Pedido Enviado\n\n");
					break;
			 	case 4:
					functions.writeToScreen("\n [Pedido de Registo]\n");

			 		functions.writeToScreen("\n  [Depois da data X]\n\n");
					while(data==null){
				 		functions.writeToScreen("Insira a data (dd/MM/aaaa): ");
						data = functions.getDate();
					}
				 	functions.writeToScreen("Nome do paciente: ");
					patient = input.nextLine();
					
					rDoc = request.createDoc();
					rDoc = request.setPatient(rDoc, patient);
					rDoc = request.setDoctor(rDoc, user);
					rDoc = request.setDate(rDoc, data);
					rDoc = request.setTimestamp(rDoc, functions.getCurrentTime());
					rDoc = request.setBeforeAfter(rDoc, "A");
					
					functions.writeToScreen("\n Pedido Enviado\n\n");
					break;
			 	case 0:

					break;
				default:
					functions.writeToScreen("\n Opção inválida!\n");
					
					return true;
			}
		
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
			return false;

	}
	
}
