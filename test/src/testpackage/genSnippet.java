package testpackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class genSnippet {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		Scanner s = new Scanner(System.in);
		keyword(s.nextLine());
		
	}

	public static void keyword(String testinput) throws FileNotFoundException, IOException {
		String input = "", s = "", text = "";
		int count = 0;
		String []lines, temp;
		input = testinput;
		FileReader f = new FileReader("input.txt");
		 BufferedReader b= new BufferedReader(f);
	
		 
		   while((s = b.readLine())!=null)
		    {
			   count++;
		    } 
		   
		   lines = new String[count];
		   
		   text = "";
		   int index = 0;
		   while((s = b.readLine())!=null)
		    {
			   lines[index++] = s;

		    } 
		   temp= new String[10];
		   
		   for(int i = 0; i<lines.length; i++) {
			   temp[i].split(input);
		   }
		   
		   
	}
}
