package SIRS.ws;

import SIRS.DBServer.*;

import javax.xml.ws.Endpoint;

import SIRS.ws.uddi.UDDINaming;

public class ServerDBMain {
	
	public static void main(String[] args) {
		try {
			SQLVerify.verifyAllReg ("Carla Josefina", "10001", "15:49:00");
			System.out.println("OUT");
		}
		catch (Exception e) {System.out.println(e.getMessage());}
		
		try{
			SQLVerify.verifyAllReg ("Cabakkj<bs", "10001", "15:49:00");
			System.out.println("OUT2");
		}
		catch (Exception e) {System.out.println(e.getMessage());}
	
			

//		SQLthings.BDaddReg("Carla Josefina", "2015/07/09 12:34:44", "10001", "Cardiologia", "entradaaaaa");

//	  Check arguments
//	  if (args.length < 3) {
//            System.err.println("Argument(s) missing!");
//            System.err.printf("Usage: java %s uddiURL wsName wsURL%n", ServerDBMain.class.getName());
//            return;
//        }
//
//        String uddiURL = args[0];
//        String name = args[1];
//        String url = args[2];
//
//        Endpoint endpoint = null;
//        SIRS.ws.uddi.UDDINaming uddiNaming = null;
//        try {
//            endpoint = Endpoint.create(new ServerImplDB());
//
//            // publish endpoint
//            System.out.printf("Starting %s%n", url);
//            endpoint.publish(url);
//
//            // publish to UDDI
//            System.out.printf("Publishing '%s' to UDDI at %s%n", name, uddiURL);
//            uddiNaming = new UDDINaming(uddiURL);
//            uddiNaming.rebind(name, url);
//
//            // wait
//            System.out.println("Awaiting connections");
//            System.out.println("Press enter to shutdown");
//            
//            
//            
//            System.in.read();
//
//        } catch (Exception e) {
//            System.out.printf("Caught exception: %s%n", e);
//            e.printStackTrace();
//
//        } finally {
//            try {
//                if (endpoint != null) {
//                    // stop endpoint
//                    endpoint.stop();
//                    System.out.printf("Stopped %s%n", url);
//                }
//            } catch (Exception e) {
//                System.out.printf("Caught exception when stopping: %s%n", e);
//            }
//            try {
//                if (uddiNaming != null) {
//                    // delete from UDDI
//                    uddiNaming.unbind(name);
//                    System.out.printf("Deleted '%s' from UDDI%n", name);
//                }
//            } catch (Exception e) {
//                System.out.printf("Caught exception when deleting: %s%n", e);
//            }
//        }

	}
}
