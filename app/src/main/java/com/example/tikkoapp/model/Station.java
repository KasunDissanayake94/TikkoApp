package com.example.tikkoapp.model;

public class Station {
    private String id;
    private String station_name;

    public Station(){

    }
    public Station(String id) {
        this.id = id;
    }

    public Station(String id, String station_name) {
        this.id = id;
        this.station_name = station_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getId() {
        return id;
    }

    public String getStation_name() {
        return station_name;
    }
}
