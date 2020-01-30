package com.vibhav.myapplication;

import java.io.Serializable;

public class ClassModel implements Serializable
{
    private String cID, className, maxStu;

    public String getcID() { return cID; }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMaxStu(String maxStu) {
        this.maxStu = maxStu;
    }

    public String getClassName() {
        return className;
    }

    public String getMaxStu() {
        return maxStu;
    }
}
