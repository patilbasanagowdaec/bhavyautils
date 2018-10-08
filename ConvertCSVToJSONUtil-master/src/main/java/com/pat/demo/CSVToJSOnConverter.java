package com.pat.demo;


import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Component
public class CSVToJSOnConverter {
	
	
/*	public void execute(){
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withoutHeader().withColumnSeparator(':');
		File file = new File("/Users/z002qhl/Documents/workspace-sts-3.8.4.RELEASE/ConvertCSVToJSONUtil/src/main/resources/SCWithData.csv");
		
		
		try {
			getValues(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Map<String,String>> resultList = getCSVEntries(file, mapper, schema);
		
		for(Map<String,String> map : resultList){
			for(Map.Entry<String, String> entry : map.entrySet()){
				System.out.println("key11 : " +entry.getKey());
				
				System.out.println("value11 : " +entry.getValue());
			}
		}
	
	
	}*/
	
	
	
	
	
	
	
	
	void getValues(File file) throws IOException{
		CsvMapper csvMapper = new CsvMapper();

        CsvSchema csvSchema = CsvSchema.emptySchema().withoutHeader().withColumnSeparator(':');
        MappingIterator<Map<String, String>> it = csvMapper.readerFor(Map.class).with(csvSchema.withColumnSeparator(':'))
                .readValues(file);
        List<Map<String, String>> listOfMapSchema = it.readAll();

        for (Map<String, String> map : listOfMapSchema) {
            System.out.println(map);
        }
	}
	
	
	List<Map<String,String>>getCSVEntries(File file , CsvMapper mapper ,CsvSchema schema ){
		try{
			
			//MappingIterator<String[]> it =  mapper.readerFor(String[].class).readValues(file);
			
			
			
			//CsvSchema  schema = mapper.schemaFor(String[].class).withColumnSeparator(':');
			ObjectReader reader = mapper.readerFor(String[].class).with(schema);
			MappingIterator<String[]> it = reader.readValues(file);
			
			
			while (it.hasNextValue()) {
				  String[] row = it.nextValue();
				  System.out.println(row[0]);
			}
			
			return null;
			
			
			/*return mapper.readerFor(Map.class)
					.with(schema).
					<Map<String,String>>readValues(file)
					.readAll();*/
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	
	
	
	
	public static List<Map<String, String>> read(File file) throws JsonProcessingException, IOException {
	    List<Map<String, String>> response = new LinkedList<Map<String, String>>();
	    CsvMapper mapper = new CsvMapper();
	    CsvSchema schema = CsvSchema.emptySchema().withoutHeader();
	    MappingIterator<Map<String, String>> iterator = mapper.reader(Map.class)
	            .with(schema)
	            .readValues(file);
	    while (iterator.hasNext()) {
	        response.add(iterator.next());
	    }
	    return response;
	}

	
	
	
	Map<String, String> getConvertedMap(Map<String, String> source , Map<String, String> fieldsMapping){
		
		/*List<String> list = new ArrayList<String>();
		Map<String, String> dest = new HashMap<String, String>();
		// Add the mapping configuration
		list.add("CASHIN_SC.xml");
		// Add to DozerMapper
		Mapper mapper = new DozerBeanMapper(list);
		mapper.map(source, dest , "BULK_BILLPAY");*/
		
		Map<String, String> updatedMap = new HashMap<>();
		for(Map.Entry<String, String> entry : source.entrySet() ){
			String newKey = fieldsMapping.get(entry.getKey());
			String value = source.get(entry.getKey());
			updatedMap.put(newKey, value);
		}
		
		
		return updatedMap;
		
	}
	
	
	
	String getStringRepresentation(Map<String , String> destMap){
		
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty("resource.loader", "class");
		velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		velocityEngine.init();
		    
		Template t = velocityEngine.getTemplate("CASHIN_SC.vm");
		     
		VelocityContext context = new VelocityContext();
		for(Map.Entry<String, String> entry :  destMap.entrySet()){
			//System.out.println(entry);
			context.put(entry.getKey(), entry.getValue());
		}
		//context.put("service", "Servicetest");
		     
		StringWriter writer = new StringWriter();
		t.merge( context, writer );
		//TODO write to the file
		return writer.toString();

	}
	
	
	
}
