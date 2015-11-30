package SIRS.DBServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.concurrent.TimeUnit.*;

public class SQLVerify {
	static String myDriver = "com.mysql.jdbc.Driver";
	static String myUrl = "jdbc:mysql://localhost:3306/hospitalRecords?allowMultiQueries=true&relaxAutoCommit=true";
	static String myUser = "root";
	static String myPass = "rootroot";
	static long tolerance = MILLISECONDS.convert(10, MINUTES);

	public static String verifyAllReg(String patient, String doctorId, String timestamp){
		String answer = null;
		if(doctorExists(doctorId) && patientExists(patient) && doctorInEmergency(doctorId) 
				&& validTimestamp(timestamp)){
			answer = SQLthings.BDgetAllRegs(patient);
		}
		return answer;
	}
	
	public static String verifyRegBySpeciality(String patient, String doctorId, String speciality, String timestamp){
		String answer = null;
		if(doctorExists(doctorId) && patientExists(patient) && isDoctorOfPatient(doctorId, patient)
				&& doctorHasSpeciality(doctorId, speciality) && validTimestamp(timestamp)){
			answer = SQLthings.BDgetRegsBySpeciality(patient, speciality);
		}
		return answer;
	}
	
	public static String verifyRegsByDate(String patient, String doctorId, String beforeafter, String date, String timestamp){
		String answer = null;
		String speciality = getSpeciality(doctorId);
		if(doctorExists(doctorId) && patientExists(patient) && isDoctorOfPatient(doctorId, patient)
				&& doctorHasSpeciality(doctorId, speciality) && validTimestamp(timestamp)){
			answer = SQLthings.BDgetRegsByDate(patient, speciality, beforeafter, date);
		}
		return answer;
	}
	
	public static void verifyAddReg(String patient, String doctorId, String speciality, String entry, String date, String timestamp){
		if(doctorExists(doctorId) && patientExists(patient) && isDoctorOfPatient(doctorId, patient)
				&& doctorHasSpeciality(doctorId, speciality) && validTimestamp(timestamp)){
			SQLthings.BDaddReg(patient, date, doctorId, speciality, entry);
		}
	}
	
	
	/**
	 * Check if doctor exists
	 * @parameter doctorId (Did)
	 * @return boolean
	 */
	public static boolean doctorExists(String doctorId){
		Connection conn=null;
		Statement stmt=null;
		ResultSet res=null;
		boolean result = false;
		try {
			Class.forName(myDriver);

			conn = DriverManager.getConnection(myUrl,myUser,myPass);

			stmt = conn.createStatement();
			res = stmt.executeQuery
					("select name from hospital_staff where id = '"+doctorId+"'");

			while(res.next()){
				result = true;
			}
			
			res.close(); 
			stmt.close();
			conn.close();
			
			return result;
						
		}
		catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		catch (Throwable ignore) {}
		return false;
	}
	
	/**
	 * Check if patient exists
	 * @parameter patient (name)
	 * @return boolean
	 */
	public static boolean patientExists(String patient){
		Connection conn=null;
		Statement stmt=null;
		ResultSet res=null;
		boolean result = false;
		try {
			Class.forName(myDriver);

			conn = DriverManager.getConnection(myUrl,myUser,myPass);

			stmt = conn.createStatement();
			res = stmt.executeQuery
					("select id from patients where name = '"+patient+"'");

			while(res.next()){
				result = true;
			}
			
			res.close(); 
			stmt.close();
			conn.close();
			
			return result;
						
		}
		catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		catch (Throwable ignore) {}
		return false;
	}
	
	/**
	 * Check if doctor has patient
	 * @parameter doctorId (Did)
	 * @parameter patient (name)
	 * @return boolean
	 */
	public static boolean isDoctorOfPatient(String doctorId, String patient){
		Connection conn=null;
		Statement stmt=null;
		ResultSet res=null;
		String Pid = null;
		boolean result = false;
		try {
			Class.forName(myDriver);

			conn = DriverManager.getConnection(myUrl,myUser,myPass);

			stmt = conn.createStatement();
			res = stmt.executeQuery
					("select id from patients where name = '"+patient+"'");

			res.next();
			Pid = res.getString("id");
			
			res = stmt.executeQuery
					("select * from patients_doctors where Pid = '"+Pid+"' and Did = '"+doctorId+"'");
			
			
			while(res.next()){
				result = true;
			}
			
			res.close(); 
			stmt.close();
			conn.close();
			
			return result;
						
		}
		catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		catch (Throwable ignore) {}
		return false;
	}
	
	/**
	 * Check if doctor has specialty
	 * @parameter doctorId (Did)
	 * @parameter specialty (name)
	 * @return boolean
	 */
	public static boolean doctorHasSpeciality(String doctorId, String speciality){
		Connection conn=null;
		Statement stmt=null;
		ResultSet res=null;
		boolean result = false;
		try {
			Class.forName(myDriver);

			conn = DriverManager.getConnection(myUrl,myUser,myPass);

			stmt = conn.createStatement();
			res = stmt.executeQuery
					("select name from hospital_staff where id = '"+doctorId+"' and speciality = '"+speciality+"'");

			while(res.next()){
				result = true;
			}
			
			res.close(); 
			stmt.close();
			conn.close();
			
			return result;
						
		}
		catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		catch (Throwable ignore) {}
		return false;
	}
	
	/**
	 * Check if doctor is in emergency context
	 * @parameter doctorId (Did)
	 * @return boolean
	 */
	public static boolean doctorInEmergency(String doctorId){
		Connection conn=null;
		Statement stmt=null;
		ResultSet res=null;
		String context = null;
		boolean result = false;
		try {
			Class.forName(myDriver);

			conn = DriverManager.getConnection(myUrl,myUser,myPass);

			stmt = conn.createStatement();
			res = stmt.executeQuery
					("select context from staff_context where id = '"+doctorId+"'");

			while(res.next()){
				context = res.getString("context");
			}
			
			if(!context.equals("normal")) result = true;
			
			
			res.close(); 
			stmt.close();
			conn.close();
			
			return result;
						
		}
		catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		catch (Throwable ignore) {}
		return false;
	}
	
	/**
	 * Check if timestamp is valid (tolerance of 10 minutes)
	 * @parameter timestamp (HH:mm:SS)
	 * @return boolean
	 */
	public static boolean validTimestamp(String timestamp){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:SS");
		String now = sdf.format(Calendar.getInstance().getTime());
		Date start= null;
		Date nowTime = null;
		
		try {
			start = sdf.parse(timestamp);
			nowTime = sdf.parse(now);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long duration = nowTime.getTime() - start.getTime();
		if(duration < tolerance){
			return true;
		}
		return false;
	}

	/**
	 * Returns doctors specialty
	 * @parameter doctorId (Did)
	 * @return specialty (String)
	 */
	public static String getSpeciality(String doctorId){
		Connection conn=null;
		Statement stmt=null;
		ResultSet res=null;
		String result = null;
		try {
			Class.forName(myDriver);

			conn = DriverManager.getConnection(myUrl,myUser,myPass);

			stmt = conn.createStatement();
			res = stmt.executeQuery
					("select speciality from hospital_staff where id = '"+doctorId+"'");

			while(res.next()){
				result = res.getString("speciality");
			}
			
			res.close(); 
			stmt.close();
			conn.close();
			
			return result;
						
		}
		catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		catch (Throwable ignore) {}
		return result;
		
	}
	
}
