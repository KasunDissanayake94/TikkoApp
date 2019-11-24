package com.example.tikkoapp.model;

import java.util.Date;

public class Information {
    private String id;
    private String station_name;
    private String date;
    private String comment ;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStation_name(String  station_name) {
        this.station_name = station_name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getStation_name() {
        return station_name;
    }

    public String getDate() {
        return date;
    }
}
