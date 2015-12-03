package SIRS.ws;

import java.util.Map;

import javax.xml.ws.*;

import sirs.ws.*;//generated-sources
import SIRS.ws.uddi.UDDINaming;
import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;

public class ConnectionServerDB {

	public static ServerDB getPortServerDB() {
		try{
				 if (ServerMain.serverDBEndPoint.length< 2) {
		           System.err.println("Argument(s) missing!");
		           System.err.printf("Usage: java %s uddiURL name%n", ServerMain.class.getName());
		       }
		
		       String uddiURLDB = ServerMain.serverDBEndPoint[0];
		       String nameDB = ServerMain.serverDBEndPoint[1];
		       System.out.printf("uddiURLDB: " + uddiURLDB);
		       System.out.printf("nameDB: " + nameDB);
		
		       System.out.printf("Contacting UDDI at %s%n", uddiURLDB);
		       UDDINaming uddiNamingDB = new UDDINaming(uddiURLDB);
		
		       System.out.printf("Looking for '%s'%n", nameDB);
		       String endpointAddress = uddiNamingDB.lookup(nameDB);
		       System.out.println("ServerDb" + endpointAddress);
		       if (endpointAddress == null) {
		           System.out.println("Not found!");
		           return null;
		       } else {
		           System.out.printf("Found %s%n", endpointAddress);
		         
			    }
		       ServerImplDBService service = new ServerImplDBService();
		       ServerDB port = service.getServerImplDBPort();
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
