package br.com.springbatch.tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class Test {
	
	public static void main(String[] args) {
		writeDataLineByLine("csv/output.csv");
	}
	
	public static void writeDataLineByLine(String filePath) {
		
	    File file = new File(filePath); 
	    
	    try { 
	        FileWriter outputfile = new FileWriter(file); 
	        CSVWriter writer = new CSVWriter(outputfile); 

	        String[] header = { "Name", "Class", "Marks" }; 
	        writer.writeNext(header); 
	  
	        String[] data1 = { "Aman", "10", "620" }; 
	        writer.writeNext(data1); 
	        
	        String[] data2 = { "Suraj", "10", "630" }; 
	        writer.writeNext(data2); 
	  
	        writer.close(); 
	    } catch (IOException e) { 
	        e.printStackTrace(); 
	    } 
	} 

}
