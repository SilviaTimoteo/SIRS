package SIRS.CryptoTools;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.lang.Integer;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        DiffieHellman diffieHellman = new DiffieHellman();
        PublicKey keyPublicC = diffieHellman.clientGenerateParams();
        Key[] keysServer = diffieHellman.serverGenerateKey(keyPublicC);
        Key keyC =  diffieHellman.clientGenerateKey(diffieHellman.getClientAgreement(), (PublicKey) keysServer[0]);
        if(keyC.equals(keysServer[1])){
        	System.out.println(" the keys are the same");
        	System.out.println(printBase64Binary(keyC.getEncoded()));
        	System.out.println(printBase64Binary(keysServer[1].getEncoded()));

        }
    
        
    }
}
