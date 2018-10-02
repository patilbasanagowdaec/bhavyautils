package com.pat.demo;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConvertCsvToJsonApplication implements CommandLineRunner {

	@Autowired
	CSVToJSOnConverter cSVToJSOnConverter;
	
	public static void main(String[] args) {
		SpringApplication.run(ConvertCsvToJsonApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	System.out.println("testing");
	File file = new File("/Users/bhavya/Documents/workspace-sts-3.8.4.RELEASE/ConvertCSVToJSON/src/main/resources/SCWithData.csv");
	
	CSVReaderUtil util = new CSVReaderUtil();
	Map<String, String> csvEntryMap = util.readCSVFile(file);
	Map<String , String> fieldsMapping = util.getMappingKeys();
	
	Map<String , String> destMap = cSVToJSOnConverter.getConvertedMap(csvEntryMap , fieldsMapping);
	
	System.out.println(cSVToJSOnConverter.getStringRepresentation(destMap));
	
	
		
	}
	
	
	
}
