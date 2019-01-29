package com.itqf.domain;

public class Newss {
    private Integer id;

    private String keywords;

    private String eventname;

    private String weightvalue;

    private String loadtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname == null ? null : eventname.trim();
    }

    public String getWeightvalue() {
        return weightvalue;
    }

    public void setWeightvalue(String weightvalue) {
        this.weightvalue = weightvalue == null ? null : weightvalue.trim();
    }

    public String getLoadtime() {
        return loadtime;
    }

    public void setLoadtime(String loadtime) {
        this.loadtime = loadtime == null ? null : loadtime.trim();
    }
}