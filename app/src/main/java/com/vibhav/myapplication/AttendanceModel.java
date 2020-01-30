package com.vibhav.myapplication;

public class AttendanceModel {
    private String cid, sid, subid, percentage, date, total, present;
    private boolean ispresent = false;

    public boolean isIspresent() { return ispresent; }

    public void setIspresent(boolean ispresent) { this.ispresent = ispresent; }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getCid() { return cid; }

    public String getSid() { return sid; }

    public String getSubid() { return subid; }

    public String getTotal() { return total; }

    public String getPresent() { return present; }
}
