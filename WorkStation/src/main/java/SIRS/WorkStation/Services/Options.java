package SIRS.WorkStation.Services;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Options {
	Scanner input = new Scanner(System.in);
	int option;
	SecondaryFunctions functions = new SecondaryFunctions();
	boolean noBack = true;
	String user;
	
	public Options(String username){
		user = username;
	}
	
	public boolean showOptions(){ 
		functions.writeToScreen("\n\n[WORKSTATION]\n\n");

		functions.writeToScreen("Escolha uma das opções:\n");
		functions.writeToScreen("1) Pedir registo médico\n");
		functions.writeToScreen("2) Adicionar registo médico\n");
		functions.writeToScreen("0) Terminar sessão\n");
		
		try{
		option = input.nextInt();
		input.nextLine();
		}catch(InputMismatchException e){
			functions.writeToScreen(String.valueOf(option));
			option = -1;
		}
		switch(option){
		 	case 1:
				while(noBack){
					AskMR mr = new AskMR(user);
					noBack = mr.showOptions();
				}
				break;
		 	case 2:
				while(noBack){
					AddMR mr = new AddMR(user);
					noBack = mr.showOptions();
				}
				break;
		 	case 0:
				functions.writeToScreen("\n\nTerminando sessão...\n\n");
		
				return false;
			default:
				functions.writeToScreen("\n Opção inválida!\n");


		}
		
	
		return true;
	
	}
	
	

}
