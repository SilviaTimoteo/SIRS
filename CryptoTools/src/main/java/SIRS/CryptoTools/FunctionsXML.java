package SIRS.CryptoTools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class FunctionsXML {
	
	public static byte[] XMLtoBytes(Document doc){
		byte[] bdoc = null;
		try {
			bdoc = (new XMLOutputter()).outputString(doc).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bdoc;
	}
	
	public static Document BytesToXML(byte[] bdoc){
		Document doc = null;

			try {
				doc = (new SAXBuilder()).build(new ByteArrayInputStream(bdoc));
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		
		return doc;
	}
}
