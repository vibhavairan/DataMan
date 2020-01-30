package com.vibhav.myapplication;

public class SubjectModel {
    private String subID, subName;
    private boolean map;

    public boolean isMap() { return map; }

    public void setMap(boolean map) { this.map = map; }

    public void setsubID(String subID) {
        this.subID = subID;
    }

    public void setSubjectName(String subName) {
        this.subName = subName;
    }

    public String getsubID() { return subID; }

    public String getSubjectName() { return subName; }

}
