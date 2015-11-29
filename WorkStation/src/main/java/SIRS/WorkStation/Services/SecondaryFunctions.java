package SIRS.WorkStation.Services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SecondaryFunctions {
	List<String> specialities = Arrays.asList("Cardiologia", "Neurologia", "Ortopedia");
	
	public void writeToScreen(String output){
		System.out.print(output);
	}
	
	public int showSpecialities(){
		int op;
		int numberOfSpecialities = specialities.size();
		
		Scanner input = new Scanner(System.in);

		
		writeToScreen("Escolha uma especialidade:\n\n");
		writeToScreen("1)" +specialities.get(0)+ "\n");
		writeToScreen("2)" +specialities.get(1)+ "\n");
		writeToScreen("3)" +specialities.get(2)+ "\n");
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
	
	public String getCurrentDate(){
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = String.valueOf(sdf.format(cal.getTime()));
        return date;  
	}
	
	public String getSpeciality(int spec){
		return specialities.get(spec-1);
	}


	public String getCurrentTime() {
	        Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	        String time = String.valueOf(sdf.format(cal.getTime()));
	        return time;
	}
	

	
}
