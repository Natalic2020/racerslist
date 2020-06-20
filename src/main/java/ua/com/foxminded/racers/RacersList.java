package ua.com.foxminded.racers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RacersList {
	
	private static final String SINGLE_INDENT = " ";
	
	public String formRacersList (String fileStart, String fileEnd ,String fileAbbreviations) throws IOException{
		
		Map<String, String > mapStart = Files.lines(Paths.get(fileStart))
				.collect(Collectors.toMap(i -> i.substring(0, 3), i -> i.substring(3).replace('_', ' ')));
		Map<String, String > mapEnd = Files.lines(Paths.get(fileEnd))
				.collect(Collectors.toMap(i -> i.substring(0, 3), i -> i.substring(3).replace('_', ' ')));
		Map<String,String > mapAbbreviations = Files.lines(Paths.get(fileAbbreviations))
				.collect(Collectors.toMap(i -> i.substring(0, 3), i -> i.substring(3)));
		
		
		System.out.println(mapStart);
		System.out.println(mapEnd);
		System.out.println(mapAbbreviations);
		


		Map<String, String> map3 = Stream.of(mapStart, mapEnd).flatMap(m -> m.entrySet().stream())
		    	       .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
		    	    		      (o1, o2) -> Duration.between(LocalDateTime.parse(o2, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")), 
		    	    		    		  LocalDateTime.parse(o1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).toString()));
		
		Map<String, String> mapSort1 = map3.entrySet().stream()
              .sorted(Map.Entry.<String,String>comparingByValue().reversed())
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e1.replace("PT", "").replace("M", ":").replace("S", ""), LinkedHashMap::new));	    
		    
		
//		Map<String, String> newMapSortedByValue = sample.entrySet().stream()
//                .sorted(Map.Entry.<String,String>comparingByValue().reversed())
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e1, LinkedHashMap::new));	    
//		    
		    System.out.println("**************************");
		    System.out.println(map3);
		    System.out.println(mapSort1);
		   
		    
		    Map<String, String> fullMap  =  Stream.of(map3, mapAbbreviations).flatMap(m -> m.entrySet().stream())
		    	       .collect(Collectors.toMap(Entry::getKey, Entry::getValue,  (o1, o2) ->  o1 + o2));
		    
		    Map<String, String> mapSort = fullMap.entrySet().stream()
		              .sorted(Map.Entry.<String,String>comparingByValue().reversed())
		              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e1.replace("PT", "").replace("M", ":").replace("S", ""), LinkedHashMap::new));	
		    
		    
		    System.out.println(fullMap);
		    System.out.println(mapSort);
		    System.out.println("**************************");

		Map<Long, String> mapTime = new TreeMap<>();

		String first15 = mapSort.entrySet().stream().limit(2).map(x -> x.getValue())
				.collect(Collectors.joining(System.lineSeparator()));
		
//		String theLast = mapSort.entrySet().stream().skip(2).map(x -> x.substring(x.getValue().indexOf("_")))
//				.collect(Collectors.joining(System.lineSeparator()));
//		
		
		
		System.out.println(first15);
		 System.out.println("**************************");
//		System.out.println(theLast);
		
		StringJoiner listRacer = new StringJoiner("");
		int serialNumber = 0;
		for (Entry<String, String> item : mapSort.entrySet()) {
			
			String nameRacer = item.getValue();
			serialNumber = serialNumber + 1;
			String outputLine = fornOutputLine(serialNumber, nameRacer);
			listRacer.add(outputLine);
			System.out.println(String.format(" %s", nameRacer));
		}
		
		System.out.println(listRacer.toString());
		
		return "";
	}
	
	private static String fornOutputLine(int serialNumber, String nameRacer) {
		int indexFirst = nameRacer.indexOf("_");
		int indexSecond = nameRacer.indexOf("_", indexFirst + 1);
		String indentFirst =  printCharacters(25 - (indexSecond - indexFirst)); 
		String indentSecond =  printCharacters(35 - nameRacer.substring(indexSecond + 1).length()); 
		String outputSerialNumber = outputSerialNumber(serialNumber);
		String outputLine = String.format("%s%s%s|%s%s|%s%n", outputSerialNumber, nameRacer.substring(indexFirst + 1,  indexSecond), indentFirst  ,
				nameRacer.substring(indexSecond + 1)  ,indentSecond,
				nameRacer.substring(0, indexFirst).replace("PT-", "").replace("M-", ":").replace("S", ""));
		return outputLine;
	}

	
	
	private static String outputSerialNumber(int serialNumber) {
		StringJoiner outputserialNumber = new StringJoiner("");
		String serialNumberString = String.valueOf(serialNumber);
		if (serialNumberString.length() == 1) {
			outputserialNumber.add(SINGLE_INDENT);
		}
		outputserialNumber.add(serialNumberString).add(".");
		return outputserialNumber.toString();
	}

	private static String printCharacters(final int lenghtLine ) {
		StringJoiner outputSymbol = new StringJoiner("");
		for (int i = 0; i < lenghtLine; i++) {
			outputSymbol.add(SINGLE_INDENT);
		}
		return outputSymbol.toString();
	}

}
