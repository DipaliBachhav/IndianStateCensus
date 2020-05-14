package censusanalyser;

public class IndiaCensusDAO {
    public int population;
    public int areaInSqkm;
    public int densityPerSqKm;
    public String state;
    public String stateCode;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV){
        state=indiaCensusCSV.state;
        areaInSqkm=indiaCensusCSV.areaInSqKm;
        densityPerSqKm=indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;
    }
}
