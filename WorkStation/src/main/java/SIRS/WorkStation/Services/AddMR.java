package SIRS.WorkStation.Services;

import java.util.Scanner;

public class AddMR {

	Scanner input = new Scanner(System.in);
	int numberOfSpecialities = 3;
	SecondaryFunctions functions = new SecondaryFunctions();
	String patient, entry;
	int speciality = -1;
	boolean invalid = true;
	String op;
	
	
	public boolean showOptions(){

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
		if(op.equalsIgnoreCase("y")) functions.writeToScreen("ENVIADO\n");
		else functions.writeToScreen("CANCELED\n");
		return false;

	}
	
	
}
