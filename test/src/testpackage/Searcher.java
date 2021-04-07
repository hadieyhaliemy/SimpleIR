package testpackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class Searcher {

	
	public void CalcSim (String input) throws IOException, ClassNotFoundException {
		double [] Freq = new double[5];
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
     				  if(Character.getNumericValue(id.charAt(0))==1) {
     					  int wq =kwrd.getCnt();
     					  String[] tfidf = id.split(",");
     					  double weight = Double.parseDouble(tfidf[1]);
     					 double sum = weight*wq;
     					 Freq[0] += sum;
     				  }else if(Character.getNumericValue(id.charAt(0))==2){
     					  int wq =kwrd.getCnt();
    					  String[] tfidf = id.split(",");
    					  double weight = Double.parseDouble(tfidf[1]);
    					  double sum = weight*wq;
    					  Freq[1] += sum;
    					  //System.out.println("File Id 2 :"+Freq[1]);
     				  }else if(Character.getNumericValue(id.charAt(0))==3){
     					 int wq =kwrd.getCnt();
    					  String[] tfidf = id.split(",");
    					  double weight = Double.parseDouble(tfidf[1]);
    					  double sum = weight*wq;
    					  Freq[2] += sum;
    					  //System.out.println("File Id 3 :"+Freq[2]);
     				  }else if(Character.getNumericValue(id.charAt(0))==4) {
     					 int wq =kwrd.getCnt();
    					  String[] tfidf = id.split(",");
    					  double weight = Double.parseDouble(tfidf[1]);
    					  double sum = weight*wq;
    					  Freq[3] += sum;
    					  //System.out.println("File Id 4 :"+Freq[3]);
     				  }else {
     					 int wq =kwrd.getCnt();
    					  String[] tfidf = id.split(",");
    					  double weight = Double.parseDouble(tfidf[1]);
    					  double sum = weight*wq;
    					  Freq[4] += sum;
    					 // System.out.println("File Id 5 :"+Freq[4]);
     				  }
     			  }
     			  break;
     		   }else 
     			  continue;
     	   }
        }
        double [][] Frequency = new double[5][2];
        for(int i = 0; i<Frequency.length; i++) {
        	Frequency[i][0] = i+1;
        	Frequency[i][1] = Freq[i];
        }
        java.util.Arrays.sort(Frequency, new java.util.Comparator<double[]>() {
            public int compare(double[] a, double[] b) {
                return Double.compare(a[1], b[1]);
            }
        });
        String first="", second="", third="";
        String[] rank = new String[3];
       String[] doctest = {"떡 ","초밥 ","파스타 ","아이스크림 ","라면 "};
       int count=0;
       for (int i = 4; i>1;i--) {
    	   for(int j = 0; j < doctest.length;j++) {
    			   if(Frequency[i][0] == j+1)
    				  rank[count++] = doctest[j];
    		   
    	   }
       }

         
  
        System.out.println("Ranking 1: "+rank[0]+" / Weight : "+Frequency[4][1]+"\nRanking 2: "+rank[1]+" / Weight : "+Frequency[3][1]+"\nRanking 3: "+rank[2]+" / Weight : "+Frequency[2][1]);
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Scanner sc = new Scanner(System.in);
		System.out.print("Please Input Text:");
		String input = sc.nextLine();
		Searcher ss = new Searcher();
		ss.CalcSim(input);

	}

}
