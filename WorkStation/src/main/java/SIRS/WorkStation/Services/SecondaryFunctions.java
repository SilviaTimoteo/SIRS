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
	List<String> specialities = Arrays.asList("Cardiology", "Neurology", "Orthopedics");
	
	public void writeToScreen(String output){
		System.out.print(output);
	}
	
	public int showSpecialities(){
		int op;
		int numberOfSpecialities = specialities.size();
		
		Scanner input = new Scanner(System.in);

		
		writeToScreen("Choose one specialty:\n\n");
		writeToScreen("1)" +specialities.get(0)+ "\n");
		writeToScreen("2)" +specialities.get(1)+ "\n");
		writeToScreen("3)" +specialities.get(2)+ "\n");
		writeToScreen("0) Go back\n");

		try{
			op = input.nextInt();
			input.nextLine();

		}catch(InputMismatchException e){
			op = -1;
		}
		if(op<0 || op > numberOfSpecialities){
			writeToScreen("\n Invalid option!\n");
			op = -1;
		}
		
		
		//input.close();
		return op;
	}
	
	public String getDate(){
		Calendar cal = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Scanner input = new Scanner(System.in);
		Date date = null;
		try {
			date = format.parse(input.next());
		} catch (ParseException e) {
			writeToScreen("\n Invalid date format\n");
			
			return null;
		}
		
		if(date.after(cal.getTime())){
			writeToScreen("\n Invalid date\n");
			return null;
		}
		cal.setTime(date);
		
		if((cal.get(Calendar.DAY_OF_MONTH)>31 && cal.get(Calendar.MONTH)>12)){
			writeToScreen("\n Invalid date\n");
			return null;
		}
		
		return format.format(date);
	}
	
	public String getCurrentDate(){
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = sdf.format(cal.getTime());
        return date;  
	}
	
	public String getSpeciality(int spec){
		return specialities.get(spec-1);
	}


	public String getCurrentTime() {
	        Calendar cal = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	        String time = sdf.format(cal.getTime());
	        return time;
	}
	

	
}
