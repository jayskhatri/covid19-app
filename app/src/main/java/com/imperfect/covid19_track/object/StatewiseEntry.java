package com.imperfect.covid19_track.object;

public class StatewiseEntry {
    private String active;
    private String confirmed;
    private String deaths;
    private String recovered;
    private String state = "temp";
    private String lastUpdatedTime;
    private String delta_active;
    private String delta_confirmed;
    private String delta_recovered;
    private String delta_deceased;

    public StatewiseEntry(String active, String confirmed, String deaths, String recovered, String state, String lastUpdatedTime, String delta_active, String delta_confirmed, String delta_recovered, String delta_deceased) {
        this.active = active;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
        this.state = state;
        this.lastUpdatedTime = lastUpdatedTime;
        this.delta_active = delta_active;
        this.delta_confirmed = delta_confirmed;
        this.delta_recovered = delta_recovered;
        this.delta_deceased = delta_deceased;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getDelta_active() {
        return "+" +delta_active;
    }

    public void setDelta_active(String delta_active) {
        this.delta_active = delta_active;
    }

    public String getDelta_confirmed() {
        return "+" + delta_confirmed;
    }

    public void setDelta_confirmed(String delta_confirmed) {
        this.delta_confirmed = delta_confirmed;
    }

    public String getDelta_recovered() {
        return "+" +delta_recovered;
    }

    public void setDelta_recovered(String delta_recovered) {
        this.delta_recovered = delta_recovered;
    }

    public String getDelta_deceased() {
        return "+" +delta_deceased;
    }

    public void setDelta_deceased(String delta_deceased) {
        this.delta_deceased = delta_deceased;
    }
}
