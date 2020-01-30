package com.vibhav.myapplication;

import android.graphics.Bitmap;

import java.io.Serializable;

public class StudentModel implements Serializable {
    private String sID, sName, cID, gender, dob, fname, fno, mail, address, last_date, sno, pass;
    private byte[] photo;

    public byte[] getPhoto() { return photo; }

    public void setPhoto(byte[] photo) { this.photo = photo; }

    public String getPass() { return pass; }

    public void setPass(String pass) { this.pass = pass; }

    public String getSno() { return sno; }

    public void setSno(String sno) { this.sno = sno; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getDob() { return dob; }

    public void setDob(String dob) { this.dob = dob; }

    public String getFname() { return fname; }

    public void setFname(String fname) { this.fname = fname; }

    public String getFno() { return fno; }

    public void setFno(String fno) { this.fno = fno; }

    public String getMail() { return mail; }

    public void setMail(String mail) { this.mail = mail; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getLast_date() { return last_date; }

    public void setLast_date(String last_date) { this.last_date = last_date; }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public void setStudentName(String sName) {
        this.sName = sName;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getsID() { return sID; }

    public String getStudentName() { return sName; }

    public String getcID() { return cID; }
}
