package SIRS.WorkStation.Services;

import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

import java.util.Map;

import javax.xml.ws.BindingProvider;

import sirs.ws.*;
import SIRS.ws.uddi.UDDINaming;
import SIRS.WorkStation.*;

public class ConnectionServerMedicalRecords {
	
	public static Server getPortServer() {
		try{
				 if (App.serverEndPoint.length< 2) {
		           System.err.println("Argument(s) missing!");
		           System.err.printf("Usage: java %s uddiURL name%n", App.class.getName());
		       }
		
		       String uddiURLDB = App.serverEndPoint[0];
		       String nameDB = App.serverEndPoint[1];
		       
		       System.out.printf("uddiURLDB: " + uddiURLDB);
		       System.out.printf("nameDB: " + nameDB);
		
		       System.out.printf("Contacting UDDI at %s%n", uddiURLDB);
		       UDDINaming uddiNamingDB = new UDDINaming(uddiURLDB);
		
		       System.out.printf("Looking for '%s'%n", nameDB);
		       String endpointAddress = uddiNamingDB.lookup(nameDB);
		
		       System.out.println("\n\n\n" +endpointAddress + "\n\n\n");
		       
		       if (endpointAddress == null) {
		           System.out.println("Not found!");
		           return null;
		       } else {
		           System.out.printf("Found %s%n", endpointAddress);
		         
			    }
		       ServerImplService service = new ServerImplService();
		       Server port = service.getServerImplPort();
		       System.out.println("Setting endpoint address ...");
		       BindingProvider bindingProvider = (BindingProvider) port;
		       Map<String, Object> requestContext = bindingProvider.getRequestContext();
		       requestContext.put(ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
		       return port;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
