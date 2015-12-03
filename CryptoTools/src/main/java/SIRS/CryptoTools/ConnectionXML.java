package SIRS.CryptoTools;

import org.jdom2.Document;
import org.jdom2.Element;

public class ConnectionXML {

	public Document createDoc(){
		Document doc = null;

		Element connectionElement = new Element("connection");
		doc = new Document(connectionElement);			

		Element fieldsElement = new Element("fields");

		Element id = new Element("id");
		Element msg = new Element("msg");
		Element c1 = new Element("c1");
		Element c2 = new Element("c2");
     
		fieldsElement.addContent(id);
		fieldsElement.addContent(msg);
		fieldsElement.addContent(c1);
		fieldsElement.addContent(c2);

		
	    doc.getRootElement().addContent(fieldsElement);

		return doc;
	}
	
	public Document setId(Document d, String id){
		Document doc = d;        
		doc.getRootElement().getChildren().get(0).getChild("id").setText(id); 
		return doc;
	}
	
	public Document setMessage(Document d, String msg){
		Document doc = d;        
		doc.getRootElement().getChildren().get(0).getChild("msg").setText(msg); 
		return doc;
	}
	
	public Document setC1(Document d, String c1){
		Document doc = d;        
		doc.getRootElement().getChildren().get(0).getChild("c1").setText(c1); 
		return doc;
	}
	
	public Document setC2(Document d, String c2){
		Document doc = d;        
		doc.getRootElement().getChildren().get(0).getChild("c2").setText(c2); 
		return doc;
	}
	
	public String getId(Document d){
		Document doc = d;        
		String id = doc.getRootElement().getChildren().get(0).getChild("id").getText(); 
		return id;
	}
	
	public String getMessage(Document d){
		Document doc = d;        
		String msg = doc.getRootElement().getChildren().get(0).getChild("msg").getText(); 
		return msg;
	}
	
	public String getC1(Document d){
		Document doc = d;        
		String c1 = doc.getRootElement().getChildren().get(0).getChild("c1").getText();
		return c1;
	}
	
	public String getC2(Document d){
		Document doc = d;        
		String c2 = doc.getRootElement().getChildren().get(0).getChild("c2").getText();
		return c2;
	}

}