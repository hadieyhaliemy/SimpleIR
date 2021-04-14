package testpackage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class Searcher {

	public double[][] InnerProduct (String input) throws IOException, ClassNotFoundException {
	double [][] Freq = new double[5][3];
	
	 FileInputStream fileStream = new FileInputStream("/Users/hadiey/Documents/SimpleIR/Index.post");
		 ObjectInputStream ObjIs = new ObjectInputStream(fileStream);
		  
		 Object object = ObjIs.readObject();
		 ObjIs.close();
		 	
	KeywordExtractor ke = new KeywordExtractor();
    KeywordList kl = ke.extractKeyword(input, true);
    for(int i = 0; i<kl.size(); i++) {
    HashMap hashmap = (HashMap)object;
 	Iterator<String> it = hashmap.keySet().iterator();
 	   String text;
 	   Keyword kwrd = kl.get(i);
 	   String pop = kwrd.getString();
 	  text =kwrd.getString()+":\t"+kwrd.getCnt()+"\t";
 	   //System.out.println(text);
 	   while(it.hasNext()) {
 		   String Key = it.next();
 		   if(Key.equals(pop)) {
 			  String value = (String)hashmap.get(Key);
 			  value = value.replaceAll("[\\[\\]\\(\\)]", "");
 			  String [] output = value.split(" , ");

 			  
 			  for(String id : output) {
 				  for (int m = 1; m<=5;m++) {
 					   if(Character.getNumericValue(id.charAt(0))==m) {
 					  
 					 int wq =kwrd.getCnt();
 					 String[] tfidf = id.split(",");
 					 double weight = Double.parseDouble(tfidf[1]);
 					 double sum = weight*wq;
 					 Freq[m-1][0] += sum;
 					 Freq[m-1][1] += wq*wq;
 					 Freq[m-1][2] += weight*weight;
 					   }
 				  }
 				  
 			  }
 			  
 			  break;
 			  
 		   }else 
 			  continue;
 			  }
 		   }
    return Freq;
 	   }
 		   
	
	public void CalcSim (String input, double[][]Freq) throws IOException, ClassNotFoundException {
		
        double [][] Frequency = new double[5][2];
        for(int i = 0; i<Frequency.length; i++) {
        	
        	Frequency[i][0] = i+1;
        	Frequency[i][1] = Freq[i][0]/(Math.sqrt(Freq[i][1])*Math.sqrt(Freq[i][2]));

        }
        java.util.Arrays.sort(Frequency, new java.util.Comparator<double[]>() {
        	
            public int compare(double[] a, double[] b) {
                return Double.compare(a[1], b[1]);
            }
        });

     
       String[] rank = new String[3];
       String[] doctest = {"떡 ","초밥 ","파스타 ","아이스크림 ","라면 "};
       int count=0;
       
       for (int i = 0; i<3;i++) {
    	   for(int j = 0; j < doctest.length;j++) {
    		   
    			   if(Frequency[i][0] == j+1)
    				  rank[count++] = doctest[j];
    		   
    	   }
       }
       
        System.out.println("Ranking 1: "+rank[0]+" / Weight : "+Frequency[0][1]+"\nRanking 2: "+rank[1]+" / Weight : "+Frequency[1][1]+"\nRanking 3: "+rank[2]+" / Weight : "+Frequency[2][1]);

	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Please Input Text:");
		String input = sc.nextLine();
		Searcher ss = new Searcher();
		ss.CalcSim(input,ss.InnerProduct(input));

	}

}
