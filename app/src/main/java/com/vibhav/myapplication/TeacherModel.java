package com.vibhav.myapplication;

import java.io.Serializable;

public class TeacherModel implements Serializable {
    private String teacherID;
    private String teachName;
    private String teachpass;
    private String teachdob;
    private String teachmail;
    private String teachadd;
    private String teachnum;
    private String teachgender;
    private byte[] teachphoto;

    public byte[] getTeachphoto() { return teachphoto; }

    public void setTeachphoto(byte[] teachphoto) { this.teachphoto = teachphoto; }

    public String getTeachgender() { return teachgender; }

    public void setTeachgender(String teachgender) { this.teachgender = teachgender; }



    public String getTeachdob() { return teachdob; }

    public void setTeachdob(String teachdob) { this.teachdob = teachdob; }

    public String getTeachmail() { return teachmail; }

    public void setTeachmail(String teachmail) { this.teachmail = teachmail; }

    public String getTeachadd() { return teachadd; }

    public void setTeachadd(String teachadd) { this.teachadd = teachadd; }

    public String getTeachnum() { return teachnum; }

    public void setTeachnum(String teachnum) { this.teachnum = teachnum; }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public void setTeachName(String teachName) { this.teachName = teachName; }

    public void setTeachpass(String teachpass) {
        this.teachpass = teachpass;
    }

    public String getTeacherID() { return teacherID; }

    public String getTeachName() { return teachName; }

    public String getTeachpass() { return teachpass; }
}
