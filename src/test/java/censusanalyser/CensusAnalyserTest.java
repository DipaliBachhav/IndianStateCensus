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


    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenTypeIncorrect_shouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusCodeData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenStateCensusCsvFile_WhenHeaderIncorrect_shouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_HEADER_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }


    @Test
    public void givenIndianStateCodeData_shouldReturnExactCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfStateCode = censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(37, numOfStateCode);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenTypeIncorrect_shouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CODE__WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CODE_WRONG_DELIMITER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenHeaderIncorrect_shouldReturnException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException expectedException = ExpectedException.none();
            expectedException.expect(ClassCastException.class);
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CODE_WRONG_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnState_shouldReturnSortedResult() throws CensusAnalyserException {

        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("Sikkim", censusCSV[0].state);
    }

    @Test

    public void givenIndiaCensusData_WhenSortedOnPopulation_ShouldReturnResult() throws CensusAnalyserException {
            CensusAnalyser censusAnalyzer = new CensusAnalyser();
            int statePopulation= censusAnalyzer.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyzer.getPopulationWiseSortedCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCsv = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(607688, censusCsv[0].population);
        }

    @Test
    public void givenIndiaCensusData_WhenSortedOnDensity_ShouldReturnResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            String sortedDensityData = censusAnalyser.getPopulationDensityWiseSortedData(INDIA_CENSUS_CSV_FILE_PATH);
            IndiaCensusCSV[] censusCsv = new Gson().fromJson(sortedDensityData, IndiaCensusCSV[].class);
            Assert.assertEquals(29, censusCsv[0].densityPerSqKm);
        } catch (CensusAnalyserException e) {
        }
    }
}
