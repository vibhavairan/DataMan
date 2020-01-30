package com.vibhav.myapplication;

import java.io.Serializable;

public class MapModel implements Serializable {
    private String cID = "", subID = "", tID = "", date;

    public String getcID() { return cID; }

    public String getSubID() { return subID; }

    public String gettID() { return tID; }

    public String getDate() { return date; }

    public void setcID(String cID) { this.cID = cID; }

    public void setSubID(String subID) { this.subID = subID; }

    public void settID(String tID) { this.tID = tID; }

    public void setDate(String date) { this.date = date; }
}
