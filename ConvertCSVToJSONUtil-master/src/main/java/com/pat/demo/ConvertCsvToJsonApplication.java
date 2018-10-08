package com.pat.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
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
		System.out.println("Read the file from csv and prepare the json file");
		File file = new File(
				"/Users/bhavya/Documents/workspace-sts-3.8.4.RELEASE/ConvertCSVToJSONUtil/src/main/resources/SCWithData.csv");
		
		
		String outputFolder = "/Users/bhavya/Documents/workspace-sts-3.8.4.RELEASE/ConvertCSVToJSON/";

		CSVReaderUtil util = new CSVReaderUtil();
		//For the file having all entries in single file
		//Map<String, String> csvEntryMap = util.readCSVFile(file);
		//Map<String, String> csvEntryMapList = util.readCSVFile(file);
		Map<String, String> fieldsMapping = util.getMappingKeys();
		//a file having row wise entries
		List<Map<String, String>> csvEntryMapList = util.readRowWiseCSVFile(file);
		
		for(Map<String, String> entry : csvEntryMapList ){
			Map<String, String> destMap = cSVToJSOnConverter.getConvertedMap(entry, fieldsMapping);
			String updatedTemplate = cSVToJSOnConverter.getStringRepresentation(destMap);

			String outputFileName = destMap.get("id");
			System.out.println("id::" + outputFileName);
			//writeToFile(updatedTemplate,
					//"/Users/bhavya/Documents/workspace-sts-3.8.4.RELEASE/ConvertCSVToJSON/1.json");
			//write to the file
			writeToFile(updatedTemplate,
					outputFolder+outputFileName+".json");
		}
		
		
		
		
		
		

	}

	public void writeToFile(String fileContent, String fileName)
			throws IOException {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(fileContent);

			writer.close();
		} catch (Exception e) {
			System.out.println("Exception in writing file" +e.getMessage());
		}

	}

}
