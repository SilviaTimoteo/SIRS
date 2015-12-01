package SIRS.CryptoTools;

import java.io.IOException;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class RequestsXML {
	public Document createDoc(){
		Document doc = null;

		Element requestElement = new Element("request");
		doc = new Document(requestElement);			

		Element fieldsElement = new Element("fields");

		Element patient = new Element("patient");
		Element doctorId = new Element("doctorId");
		Element speciality = new Element("speciality");
		Element date = new Element("date");
		Element beforeafter = new Element("beforeafter");
		Element entry = new Element("entry");
		Element timestamp = new Element("timestamp");
     
		fieldsElement.addContent(patient);
		fieldsElement.addContent(doctorId);
		fieldsElement.addContent(speciality);
		fieldsElement.addContent(date);
		fieldsElement.addContent(beforeafter);
		fieldsElement.addContent(entry);
		fieldsElement.addContent(timestamp);
		
	    doc.getRootElement().addContent(fieldsElement);

		return doc;
	}
	
	public Document setPatient(Document d, String patient){
		Document doc = d;        
		doc.getRootElement().getChildren().get(0).getChild("patient").setText(patient); 
		return doc;
	}
	
	public Document setDoctor(Document d, String doctorId){
		Document doc = d;        
		doc.getRootElement().getChildren().get(0).getChild("doctorId").setText(doctorId); 
		return doc;
	}
	
	public Document setSpeciality(Document d, String speciality){
		Document doc = d;        
		doc.getRootElement().getChildren().get(0).getChild("speciality").setText(speciality); 
		return doc;
	}
	
	public Document setDate(Document d, String date){
		Document doc = d;        
		doc.getRootElement().getChildren().get(0).getChild("date").setText(date); 
		return doc;
	}
	
	public Document setBeforeAfter(Document d, String beforeafter){
		Document doc = d;        
		doc.getRootElement().getChildren().get(0).getChild("beforeafter").setText(beforeafter); 
		return doc;
	}
	
	public Document setEntry(Document d, String entry){
		Document doc = d;        
		doc.getRootElement().getChildren().get(0).getChild("entry").setText(entry); 
		return doc;
	}
	
	public Document setTimestamp(Document d, String timestamp){
		Document doc = d;        
		doc.getRootElement().getChildren().get(0).getChild("timestamp").setText(timestamp); 
		return doc;
	}


	public String getPatient(Document d){
		Document doc = d;        
		String p = doc.getRootElement().getChildren().get(0).getChild("patient").getText(); 
		return p;
	}
	
	public String getDoctor(Document d){
		Document doc = d;        
		String id = doc.getRootElement().getChildren().get(0).getChild("doctorId").getText(); 
		return id;
	}
	
	public String getSpeciality(Document d){
		Document doc = d;        
		String s = doc.getRootElement().getChildren().get(0).getChild("speciality").getText();
		return s;
	}
	
	public String getDate(Document d){
		Document doc = d;        
		String dt = doc.getRootElement().getChildren().get(0).getChild("date").getText();
		return dt;
	}
	
	public String getBeforeAfter(Document d){
		Document doc = d;        
		String ba = doc.getRootElement().getChildren().get(0).getChild("beforeafter").getText();
		return ba;
	}
	
	public String getEntry(Document d){
		Document doc = d;        
		String e = doc.getRootElement().getChildren().get(0).getChild("entry").getText(); 
		return e;
	}
	
	public String getTimestamp(Document d){
		Document doc = d;       
		String ts =	doc.getRootElement().getChildren().get(0).getChild("timestamp").getText();
		return ts;
	}

}
