package ua.com.foxminded.racers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReportParser {

    private static final String PATTERN_DATA_TIME = "yyyy-MM-dd HH:mm:ss.SSS";

    private static final char TEXT_SEPARATOR = '_';
    private static final String PARSING_EMPTY_RESULT = "";
    public static final int NOT_FOUND_INDEX = -1;
    public static final char EMPTY_CHAR = ' ';
    public static final int LENGTH_ABBR = 3;
    public static final int ABBR_START_INDEX = 0;
    public static final int NAME_START_INDEX = 4;
    
    protected List<RacerData> parseRacersData(String fileName) {
        List<String> rawRacersData = readFileToLines(fileName);
        List<RacerData> racersData = parseRacersRawData(rawRacersData);
        return racersData;
    }

    protected Map<String, LocalDateTime> parseTimeData(String fileName) {
        List<String> rawRacersTime = readFileToLines(fileName);
        Map<String, LocalDateTime> mapTime = parseTimeRawData(rawRacersTime);
        return mapTime;
    }

    Map<String, LocalDateTime> parseTimeRawData(List<String> rawRacersTime) {
        Map<String, LocalDateTime> mapTime = new HashMap<>();
        mapTime = rawRacersTime
            .stream()
            .collect(Collectors.toMap(i -> parseAbbreviation(i), i -> parseStringToLocalDT(parseTime(i))));
        return mapTime;
    }

    List<RacerData> parseRacersRawData(List<String> rawData) {
        List<RacerData> racerDataList = new ArrayList<RacerData>();
        racerDataList = rawData.stream().map(this::parseRacer).collect(Collectors.toList());
        return racerDataList;
    }

    protected List<String> readFileToLines(String fileName) {
        List<String> rawData = new ArrayList<String>();
        String file = receivePath(fileName);
        try (Stream<String> fileInStream = Files.lines(Paths.get(file))) {
            rawData = fileInStream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rawData;
    }

    public RacerData parseRacer(String text) {
        return new RacerData(parseAbbreviation(text), parseName(text), parseCar(text));
    }

    private String parseAbbreviation(String text) {
        return text.substring(ABBR_START_INDEX, LENGTH_ABBR);
    }

    private String parseTime(String text) {
        return text.substring(LENGTH_ABBR);
    }

    private String parseName(String line) {
        int indexSeparator = findIndexOfElementInLine(line);
        return indexSeparator == NOT_FOUND_INDEX ? PARSING_EMPTY_RESULT
                : line.substring(NAME_START_INDEX, indexSeparator);
    }

    private String parseCar(String line) {
        int indexSeparator = findIndexOfElementInLine(line);
        return indexSeparator == NOT_FOUND_INDEX ? PARSING_EMPTY_RESULT : line.substring(indexSeparator + 1);
    }

    private int findIndexOfElementInLine(String line) {
        return line.indexOf(TEXT_SEPARATOR, NAME_START_INDEX);

    }

    public Duration recieveDuration(LocalDateTime timeStart, LocalDateTime timeEnd) {
        if (timeStart == null || timeEnd == null) {
            return Duration.ZERO;
        }
        return Duration.between(timeStart, timeEnd);
    }

    private LocalDateTime parseStringToLocalDT(String text) {
        LocalDateTime dateTime = LocalDateTime
            .parse(text.replace(TEXT_SEPARATOR, EMPTY_CHAR), DateTimeFormatter.ofPattern(PATTERN_DATA_TIME));
        return dateTime;
    }

    public void checkFileName(String fileName) {
        Optional
            .ofNullable(fileName)
            .orElseThrow(() -> new IllegalArgumentException("Null as file name is not allowed "));

        if (fileName.isEmpty()) {
            throw new IllegalArgumentException("Empty parameters are not allowed " + fileName);
        }
    }

    String receivePath(String fileName) {
        checkFileName(fileName);

        ClassLoader classLoader = getClass().getClassLoader();

        File file = Optional
            .ofNullable(classLoader.getResource(fileName))
            .map(URL::getFile)
            .map(File::new)
            .orElseThrow(() -> new IllegalArgumentException("File doesn't exis"));

        checkFile(file);

        return file.getAbsolutePath();
    }

    private void checkFile(File file) {
        if (isNotExist(file)) {
            throw new IllegalArgumentException("File " + file + " doesn't exist");
        }
        if (isNotFile(file)) {
            throw new IllegalArgumentException("Directory is  not allowed  " + file + " Wait for a file ....");
        }
    }

    private boolean isNotFile(File file) {
        return !file.isFile();
    }

    private boolean isNotExist(File file) {
        return !file.exists();
    }
}
