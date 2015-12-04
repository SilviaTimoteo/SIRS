
package SIRS.WorkStation;

import SIRS.WorkStation.Services.*;
import SIRS.ws.uddi.UDDINaming;
import SIRS.ws.*;

import java.security.Key;
import java.util.Map;

import javax.xml.ws.*;

import sirs.ws.Server;
import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;
public class App 
{
	public static String[] serverEndPoint=new String[3];//uddiURLDB nameDB
	public static Key key = null;
	
	
    public static void main( String[] args ) throws Exception
    {
    	try{
	    	serverEndPoint[0] = args[0];
	    	serverEndPoint[1] = args[1];
	       
	    	Server port = ConnectionServerMedicalRecords.getPortServer();
	        
	    	boolean userLogin = false;
	    	clearScreen();
	    	while(true){
	    		
	    		Login login = new Login(port);
	    		userLogin = login.login();
	    	
	    		while(userLogin){
	    			Options ops = new Options(port, login.getUsername());
	    			userLogin = ops.showOptions();	
	    		}
	    	}
    	}catch (Exception e){
    		System.out.println(e.getMessage());
    	}
    
    }
    
    
    public static void clearScreen() throws Exception{
    	final String os = System.getProperty("os.name");
        if (os.contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
    }
    
    
    
    
}
