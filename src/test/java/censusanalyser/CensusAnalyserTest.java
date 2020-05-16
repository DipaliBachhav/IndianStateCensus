package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/StateCensusData.txt";
    private static final String WRONG_CSV_FILE_DELIMITER = "./src/test/resources/IndiaStateCensusData,csv";
    private static final String WRONG_HEADER_CSV_FILE_PATH = "./src/test/resources/IndiaStateCodeHeader.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.";
    private static final String INDIA_STATE_CODE__WRONG_CSV_FILE_TYPE = "./src/main/resources/IndiaStateCode.txt";
    private static final String INDIA_STATE_CODE_WRONG_DELIMITER = "./src/test/resources/IndiaStateCodeIncorrect,csv";
    private static final String INDIA_STATE_CODE_WRONG_HEADER = "./src/test/resources/IndiaStateCodeHeader.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";


    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,WRONG_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenTypeIncorrect_shouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,WRONG_CSV_FILE_TYPE,INDIA_STATE_CODE__WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusCodeData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,WRONG_CSV_FILE_DELIMITER,INDIA_STATE_CODE_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenHeaderIncorrect_shouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,WRONG_HEADER_CSV_FILE_PATH,INDIA_STATE_CODE_WRONG_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_STATE_CODE_CSV_FILE_PATH, INDIA_STATE_CODE_WRONG_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenTypeIncorrect_shouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_STATE_CODE__WRONG_CSV_FILE_TYPE, INDIA_STATE_CODE_WRONG_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_STATE_CODE_WRONG_DELIMITER, INDIA_STATE_CODE_WRONG_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenHeaderIncorrect_shouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_STATE_CODE_WRONG_HEADER, INDIA_STATE_CODE_WRONG_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnState_shouldReturnSortedResult() throws CensusAnalyserException {
        try{
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
        censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_WRONG_HEADER);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CSV_FILE_PATH);
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("Sikkim", censusCSV[0].state);
    }catch (CensusAnalyserException e) {
        }
        }

    @Test

    public void givenIndiaCensusData_WhenSortedOnPopulation_ShouldReturnResult() throws CensusAnalyserException {
        try{
        CensusAnalyser censusAnalyzer = new CensusAnalyser(CensusAnalyser.Country.India);
        int statePopulation = censusAnalyzer.loadCensusData(CensusAnalyser.Country.India,INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_WRONG_HEADER);
        String sortedCensusData = censusAnalyzer.getPopulationWiseSortedCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        IndiaCensusCSV[] censusCsv = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals(607688, censusCsv[0].population);
    }catch (CensusAnalyserException e) {
        }
        }

    @Test
    public void givenIndiaCensusData_WhenSortedOnDensity_ShouldReturnResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            String sortedDensityData = censusAnalyser.getPopulationDensityWiseSortedData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCsv = new Gson().fromJson(sortedDensityData, IndiaCensusCSV[].class);
            Assert.assertEquals(29, censusCsv[0].densityPerSqKm);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnArea_shouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_WRONG_HEADER);
            String sortedAreaData = censusAnalyser.getPopulationAreaWiseSortedData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedAreaData, IndiaCensusCSV[].class);
            Assert.assertEquals(7096, censusCSV[0].areaInSqKm);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSCensusData_shouldReturnCorrectRecords() throws CensusAnalyserException {
        try{
        CensusAnalyser censusAnalyser=new CensusAnalyser(CensusAnalyser.Country.US);
        int censusDataCnt=censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(51,censusDataCnt);
    }catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenUsCensusData_WhenSortedPopulationDensity_ShouldReturnCorrectResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getPopulationDensityWiseSortedData(US_CENSUS_CSV_FILE_PATH);
            USCensusCSV[] censusCsv = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alabama", censusCsv[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUsCensusData_WhenSortedArea_ShouldReturnCorrectResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getPopulationAreaWiseSortedData();
            USCensusCSV[] censusCsv = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", censusCsv[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}