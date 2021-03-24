package testpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException; 
import java.io.FileOutputStream;
import java.io.FileReader;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.parsers.ParserConfigurationException; 
import javax.xml.transform.OutputKeys; 
import javax.xml.transform.Transformer; 
import javax.xml.transform.TransformerConfigurationException; 
import javax.xml.transform.TransformerException; 
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource; 
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.w3c.dom.Document; 
import org.w3c.dom.Element;

import org.w3c.dom.Node;

public class testclass {

	public static void main(String[] args) throws ParserConfigurationException, FileNotFoundException, TransformerException {
		// TODO Auto-generated method stub
		
//		int num = 0;
//		File path = new File("/Users/hadiey/Documents/SimpleIR/html");
//		String[] files = {"/Users/hadiey/Documents/SimpleIR/떡.html","/Users/hadiey/Documents/SimpleIR/라면.html","/Users/hadiey/Documents/SimpleIR/아이스크림.html","/Users/hadiey/Documents/SimpleIR/초밥.html","/Users/hadiey/Documents/SimpleIR/파스타.html"};
//		int fileNum = files.length;
		
		File folder = new File("/Users/hadiey/Documents/SimpleIR/html files");
		File[] files = folder.listFiles();
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
		Document doc = docBuilder.newDocument();
		
		Element docs = doc.createElement("docs");
		doc.appendChild(docs);
		
		
		for(int i = 1;i<files.length;i++) {
			String s = null,content = null;
			try{
				
				   FileReader fr=new FileReader(files[i].getPath());
				   BufferedReader br= new BufferedReader(fr);
				    content="";
				   while((s=br.readLine())!=null)
				    {

				     content=content+s;

				    } 
				  org.jsoup.nodes.Document doctest =  Jsoup.parse(content);
				  String text = doctest.body().text();
				  
		Element code = doc.createElement("code");
		docs.appendChild(code);
		
		code.setAttribute("docid", " "+i);
		
		Element title = doc.createElement("title");
		String fileName = files[i].getName();
		int lastPeriodPos = fileName.lastIndexOf('.');
        if (lastPeriodPos > 0)
        fileName = fileName.substring(0, lastPeriodPos);
        title.appendChild(doc.createTextNode(fileName));
		
		code.appendChild(title);
		
		Element body = doc.createElement("body");
		body.appendChild(doc.createTextNode(text));
		code.appendChild(body);
		
		}
				  catch(Exception ex)
				   {

				    }}
				  TransformerFactory transformerFactory = TransformerFactory.newInstance();
		
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new FileOutputStream(new File("/Users/hadiey/Documents/SimpleIR/collection.xml")));
			
		transformer.transform(source, result);
	}
}