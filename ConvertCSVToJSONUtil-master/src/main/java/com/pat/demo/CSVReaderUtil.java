package com.pat.demo;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dozer.DozerBeanMapper;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class CSVReaderUtil {

	public Map<String, String> readCSVFile(File file) {
		Map<String, String> csvFileMap = new HashMap<>();

		try {
			// Create an object of file reader
			// class with CSV file as a parameter.
			FileReader filereader = new FileReader(file);

			// create csvReader object and skip first Line
			CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
			List<String[]> allData = csvReader.readAll();

			// print Data
			for (String[] row : allData) {
				for (String cell : row) {
					String[] splittedRow = cell.split(":");
					if (splittedRow != null && splittedRow.length > 1) {
						
						String key = splittedRow[0].substring(0,splittedRow[0].lastIndexOf("\""));
						
						String value = splittedRow[1].substring(splittedRow[1].indexOf("\"")+1 , splittedRow[1].length());
						csvFileMap.put(key, value);
						System.out.println(key + "\t" + value + "\t");
					} else {
						//to handle empty rows
						if(splittedRow[0] != null && splittedRow[0].length() > 0){
							String key = splittedRow[0].substring(0,splittedRow[0].lastIndexOf("\""));
							csvFileMap.put( key, null);
							System.out.println(key + "\t" );
						}
						
					}

					
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		dozerConverter(csvFileMap);
		return csvFileMap;

	}
	
	
	
	
	public void dozerConverter(Map<String, String> csvFileMap){
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		
		dozerBeanMapper.setMappingFiles(Arrays.asList("CASHIN_SC.xml"));
		Map<String, String> destFileMap = dozerBeanMapper.map(csvFileMap , Map.class);
		
		for (Map.Entry<String,String> entry : destFileMap.entrySet())  
            System.out.println("Key = " + entry.getKey() + 
                             ", Value = " + entry.getValue()); 
    } 
		
	
	
	
	
	
	
	Map<String , String> getMappingKeys(){
		Map<String, String> map = new HashMap<>();
		map.put("Sender MFS Provider", "sendermfsprovider");
		map.put("Sender Wallet Type/ Linked Bank", "senderwallet/tbank");
		map.put("Sender Grade", "sendergrade");
		map.put("Receiver Grade", "receivergrade");
		map.put("Service", "service");
		map.put("Paying Entity - Sender name", "payingentity");
		map.put("Credited Entity - Receiver name", "creditedentity");
		map.put("min Fixed Service charge ", "minfixedservicecharge");
		map.put("max Fixed Service Charge", "maxfixedservicecharge");
		map.put("Telescopic Pricing On Service Charge", "telescopicpricingonsc");
		map.put("SC From Range slab-0", "SCfromrangeslab0");
		map.put("SC To Range slab-0", "SCtorangeslab0");
		map.put("Service Charge slab-0(%)", "servicechargeslab0(%)");
		map.put("Service Charge slab-0(fixed)", "servicechargeslab0(fixed)");
		map.put("SC From Range slab-1", "SCfromrangeslab1");
		//TODO add remaning fields
		
		return map;
	}
	

}
