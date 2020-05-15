package censusanalyser;

public class CSVBuilderException extends Throwable {
    enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE;
    }

    ExceptionType type;

    public CSVBuilderException(ExceptionType type) {
        this.type = type;
    }
}