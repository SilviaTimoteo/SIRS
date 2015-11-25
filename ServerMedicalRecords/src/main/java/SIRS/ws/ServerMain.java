package SIRS.ws;

import javax.xml.registry.JAXRException;
import javax.xml.ws.Endpoint;

import SIRS.ws.uddi.UDDINaming;

public class ServerMain {
	public static void main(String[] args) throws JAXRException {
        // Check arguments
        if (args.length < 3) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s uddiURL wsName wsURL%n", ServerMain.class.getName());
            return;
        }

        String uddiURL = args[0];
        String name = args[1];
        String url = args[2];

        Endpoint endpoint = null;
        SIRS.ws.uddi.UDDINaming uddiNaming = null;
        try {
            endpoint = Endpoint.create(new ServerImpl());

            // publish endpoint
            System.out.printf("Starting %s%n", url);
            endpoint.publish(url);

            // publish to UDDI
            System.out.printf("Publishing '%s' to UDDI at %s%n", name, uddiURL);
            uddiNaming = new UDDINaming(uddiURL);
            uddiNaming.rebind(name, url);

            // wait
            System.out.println("Awaiting connections");
            System.out.println("Press enter to shutdown");
            //SERVERDB CONNECTION
            if (args.length < 2) {
                System.err.println("Argument(s) missing!");
                System.err.printf("Usage: java %s uddiURL name%n", ServerMain.class.getName());
                return;
            }

            String uddiURLDB = args[3];
            String nameDB = args[4];

            System.out.printf("Contacting UDDI at %s%n", uddiURL);
            UDDINaming uddiNamingDB = new UDDINaming(uddiURL);

            System.out.printf("Looking for '%s'%n", nameDB);
            String endpointAddress = uddiNamingDB.lookup(nameDB);

            if (endpointAddress == null) {
                System.out.println("Not found!");
                return;
            } else {
                System.out.printf("Found %s%n", endpointAddress);
              
    	    }
            System.in.read();

        } catch (Exception e) {
            System.out.printf("Caught exception: %s%n", e);
            e.printStackTrace();

        } finally {
            try {
                if (endpoint != null) {
                    // stop endpoint
                    endpoint.stop();
                    System.out.printf("Stopped %s%n", url);
                }
            } catch (Exception e) {
                System.out.printf("Caught exception when stopping: %s%n", e);
            }
            try {
                if (uddiNaming != null) {
                    // delete from UDDI
                    uddiNaming.unbind(name);
                    System.out.printf("Deleted '%s' from UDDI%n", name);
                }
            } catch (Exception e) {
                System.out.printf("Caught exception when deleting: %s%n", e);
            }
        }
        
      

    }
}
