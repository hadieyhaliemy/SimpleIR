package testpackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.IntPredicate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Lab04 {
	
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException {
		HashMap[] Final = new HashMap[5];
		HashMap<String, Integer> Combine = new HashMap<String,Integer>();
		String [] input = new String [5];
		int k;
		 File inputFile = new File("/Users/hadiey/Documents/SimpleIR/index.xml");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         
         Document newdoc = dBuilder.newDocument();
         Element docs = newdoc.createElement("docs");
         newdoc.appendChild(docs);
         doc.getDocumentElement().normalize();
         NodeList nList = doc.getElementsByTagName("code");
         for (int temp = 0; temp < nList.getLength(); temp++) {
        	//Input[temp] = new HashMap<String, Integer>();
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
	              input[temp] =eElement.getElementsByTagName("body").item(0).getTextContent();
	              input[temp] = input[temp].replaceAll("\\p{P}","");
	              String [] output = input[temp].split("\\s");
	              HashMap<String, Integer> Input = new HashMap<String, Integer>();
	             for(int i = 0; i<output.length;i++) {
	              Input.put(output[i], k=Integer.parseInt(output[i+1]));
	              i++;
	            }
	             Final[temp] = Input;
            }
         }
//         for (HashMap<String, Integer> i : Final) {
//        	 for (String m : i.keySet()) {
//        		  System.out.println("key: " + m + " value: " + i.get(m));
//        		}
//        	}
         FileOutputStream Filestream = new FileOutputStream("/Users/hadiey/Documents/SimpleIR/Index.post");
         ObjectOutputStream ObjOs = new ObjectOutputStream(Filestream);
         HashMap<String,String> Post = new HashMap<String,String>();
         
         
         for(int i = 0; i<Final.length; i++) {
        	 Combine.putAll(Final[i]);
         }
       
         for (String m : Combine.keySet()) {
        	 String text = "";
//             System.out.print("키워드 : "+m + " ");
             double doc_count = 0.0;
             int [] doc1 = new int[5];
             int j=0;
              for (HashMap<String, Integer> i : Final) {
                int p=0;
                  for (String x : i.keySet()) {
                      if(m.equals(x)) {
                          doc_count += 1;
                          p=1;
                      }

                  }
                   if(p==1) {
                   doc1[0+j]=1;
                   j++;
                   }else {
                      doc1[0+j]=0;
                      j++;
                   }

              }
              int z = 0;
              for (HashMap<String, Integer> i : Final) {
                  for (String x : i.keySet()) {
                	  
                      if(m.equals(x)) {
                    	  while(true) {
                    	  if(doc1[z]==1) {
//                          System.out.print((z+1)+" "+i.get(x) * Math.log10((5/doc_count))+" ");
                          text += (z+1)+", "+i.get(x) * Math.log10((5/doc_count))+", ";
                          z++;
                    	  break;
                    	  }
                    	  else {
                    		  z++;
                    	  }
                    	  }
                             
                     }
                         Post.put(m, text);
                         }
                  }
              
//        		  System.out.println();
        	 }
         
        
         ObjOs.writeObject(Post);
         ObjOs.close();
         

         FileInputStream fileStream = new FileInputStream("/Users/hadiey/Documents/SimpleIR/Index.post");
         ObjectInputStream ObjIs = new ObjectInputStream(fileStream);

         Object object = ObjIs.readObject();
         ObjIs.close();

         System.out.println("읽어온 객체의 type = "+object.getClass());

         HashMap hashmap = (HashMap)object;
         Iterator<String> it = hashmap.keySet().iterator();

         while(it.hasNext()) {
             String key = it.next();
             String value = (String)hashmap.get(key);
             System.out.println(key+ " = ["+ value+"]");
         }
        	 
}

	}

