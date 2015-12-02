package SIRS.DBServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLthings {
	static String myDriver = "com.mysql.jdbc.Driver";
	static String myUrl = "jdbc:mysql://localhost:3306/hospitalRecords?allowMultiQueries=true&relaxAutoCommit=true";
	static String myUser = "root";
	static String myPass = "rootroot";
	
	public static String BDgetAllRegs(String patient) {
		Connection conn=null;
		Statement stmt=null;
		ResultSet res=null;
		String medReg = "| Doc ID | Doc Name | Speciality | Date | Entry |\n";
		int numCol = 0;
		
		try {
			Class.forName(myDriver);

			conn = DriverManager.getConnection(myUrl,myUser,myPass);

			stmt = conn.createStatement();
			res = stmt.executeQuery
					("select md.Did, d.name, md.speciality, md.date_of_record, md.entry "
							+ "from medical_records as md, patients as p, hospital_staff as d "
							+ "where p.name='" +patient+"' and md.Pid=p.id and md.Did=d.id");
			
			while (res.next())  {
				numCol++;
				
				int Did = res.getInt("Did");
				String Dname = res.getString("name");
				String spec = res.getString("speciality");
				String date = res.getDate("date_of_record").toString();
				String entry = res.getString("entry");
				
				medReg += "| " + Did + " | " + Dname + " | " + spec + " | " + date + " | " + entry + " |\n";  
				//res.getBlob(numCol).free();

			}
			
			if (numCol==0) {
				System.out.println("The patient has no medical records\n");
				return "The patient has no medical records\n";
			}
			
			res.close(); 
			stmt.close();
			conn.close();
			
			System.out.println(medReg);
			return medReg;
			
		}
		catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		catch(IllegalThreadStateException e) { System.out.println("GOTCHAA!!!!");} 
		
		return "";
	}

	public static String BDgetRegsBySpeciality(String patient, String speciality){
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet res=null;
		String medReg = "| Doc ID | Doc Name | Speciality | Date | Entry |\n";
		int numCol = 0;
		
		try {
			Class.forName(myDriver);

			conn = DriverManager.getConnection(myUrl,myUser,myPass);

			stmt = conn.createStatement();
			res = stmt.executeQuery
					("select md.Did, d.name, md.speciality, md.date_of_record, md.entry "
							+ "from medical_records as md, patients as p, hospital_staff as d "
							+ "where p.name='" +patient+"' and md.Pid=p.id and md.Did=d.id "
							+ "and md.speciality='" +speciality+"' ");
			
			while (res.next())  {
				numCol++;
				
				int Did = res.getInt("Did");
				String Dname = res.getString("name");
				String spec = res.getString("speciality");
				String date = res.getDate("date_of_record").toString();
				String entry = res.getString("entry");
				
				medReg += "| " + Did + " | " + Dname + " | " + spec + " | " + date + " | " + entry + " |\n";  
				//res.getBlob(numCol).free();

			}
			
			if (numCol==0) {
				System.out.println("The patient has no medical records\n");
				return "The patient has no medical records\n";
			}
			
			res.close(); 
			stmt.close();
			conn.close();
			
			System.out.println(medReg);
			return medReg;
			
		}
		catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		catch(IllegalThreadStateException e) { System.out.println("GOTCHAA!!!!");} 
		
		return "";
		
		
	}
	
	public static String BDgetRegsByDate(String patient, String speciality, String beforeafter, String REGdate){
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet res=null;
		String medReg = "| Doc ID | Doc Name | Speciality | Date | Entry |\n";
		int numCol = 0;
		String ba = null;
		
		
		if(beforeafter.equalsIgnoreCase("A")) ba = ">";
		else ba = "<";

		try {
			Class.forName(myDriver);

			conn = DriverManager.getConnection(myUrl,myUser,myPass);

			stmt = conn.createStatement();
			res = stmt.executeQuery
					("select md.Did, d.name, md.speciality, md.date_of_record, md.entry "
							+ "from medical_records as md, patients as p, hospital_staff as d "
							+ "where p.name='" +patient+"' and md.speciality='" +speciality
							+"'and md.Pid=p.id and md.Did=d.id "
							+ "and md.date_of_record " +ba+" '" +REGdate+"' ");
			
			while (res.next())  {
				numCol++;
				
				int Did = res.getInt("Did");
				String Dname = res.getString("name");
				String spec = res.getString("speciality");
				String date = res.getDate("date_of_record").toString();
				String entry = res.getString("entry");
				
				medReg += "| " + Did + " | " + Dname + " | " + spec + " | " + date + " | " + entry + " |\n";  
				//res.getBlob(numCol).free();

			}
			
			if (numCol==0) {
				System.out.println("The patient has no medical records\n");
				return "The patient has no medical records\n";
			}
			
			res.close(); 
			stmt.close();
			conn.close();
			
			System.out.println(medReg);
			return medReg;
			
		}
		catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		catch(IllegalThreadStateException e) { System.out.println("GOTCHAA!!!!");} 
		
		return "";
		
		
	}

	public static void BDaddReg(String patient, String REGdate, String doctor, String speciality, String entry){
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet res=null;
		String medReg = "| Doc ID | Doc Name | Speciality | Date | Entry |\n";
		int numCol = 0;
		String ba = null;		
		
		try {
			Class.forName(myDriver);

			conn = DriverManager.getConnection(myUrl,myUser,myPass);

			stmt = conn.createStatement();
			res = stmt.executeQuery
					("select id from patients where patients.name = '"+patient+"'");
			
			res.next();
			int Pid = res.getInt("id");

			stmt.executeUpdate("INSERT INTO medical_records (Pid,Did,speciality,date_of_record,entry) VALUES"
					+ " ('"+Pid+"','"+doctor+"', '"+speciality+"', '"+REGdate+"' ,'"+entry+"')");
			
			res.close(); 
			stmt.close();
			conn.close();
			
			System.out.println(medReg);		
		}
		catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		catch(IllegalThreadStateException e) { System.out.println("GOTCHAA!!!!");} 


	}
	

}