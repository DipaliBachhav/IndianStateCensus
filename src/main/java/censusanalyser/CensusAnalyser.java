package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser {
    List<CensusDAO> censusList = null;
    Map<String,CensusDAO> censusCSVMap;
    public enum Country{India,US}
    private Country country;

    public CensusAnalyser(Country country) {
        this.country=country;
    }

    public int loadCensusData(Country country,String... csvFilePath) throws CensusAnalyserException {
        censusCSVMap = CensusAdapterFactory.getCensusData(country,csvFilePath);
        return censusCSVMap.size();
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

    public String getSortedCensusDataAsPerStateCode() throws CensusAnalyserException {
        if(censusCSVMap == null || censusCSVMap.size() ==0 ) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(census -> census.stateCode);
        ArrayList censusDTOS=censusCSVMap.values().stream().
                sorted(censusCSVComparator).
                map(censusDAO-> censusDAO.getCensusDTO(country)).
                collect(toCollection(ArrayList::new));
        String sortedStateCode = new Gson().toJson(censusDTOS);
        return sortedStateCode;
    }


    public String getStateWiseSortedCensusData(String censusCsvFilePath, String indiaCensusCsvFilePath) throws CensusAnalyserException {
        if(censusCSVMap == null || censusCSVMap.size() ==0 ) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(census -> census.state);
        ArrayList censusDTOS=censusCSVMap.values().stream().
                sorted(censusCSVComparator).
                map(censusDAO-> censusDAO.getCensusDTO(country)).
                collect(toCollection(ArrayList::new));
        String sortedStateData = new Gson().toJson(censusDTOS);
        return sortedStateData;
    }

    public String getPopulationWiseSortedCensusData(String indiaCensusCsvFilePath) throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(census -> census.population);
        ArrayList censusDTOS=censusCSVMap.values().stream().
                sorted(censusCSVComparator).
                map(censusDAO-> censusDAO.getCensusDTO(country)).
                collect(toCollection(ArrayList::new));
        String population = new Gson().toJson(censusDTOS);
        return population;
    }

    public String getPopulationDensityWiseSortedData(String indiaCensusCsvFilePath) throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(census -> census.populationDensity);
        ArrayList censusDTOS=censusCSVMap.values().stream().
                sorted(censusCSVComparator).
                map(censusDAO-> censusDAO.getCensusDTO(country)).
                collect(toCollection(ArrayList::new));
        String sortedPopulationCensus = new Gson().toJson(censusDTOS);
        return sortedPopulationCensus;
    }

    public String getPopulationAreaWiseSortedData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(census -> census.totalArea);
        ArrayList censusDTOS=censusCSVMap.values().stream().
                sorted(censusCSVComparator).
                map(censusDAO-> censusDAO.getCensusDTO(country)).
                collect(toCollection(ArrayList::new));
        String sortedAreaCensus = new Gson().toJson(censusDTOS);
        return sortedAreaCensus;
    }
}
