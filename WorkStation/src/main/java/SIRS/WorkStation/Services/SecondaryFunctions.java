package SIRS.WorkStation.Services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SecondaryFunctions {
	
	public void writeToScreen(String output){
		System.out.print(output);
	}
	
	public int showSpecialities(){
		int op;
		int numberOfSpecialities = 3;
		Scanner input = new Scanner(System.in);

		
		writeToScreen("Escolha uma especialidade:\n\n");
		writeToScreen("1) Cardiologia \n");
		writeToScreen("2) Neurologia \n");
		writeToScreen("3) Ortopedia \n");
		writeToScreen("0) Voltar atrás\n");

		try{
			op = input.nextInt();
			input.nextLine();

		}catch(InputMismatchException e){
			op = -1;
		}
		if(op<0 || op > numberOfSpecialities){
			writeToScreen("\n Opção inválida!\n");
			op = -1;
		}
		
		
		//input.close();
		return op;
	}
	
	public String getDate(){
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Scanner input = new Scanner(System.in);
		Date date = null;
		try {
			date = format.parse(input.next());
		} catch (ParseException e) {
			writeToScreen("\n Formato inválido da data\n");
			
			return null;
		}
		
		//input.close();
		
		return format.format(date);
	}

	
}
