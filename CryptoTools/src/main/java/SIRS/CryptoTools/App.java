package SIRS.CryptoTools;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.lang.Integer;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import static javax.xml.bind.DatatypeConverter.parseBase64Binary;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;
public class App 
{
    public static void main( String[] args ) throws Exception
    {
//        DiffieHellman diffieHellman = new DiffieHellman();
//        PublicKey keyPublicC = diffieHellman.clientGenerateParams();
//        Key[] keysServer = diffieHellman.serverGenerateKey(keyPublicC);
//        Key keyC =  diffieHellman.clientGenerateKey(diffieHellman.getClientAgreement(), (PublicKey) keysServer[0]);
//        if(keyC.equals(keysServer[1])){
//        	System.out.println(" the keys are the same");
//        	System.out.println(printBase64Binary(keyC.getEncoded()));
//        	System.out.println(printBase64Binary(keysServer[1].getEncoded()));
//
//        }
    	RequestsXML request = new RequestsXML();
    	Document doc = null;
    	doc = request.createDoc();
    	

        XMLOutputter xmlOutput = new XMLOutputter();

        // display ml
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(doc, System.out); 
        
    	doc = request.setPatient(doc, "John Doe");
    	doc = request.setSpeciality(doc, "Cardio");
    	doc = request.setDoctor(doc, "Doc12345");
    	doc = request.setDate(doc, "12/09/15");
    	doc = request.setBeforeAfter(doc, "A");
    	doc = request.setTimestamp(doc, "17:09");
    	doc = request.setEntry(doc, "entry entry entry entry entry entry entry entry");
    	
    	xmlOutput.output(doc, System.out); 
    	
    	System.out.println(request.getPatient(doc));
    	System.out.println(request.getDoctor(doc));
    	System.out.println(request.getSpeciality(doc));
    	System.out.println(request.getDate(doc));
    	System.out.println(request.getBeforeAfter(doc));
    	System.out.println(request.getEntry(doc));
    	System.out.println(request.getTimestamp(doc));
    	
    	ConnectionXML connection = new ConnectionXML();
    	Document doc2 = connection.createDoc();
    	
    	xmlOutput.output(doc2, System.out); 
    	
    	doc2 = connection.setId(doc2, "1234567890");
    	doc2 = connection.setMessage(doc2, "msg msg msg msg msg msg msg");
    	doc2 = connection.setC1(doc2, "c1c1c1c1c1c1");
    	doc2 = connection.setC2(doc2, "c2c2c2c2c2c2");
    	
    	xmlOutput.output(doc2, System.out); 
    	
    	System.out.println(connection.getId(doc2));
    	System.out.println(connection.getMessage(doc2));
    	System.out.println(connection.getC1(doc2));
    	System.out.println(connection.getC2(doc2));
    	
    	
    	
    	
    }
}
