package testpackage;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Keyword {
	public static void main(String[] args) {
		
	      try {
	    	  
	         File inputFile = new File("/Users/hadiey/Documents/SimpleIR/collection.xml");
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         Document newdoc = dBuilder.newDocument();
	         Element docs = newdoc.createElement("docs");
	 		 newdoc.appendChild(docs);
	         doc.getDocumentElement().normalize();
	         System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	         NodeList nList = doc.getElementsByTagName("code");
	         System.out.println("----------------------------");
	          
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	        		Node nNode = nList.item(temp);
		            Element body = newdoc.createElement("body");
		            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		               Element eElement = (Element) nNode;
		               
		               Element code = newdoc.createElement("code");
		  	     	   docs.appendChild(code);
		               
		               code.setAttribute("docid", ""+ eElement.getAttribute("docid"));
		         
		               Element title = newdoc.createElement("title");
		               title.appendChild(newdoc.createTextNode(eElement.getElementsByTagName("title").item(0).getTextContent()));
		               code.appendChild(title);
		               
//		               System.out.println("Body : "+ eElement.getElementsByTagName("body").item(0).getTextContent());
	               String input= ""+eElement.getElementsByTagName("body").item(0).getTextContent();
	               
	               KeywordExtractor ke = new KeywordExtractor();
	               KeywordList kl = ke.extractKeyword(input, true);
	               
	              
	               for(int i = 0; i<kl.size(); i++) { 
	            	   String text;
	            	   org.snu.ids.kkma.index.Keyword kwrd = kl.get(i);
	            	   text = "#"+kwrd.getString()+":\t"+kwrd.getCnt()+"\t";
//	            	   System.out.println(text);
	            	   body.appendChild(newdoc.createTextNode(text));
	            	   code.appendChild(body);
	               }
	            }
	         }
	         
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	 		
	 		Transformer transformer = transformerFactory.newTransformer();
	 		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	 		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	 		
	 		DOMSource source = new DOMSource(newdoc);
	 		StreamResult result = new StreamResult(new FileOutputStream(new File("/Users/hadiey/Documents/SimpleIR/index.xml")));
	 		
	 		transformer.transform(source, result);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
	}

