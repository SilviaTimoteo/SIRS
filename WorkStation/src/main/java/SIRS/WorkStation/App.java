package SIRS.WorkStation;

import javax.xml.registry.JAXRException;

import SIRS.ws.uddi.UDDINaming;
import SIRS.ws.*;

import java.util.Map;
import javax.xml.ws.*;
import static javax.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY;
public class App 
{
    public static void main( String[] args ) throws JAXRException
    {
    	if (args.length < 2) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s uddiURL name%n", App.class.getName());
            return;
        }

        String uddiURL = args[0];
        String name = args[1];

        System.out.printf("Contacting UDDI at %s%n", uddiURL);
        UDDINaming uddiNaming = new UDDINaming(uddiURL);

        System.out.printf("Looking for '%s'%n", name);
        String endpointAddress = uddiNaming.lookup(name);

        if (endpointAddress == null) {
            System.out.println("Not found!");
            return;
        } else {
            System.out.printf("Found %s%n", endpointAddress);
      
    
	    }
    }
}
