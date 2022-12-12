package ru.anbn.mhz;

public class Frequency {

    // road;region;station;frequency

    private String road;
    private String region;
    private String station;
    private String frequency;

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }


    @Override
    public String toString(){
        return "\nДорога: " + getRoad() + "  Регион: " + getRegion() + "  Станция: " + getStation() + "  Частота: " + getFrequency();
    }

}
