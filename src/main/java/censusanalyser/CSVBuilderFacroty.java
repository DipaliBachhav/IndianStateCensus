package censusanalyser;

public class CSVBuilderFacroty {
    public static ICSVBuilder createCSVBuilder() {
        return new OpenCSVBuilder();
    }
}
