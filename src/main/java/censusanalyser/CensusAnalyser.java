package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<CensusDAO> censusList = null;
    Map<String,CensusDAO> censusCSVMap;

    public CensusAnalyser() {
        censusCSVMap = new HashMap<>();

    }
    public int loadUsCensusData(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath,USCensusCSV.class);
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        return this.loadCensusData(csvFilePath, IndiaCensusCSV.class);
    }
    private <E> int loadCensusData(String csvFilePath, Class<E> censusCSVClass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFacroty.createCSVBuilder();
            Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
            Iterable<E> csvIterable = () -> csvFileIterator;
            if(censusCSVClass.getName().equals("censusAnalyser.IndiaCensusCSV")) {
                         StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if(censusCSVClass.getName().equals("censusAnalyser.USCensusCSV")) {
                        StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV ->censusCSVMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            return this.censusList.size();

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }


    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFacroty.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator=csvBuilder.getCSVFileIterator(reader,IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> stateCodeCSVIterable=()-> stateCodeCSVIterator;
            StreamSupport.stream(stateCodeCSVIterable.spliterator(),false)
                    .filter(stateCodeCSV -> censusCSVMap.get(stateCodeCSV.state) != null )
                    .forEach(stateCodeCSV-> censusCSVMap.get(stateCodeCSV.state).stateCode = stateCodeCSV.stateCode);
            return censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEnteries;
    }


    public String getStateWiseSortedCensusData(String indiaCensusCsvFilePath) throws CensusAnalyserException {
        if(censusCSVMap == null || censusCSVMap.size() ==0 ) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(census -> census.state);
        List<CensusDAO> sortedCensusData = censusCSVMap.values().stream().collect(Collectors.toList());
       this.sort(censusCSVComparator);
        String sortedStateData = new Gson().toJson(sortedCensusData);
        return sortedStateData;
    }

    public String getPopulationWiseSortedCensusData(String indiaCensusCsvFilePath) throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(census -> census.population);
        List<CensusDAO> sortedCensusData = censusCSVMap.values().stream().collect(Collectors.toList());
        this.sort(censusCSVComparator);
        String population = new Gson().toJson(this.censusList);
        return population;
    }

    public String getPopulationDensityWiseSortedData(String indiaCensusCsvFilePath) throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(census -> census.populationDensity);
        List<CensusDAO> sortedCensusData = censusCSVMap.values().stream().collect(Collectors.toList());
        this.sort(censusCSVComparator);
        String sortedPopulationCensus = new Gson().toJson(this.censusList);
        return sortedPopulationCensus;
    }

    public String getPopulationAreaWiseSortedData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(census -> census.totalArea);
        List<CensusDAO> sortedCensusData = censusCSVMap.values().stream().collect(Collectors.toList());
        this.sort(censusCSVComparator);
        String sortedAreaCensus = new Gson().toJson(this.censusList);
        return sortedAreaCensus;
    }


    private void sort(Comparator<CensusDAO> censusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                CensusDAO census1 = censusList.get(j);
                CensusDAO census2 = censusList.get(j + 1);
                censusList.set(j, census2);
                censusList.set(j + 1, census1);
            }
        }
    }
}
