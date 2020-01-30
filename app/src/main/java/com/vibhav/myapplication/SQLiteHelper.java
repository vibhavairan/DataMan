package com.vibhav.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";
    public static final String CLASS_TABLE_NAME = "CLASS";
    public static final String COLUMN_CID = "CID";
    public static final String COLUMN_CLASS_NAME = "CLASS_NAME";
    public static final String COLUMN_MAX_STU = "MAX_STU";
    public static final String STUDENT_TABLE_NAME = "STUDENT";
    public static final String COLUMN_SID = "SID";
    public static final String COLUMN_STUDENT_NAME = "STUDENT_NAME";
    public static final String SUBJECT_TABLE_NAME = "SUBJECT";
    public static final String COLUMN_SUBID = "SUBID";
    public static final String COLUMN_SUBJECT_NAME = "SUBJECT_NAME";
    public static final String TEACHER_TABLE_NAME = "TEACHER";
    public static final String COLUMN_TID = "TID";
    public static final String COLUMN_TEACHER_NAME = "TEACHER_NAME";
    public static final String COLUMN_PASS = "PASS";
    public static final String MAP_TABLE_NAME = "MAP";
    public static final String ATTENDANCE_TABLE_NAME = "ATTENDANCE";
    public static final String COLUMN_TOTAL_CLASS = "TOTAL_CLASS";
    public static final String COLUMN_PRESENT_CLASS = "PRESENT_CLASS";
    public static final String COLUMN_LAST_DATE = "LAST_DATE";
    public static final String COLUMN_PHOTO = "PHOTO";
    public static final String COLUMN_GENDER = "GENDER";
    public static final String COLUMN_DOB = "DOB";
    public static final String COLUMN_FATHER_NAME = "FATHER_NAME";
    public static final String COLUMN_CONTACT_NO = "CONTACT_NO";
    public static final String COLUMN_FATHER_CONTACT_NUMBER = "FATHER_NO";
    public static final String COLUMN_EMAIL_ID = "EMAIL";
    public static final String COLUMN_ADDRESS = "ADDRESS";
    public static final String TEACHER_SUBJECT_TABLE_NAME = "TEACHER_SUBJECT";
    private SQLiteDatabase database;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CLASS_TABLE_NAME + " ( " + COLUMN_CID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CLASS_NAME + " VARCHAR(30), " + COLUMN_MAX_STU + " INTEGER);");
        db.execSQL("create table " + STUDENT_TABLE_NAME + " ( " + COLUMN_SID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_STUDENT_NAME + " VARCHAR(30), "  + COLUMN_CID + " INTEGER,"+ COLUMN_PHOTO + " BLOB,"+ COLUMN_GENDER + " VARCHAR(10),"+ COLUMN_CONTACT_NO + " VARCHAR(20),"+ COLUMN_DOB + " VARCHAR(20),"+ COLUMN_EMAIL_ID + " VARCHAR(30),"+ COLUMN_ADDRESS + " VARCHAR(200),"+ COLUMN_FATHER_NAME + " VARCHAR(30),"+ COLUMN_FATHER_CONTACT_NUMBER + " VARCHAR(20),"+ COLUMN_LAST_DATE + " DATE," + COLUMN_PASS + " VARCHAR(20),"+"FOREIGN KEY (" + COLUMN_CID + ") REFERENCES " +CLASS_TABLE_NAME+ "("+ COLUMN_CID+" ));");
        db.execSQL("create table " + SUBJECT_TABLE_NAME + " (" + COLUMN_SUBID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_SUBJECT_NAME + " VARCHAR(30));");
        db.execSQL("create table " + TEACHER_TABLE_NAME + " (" + COLUMN_TID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TEACHER_NAME + " VARCHAR(30)," + COLUMN_PHOTO + " BLOB,"+ COLUMN_GENDER + " VARCHAR(15)," + COLUMN_DOB + " VARCHAR(15)," + COLUMN_ADDRESS + " VARCHAR(200)," + COLUMN_EMAIL_ID + " VARCHAR(30)," + COLUMN_CONTACT_NO + " VARCHAR(20)," + COLUMN_PASS + " VARCHAR(30));");
        db.execSQL("create table " + MAP_TABLE_NAME + " (" + COLUMN_CID + " INTEGER ," + COLUMN_TID + " INTEGER," + COLUMN_SUBID + " INTEGER," + COLUMN_LAST_DATE + " DATE,PRIMARY KEY ("+ COLUMN_CID +","+ COLUMN_SUBID +"));");
        db.execSQL("create table " + ATTENDANCE_TABLE_NAME + " ( " + COLUMN_CID + " INTEGER ," + COLUMN_SID + " INTEGER," + COLUMN_SUBID + " INTEGER," + COLUMN_TOTAL_CLASS + " INTEGER,"+ COLUMN_PRESENT_CLASS +" INTEGER);");
        db.execSQL("create table " + TEACHER_SUBJECT_TABLE_NAME + " (" + COLUMN_TID + " INTEGER," + COLUMN_SUBID + " INTEGER);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CLASS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SUBJECT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TEACHER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MAP_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ATTENDANCE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TEACHER_SUBJECT_TABLE_NAME);
        onCreate(db);
    }
    public void insertRecordClass(ClassModel newClass) {
        database = this.getReadableDatabase();
        database.execSQL("INSERT INTO " + CLASS_TABLE_NAME + "("+ COLUMN_CLASS_NAME + "," + COLUMN_MAX_STU + ") VALUES('" +newClass.getClassName() + "','" + newClass.getMaxStu() + "')");
        database.close();
    }
    public void updateRecordClass(ClassModel newClass) {
        database = this.getReadableDatabase();
        database.execSQL("update " + CLASS_TABLE_NAME + " set " + COLUMN_CLASS_NAME + " = '" + newClass.getClassName() + "', " + COLUMN_MAX_STU + " = '" + newClass.getMaxStu() + "' where " + COLUMN_CID + " = '" + newClass.getcID() + "'");
        database.close();
    }
    public void deleteRecordClass(ClassModel newClass) {
        database = this.getReadableDatabase();
        database.execSQL("delete from " + CLASS_TABLE_NAME + " where " + COLUMN_CID + " = '" + newClass.getcID() + "'");
        database.execSQL("delete from " + STUDENT_TABLE_NAME + " where " + COLUMN_CID + " = '" + newClass.getcID() + "'");
        database.execSQL("delete from " + MAP_TABLE_NAME + " where " + COLUMN_CID + " = '" + newClass.getcID() + "'");
        database.execSQL("delete from " + ATTENDANCE_TABLE_NAME + " where " + COLUMN_CID + " = '" + newClass.getcID() + "'");
        database.close();
    }
    public ArrayList<ClassModel> getAllRecordsClass() {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + CLASS_TABLE_NAME, null);
        ArrayList<ClassModel> classes = new ArrayList<>();
        ClassModel temp;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                temp = new ClassModel();
                temp.setcID(cursor.getString(0));
                temp.setClassName(cursor.getString(1));
                temp.setMaxStu(cursor.getString(2));
                classes.add(temp);
            }
        }
        cursor.close();
        database.close();
        return classes;
    }

    public void insertRecordStudent(StudentModel newStudent) {
        database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_STUDENT_NAME, newStudent.getStudentName());
        cv.put(COLUMN_CID, newStudent.getcID());
        cv.put(COLUMN_PHOTO, newStudent.getPhoto());
        cv.put(COLUMN_GENDER, newStudent.getGender());
        cv.put(COLUMN_CONTACT_NO, newStudent.getSno());
        cv.put(COLUMN_DOB, newStudent.getDob());
        cv.put(COLUMN_EMAIL_ID, newStudent.getMail());
        cv.put(COLUMN_ADDRESS, newStudent.getAddress());
        cv.put(COLUMN_FATHER_NAME, newStudent.getFname());
        cv.put(COLUMN_FATHER_CONTACT_NUMBER, newStudent.getFno());
        cv.put(COLUMN_LAST_DATE,   newStudent.getLast_date());
        cv.put(COLUMN_PASS,   newStudent.getPass());
        database.insert( STUDENT_TABLE_NAME, null, cv );
        database.close();
    }

    public void updateRecordStudent(StudentModel newStudent) {
        database = this.getWritableDatabase();
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put(COLUMN_STUDENT_NAME,newStudent.getStudentName());
        dataToInsert.put(COLUMN_PHOTO,newStudent.getPhoto());
        dataToInsert.put(COLUMN_GENDER,newStudent.getGender());
        dataToInsert.put(COLUMN_CONTACT_NO,newStudent.getSno());
        dataToInsert.put(COLUMN_DOB,newStudent.getDob());
        dataToInsert.put(COLUMN_EMAIL_ID,newStudent.getMail());
        dataToInsert.put(COLUMN_ADDRESS,newStudent.getAddress());
        dataToInsert.put(COLUMN_FATHER_NAME,newStudent.getFname());
        dataToInsert.put(COLUMN_FATHER_CONTACT_NUMBER,newStudent.getFno());
        dataToInsert.put(COLUMN_LAST_DATE,newStudent.getLast_date());
        dataToInsert.put(COLUMN_PASS,newStudent.getPass());
        String where = COLUMN_SID + "=" + newStudent.getsID();
        try{
            database.update(STUDENT_TABLE_NAME, dataToInsert, where, null);
        }
        catch (Exception e){}
        database.close();
    }

    public void deleteRecordStudent(StudentModel newStudent) {
        database = this.getReadableDatabase();
        database.execSQL("delete from " + STUDENT_TABLE_NAME + " where " + COLUMN_SID + " = '" + newStudent.getsID() + "'");
        database.execSQL("delete from " + ATTENDANCE_TABLE_NAME + " where " + COLUMN_SID + " = '" + newStudent.getsID() + "'");
        database.close();
    }
    public ArrayList<StudentModel> getAllRecordsStudents(ClassModel classtemp) {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + STUDENT_TABLE_NAME + " WHERE " + COLUMN_CID + " LIKE '" + classtemp.getcID() +"'" , null);
        ArrayList<StudentModel> students = new ArrayList<>();
        StudentModel temp;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                temp = new StudentModel();
                temp.setsID(cursor.getString(0));
                temp.setStudentName(cursor.getString(1));
                temp.setcID(cursor.getString(2));
                temp.setPhoto(cursor.getBlob(3));
                temp.setGender(cursor.getString(4));
                temp.setSno(cursor.getString(5));
                temp.setDob(cursor.getString(6));
                temp.setMail(cursor.getString(7));
                temp.setAddress(cursor.getString(8));
                temp.setFname(cursor.getString(9));
                temp.setFno(cursor.getString(10));
                temp.setLast_date(cursor.getString(11));
                temp.setPass(cursor.getString(12));
                students.add(temp);
            }
        }
        cursor.close();
        database.close();
        return students;
    }
    public StudentModel getSingleRecordStudent(StudentModel s) {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + STUDENT_TABLE_NAME + " WHERE " + COLUMN_SID + " LIKE '" + s.getsID() +"'" , null);
        StudentModel temp;
        cursor.moveToNext();
        temp = new StudentModel();
        temp.setsID(cursor.getString(0));
        temp.setStudentName(cursor.getString(1));
        temp.setcID(cursor.getString(2));
        temp.setPhoto(cursor.getBlob(3));
        temp.setGender(cursor.getString(4));
        temp.setSno(cursor.getString(5));
        temp.setDob(cursor.getString(6));
        temp.setMail(cursor.getString(7));
        temp.setAddress(cursor.getString(8));
        temp.setFname(cursor.getString(9));
        temp.setFno(cursor.getString(10));
        temp.setLast_date(cursor.getString(11));
        temp.setPass(cursor.getString(12));
        cursor.close();
        database.close();
        return temp;
    }
    public void insertRecordSubject(SubjectModel newSub) {
        database = this.getReadableDatabase();
        database.execSQL("INSERT INTO " + SUBJECT_TABLE_NAME + "(" + COLUMN_SUBJECT_NAME + ") VALUES('" + newSub.getSubjectName() + "')");
        database.close();
    }

    public void deleteRecordSubject(SubjectModel newSub) {
        database = this.getReadableDatabase();
        database.execSQL("delete from " + SUBJECT_TABLE_NAME + " where " + COLUMN_SUBID + " = '" + newSub.getsubID() + "'");
        database.execSQL("delete from " + ATTENDANCE_TABLE_NAME + " where " + COLUMN_SUBID + " = '" + newSub.getsubID() + "'");
        database.execSQL("delete from " + TEACHER_SUBJECT_TABLE_NAME + " where " + COLUMN_SUBID + " = '" + newSub.getsubID() + "'");
        database.execSQL("delete from " + MAP_TABLE_NAME + " where " + COLUMN_SUBID + " = '" + newSub.getsubID() + "'");
        database.close();
    }
    public ArrayList<SubjectModel> getAllRecordsSubject() {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + SUBJECT_TABLE_NAME, null);
        ArrayList<SubjectModel> subjects = new ArrayList<>();
        SubjectModel temp;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                temp = new SubjectModel();
                temp.setsubID(cursor.getString(0));
                temp.setSubjectName(cursor.getString(1));
                subjects.add(temp);
            }
        }
        cursor.close();
        database.close();
        return subjects;
    }
    public void insertRecordTeacherSubject(TeacherSubjectModel newSub) {
        database = this.getReadableDatabase();
        database.execSQL("INSERT INTO " + TEACHER_SUBJECT_TABLE_NAME + "("+ COLUMN_TID + "," + COLUMN_SUBID + ") VALUES('" + newSub.getTeacherID() + "','" + newSub.getSubjectID() + "')");
        database.close();
    }
    public boolean checkTeacherSubject(SubjectModel s,TeacherModel t)
    {
        boolean bool;
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TEACHER_SUBJECT_TABLE_NAME + " WHERE " + COLUMN_TID + " = " + t.getTeacherID() + " AND " + COLUMN_SUBID + " = " + s.getsubID(),null);
        if(cursor.getCount()>0)
            bool = true;
        else
            bool = false;
        cursor.close();
        database.close();
        return bool;
    }
    public void deleteSubjectTeacher(TeacherModel t)
    {
        database = this.getReadableDatabase();
        database.execSQL("DELETE FROM "+ TEACHER_SUBJECT_TABLE_NAME + " WHERE "+ COLUMN_TID + " = " + t.getTeacherID());
        database.close();
    }

    public ArrayList<SubjectModel> getAllRecordsSubjectTeacher(MapModel p)
    {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + SUBJECT_TABLE_NAME + " WHERE "+ COLUMN_SUBID + " IN ( SELECT "+ COLUMN_SUBID +" FROM " + TEACHER_SUBJECT_TABLE_NAME + " WHERE "+ COLUMN_TID + " = " + p.gettID() + ")", null);
        ArrayList<SubjectModel> subjects = new ArrayList<>();
        SubjectModel temp;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                temp = new SubjectModel();
                temp.setsubID(cursor.getString(0));
                temp.setSubjectName(cursor.getString(1));
                subjects.add(temp);
            }
        }
        cursor.close();
        database.close();
        return subjects;
    }
    public void insertRecordTeacher(TeacherModel newTeacher) {
        database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TID, newTeacher.getTeacherID());
        cv.put(COLUMN_TEACHER_NAME, newTeacher.getTeachName());
        cv.put(COLUMN_PHOTO, newTeacher.getTeachphoto());
        cv.put(COLUMN_GENDER, newTeacher.getTeachgender());
        cv.put(COLUMN_DOB, newTeacher.getTeachdob());
        cv.put(COLUMN_ADDRESS, newTeacher.getTeachadd());
        cv.put(COLUMN_EMAIL_ID, newTeacher.getTeachmail());
        cv.put(COLUMN_CONTACT_NO, newTeacher.getTeachnum());
        cv.put(COLUMN_PASS, newTeacher.getTeachpass());
        database.insert(TEACHER_TABLE_NAME,null,cv);
        database.close();
    }
    public void updateRecordTeacher(TeacherModel newTeacher) {
        database = this.getReadableDatabase();
        database.execSQL("update " + TEACHER_TABLE_NAME + " set " + COLUMN_PASS + " = '" + newTeacher.getTeachpass() + "' where " + COLUMN_TID + " = '" + newTeacher.getTeacherID() + "'");
        database.close();
    }
    public void deleteRecordTeacher(TeacherModel newTeacher) {
        database = this.getReadableDatabase();
        database.execSQL("delete from " + TEACHER_TABLE_NAME + " where " + COLUMN_TID + " = '" + newTeacher.getTeacherID() + "'");
        database.execSQL("delete from " + ATTENDANCE_TABLE_NAME + " where " + COLUMN_SUBID + " IN " + "( select Distinct("+ COLUMN_SUBID + ") from " + TEACHER_SUBJECT_TABLE_NAME + " where " + COLUMN_TID + " = '" + newTeacher.getTeacherID() + "')" );
        database.execSQL("delete from " + MAP_TABLE_NAME + " where " + COLUMN_TID + " = '" + newTeacher.getTeacherID() + "'");
        database.execSQL("delete from " + TEACHER_SUBJECT_TABLE_NAME + " where " + COLUMN_TID + " = '" + newTeacher.getTeacherID() + "'");
        database.close();
    }
    public ArrayList<TeacherModel> getAllRecordsTeacher() {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TEACHER_TABLE_NAME, null);
        ArrayList<TeacherModel> teachers = new ArrayList<>();
        TeacherModel temp;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                temp = new TeacherModel();
                temp.setTeacherID(cursor.getString(0));
                temp.setTeachName(cursor.getString(1));
                temp.setTeachphoto(cursor.getBlob(2));
                temp.setTeachgender(cursor.getString(3));
                temp.setTeachdob(cursor.getString(4));
                temp.setTeachadd(cursor.getString(5));
                temp.setTeachmail(cursor.getString(6));
                temp.setTeachnum(cursor.getString(7));
                temp.setTeachpass(cursor.getString(8));
                teachers.add(temp);
            }
        }
        cursor.close();
        database.close();
        return teachers;
    }
    public TeacherModel getSingleRecordsTeacher(TeacherModel t) {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TEACHER_TABLE_NAME + " WHERE "+ COLUMN_TID + " = "+ t.getTeacherID(), null);
        TeacherModel temp = new TeacherModel();
        cursor.moveToNext();
        temp.setTeacherID(cursor.getString(0));
        temp.setTeachName(cursor.getString(1));
        temp.setTeachphoto(cursor.getBlob(2));
        temp.setTeachgender(cursor.getString(3));
        temp.setTeachdob(cursor.getString(4));
        temp.setTeachadd(cursor.getString(5));
        temp.setTeachmail(cursor.getString(6));
        temp.setTeachnum(cursor.getString(7));
        temp.setTeachpass(cursor.getString(8));
        cursor.close();
        database.close();
        return temp;
    }
    public boolean passcheckTeacher(String tid,String pass)
    {   boolean z = false;
        database = this.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT "+ COLUMN_PASS + " FROM "+TEACHER_TABLE_NAME+" WHERE "+ COLUMN_TID +" = "+tid,null);
        c.moveToNext();
        String s = c.getString(0);
        if(s.equals(pass))
            z = true;
        c.close();
        database.close();
        return (z);
    }
    public boolean passcheckStudent(String tid,String pass)
    {   boolean z = false;
        database = this.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT "+ COLUMN_PASS + " FROM "+ STUDENT_TABLE_NAME +" WHERE "+ COLUMN_SID +" = "+ tid,null);
        c.moveToNext();
        String s = c.getString(0);
        if(s.equals(pass))
            z = true;
        c.close();
        database.close();
        return (z);
    }
    public boolean checkmaprecords(MapModel m)
    {
        database = this.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT COUNT("+ COLUMN_CID + ") FROM "+ MAP_TABLE_NAME +" WHERE "+ COLUMN_SUBID +" = "+ m.getSubID()+" AND "+ COLUMN_CID +" = "+ m.getcID(),null);
        c.moveToNext();
        boolean z = false;
        int s = Integer.parseInt(c.getString(0));
        if(s==0)
            z = true;
        c.close();
        database.close();
        return z;
    }

    public void maprecords(MapModel m)
    {
        database = this.getReadableDatabase();
        database.execSQL("INSERT INTO " + MAP_TABLE_NAME + "("+ COLUMN_CID + "," + COLUMN_SUBID + "," + COLUMN_TID + "," + COLUMN_LAST_DATE + ") VALUES('" + m.getcID() + "','" + m.getSubID() + "','" + m.gettID() + "','yyyy/MM/dd')");
        database.close();
    }
    public String getSubjectName(MapModel m)
    {
        database = this.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT "+ COLUMN_SUBJECT_NAME + " FROM "+SUBJECT_TABLE_NAME+" WHERE "+ COLUMN_SUBID +" = "+ m.getSubID(),null);
        c.moveToNext();
        String s = c.getString(0);
        c.close();
        database.close();
        return s;
    }
    public String getTeacherName(MapModel m)
    {
        database = this.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT "+ COLUMN_TEACHER_NAME + " FROM "+TEACHER_TABLE_NAME+" WHERE "+ COLUMN_TID +" = "+ m.gettID(),null);
        c.moveToNext();
        String s = c.getString(0);
        c.close();
        database.close();
        return s;
    }
    public String getClassName(MapModel m)
    {
        database = this.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT "+ COLUMN_CLASS_NAME + " FROM "+CLASS_TABLE_NAME+" WHERE "+ COLUMN_CID +" = "+ m.getcID(),null);
        c.moveToNext();
        String s = c.getString(0);
        c.close();
        database.close();
        return s;
    }
    public ArrayList<MapModel> getAllRecordsMap() {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + MAP_TABLE_NAME, null);
        ArrayList<MapModel> maps = new ArrayList<>();
        MapModel temp;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                temp = new MapModel();
                temp.setcID(cursor.getString(0));
                temp.settID(cursor.getString(1));
                temp.setSubID(cursor.getString(2));
                maps.add(temp);
            }
        }
        cursor.close();
        database.close();
        return maps;
    }
    public void deleteRecordMap(MapModel m) {
        database = this.getReadableDatabase();
        database.execSQL("delete from " + MAP_TABLE_NAME + " where " + COLUMN_SUBID + " ='" + m.getSubID() + "' AND "+ COLUMN_CID + " = '" + m.getcID() + "'");
        database.execSQL("delete from " + ATTENDANCE_TABLE_NAME + " where " + COLUMN_SUBID + " ='" + m.getSubID() + "' AND "+ COLUMN_CID + " = '" + m.getcID() + "'");
        database.close();
    }
    public ArrayList<MapModel> getAllRecordsTeacherMap(TeacherModel m) {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + MAP_TABLE_NAME + " WHERE " + COLUMN_TID + " = "+m.getTeacherID(), null);
        ArrayList<MapModel> records = new ArrayList<>();
        MapModel temp;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                temp = new MapModel();
                temp.setcID(cursor.getString(0));
                temp.settID(cursor.getString(1));
                temp.setSubID(cursor.getString(2));
                temp.setDate(cursor.getString(3));
                records.add(temp);
            }
        }
        cursor.close();
        database.close();
        return records;
    }

    public void updateDate(MapModel m)
    {
        database = this.getReadableDatabase();
        String s=" UPDATE "+ MAP_TABLE_NAME + " SET "+ COLUMN_LAST_DATE +" = '"+ m.getDate() +"' WHERE "+ COLUMN_CID + "="+ m.getcID()+ " AND "+ COLUMN_SUBID + " = "+m.getSubID();
        database.execSQL(s);
    }
    public void insertRecordAttendance(MapModel m) {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT "+ COLUMN_SID +" FROM " + STUDENT_TABLE_NAME + " WHERE " + COLUMN_CID + " = " + m.getcID(), null);
        StudentModel temp;
        if (cursor.getCount() > 0) {
            for(int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                temp = new StudentModel();
                temp.setsID(cursor.getString(0));
                database.execSQL("INSERT INTO " + ATTENDANCE_TABLE_NAME + "("+ COLUMN_CID + "," + COLUMN_SID + "," + COLUMN_SUBID  + "," + COLUMN_TOTAL_CLASS + "," + COLUMN_PRESENT_CLASS +") VALUES('" + m.getcID() + "','" + temp.getsID() + "','" + m.getSubID()  +"','" + 0 + "','" + 0 +"')");
            }
        }
        cursor.close();
        database.close();
    }
    public void insertRecordAttendanceLater(StudentModel s) {
        database = this.getReadableDatabase();
        Cursor cursor3 = database.rawQuery("SELECT max(" + COLUMN_SID + ") FROM " + STUDENT_TABLE_NAME,null);
        cursor3.moveToNext();
        Cursor cursor = database.rawQuery("SELECT "+ COLUMN_SUBID +" FROM " + MAP_TABLE_NAME + " WHERE " + COLUMN_CID + " = " + s.getcID(), null);
        SubjectModel temp;
        if (cursor.getCount() > 0) {
            for(int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                temp = new SubjectModel();
                temp.setsubID(cursor.getString(0));
                database.execSQL("INSERT INTO " + ATTENDANCE_TABLE_NAME + "("+ COLUMN_CID + "," + COLUMN_SID + "," + COLUMN_SUBID  + "," + COLUMN_TOTAL_CLASS + "," + COLUMN_PRESENT_CLASS +") VALUES('" + s.getcID() + "','" + cursor3.getString(0) + "','" + temp.getsubID() +"','" + 0 + "','" + 0 +"')");
            }
        }
        cursor.close();
        database.close();
    }
    public String getStudentName(AttendanceModel m)
    {
        database = this.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT "+ COLUMN_STUDENT_NAME + " FROM "+STUDENT_TABLE_NAME+" WHERE "+ COLUMN_SID +" = "+ m.getSid(),null);
        c.moveToNext();
        String s = c.getString(0);
        c.close();
        database.close();
        return s;
    }
    public String getTeacherName2(TeacherModel m)
    {
        database = this.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT "+ COLUMN_TEACHER_NAME + " FROM "+TEACHER_TABLE_NAME+" WHERE "+ COLUMN_TID +" = "+ m.getTeacherID(),null);
        c.moveToNext();
        String s = c.getString(0);
        c.close();
        database.close();
        return s;
    }
    public String getSubjectName2(AttendanceModel m)
    {
        database = this.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT "+ COLUMN_SUBJECT_NAME + " FROM "+SUBJECT_TABLE_NAME+" WHERE "+ COLUMN_SUBID +" = "+ m.getSubid(),null);
        c.moveToNext();
        String s = c.getString(0);
        c.close();
        database.close();
        return s;
    }
    public byte[] getStudentPhoto(AttendanceModel m)
    {
        database = this.getReadableDatabase();
        Cursor c = database.rawQuery("SELECT "+ COLUMN_PHOTO + " FROM "+STUDENT_TABLE_NAME+" WHERE "+ COLUMN_SID +" = "+ m.getSid(),null);
        c.moveToNext();
        byte[] temp = c.getBlob(0);
        c.close();
        database.close();
        return temp;
    }

    public ArrayList<AttendanceModel> getAllRecordsStudentsAttendance(MapModel temp) {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + ATTENDANCE_TABLE_NAME + " WHERE " + COLUMN_CID + " LIKE '" + temp.getcID() +"' AND "+ COLUMN_SUBID + " LIKE '"+ temp.getSubID() + "'", null);
        ArrayList<AttendanceModel> students = new ArrayList<>();
        AttendanceModel tempnew;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                tempnew = new AttendanceModel();
                tempnew.setCid(cursor.getString(0));
                tempnew.setSid(cursor.getString(1));
                tempnew.setSubid(cursor.getString(2));
                tempnew.setTotal(cursor.getString(3));
                tempnew.setPresent(cursor.getString(4));
                students.add(tempnew);
            }
        }
        cursor.close();
        database.close();
        return students;
    }
    public ArrayList<AttendanceModel> getAllRecordsStudentsAttendanceLater(StudentModel temp) {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + ATTENDANCE_TABLE_NAME + " WHERE " + COLUMN_SID + " LIKE '" + temp.getsID() + "'", null);
        ArrayList<AttendanceModel> students = new ArrayList<>();
        AttendanceModel tempnew;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                tempnew = new AttendanceModel();
                tempnew.setCid(cursor.getString(0));
                tempnew.setSid(cursor.getString(1));
                tempnew.setSubid(cursor.getString(2));
                tempnew.setTotal(cursor.getString(3));
                tempnew.setPresent(cursor.getString(4));
                students.add(tempnew);
            }
        }
        cursor.close();
        database.close();
        return students;
    }
    public void updateAttendance(AttendanceModel t)
    {
        database = this.getReadableDatabase();
        if(t.isIspresent())
        database.execSQL("UPDATE " + ATTENDANCE_TABLE_NAME + " SET "+ COLUMN_TOTAL_CLASS + " = " + (Integer.parseInt(t.getTotal())+1) + " , " + COLUMN_PRESENT_CLASS + " = " + (Integer.parseInt(t.getPresent())+1)  +" WHERE "+ COLUMN_SUBID + " = "+ t.getSubid() + " AND "+ COLUMN_SID + " = " +t.getSid());
        else
            database.execSQL("UPDATE " + ATTENDANCE_TABLE_NAME + " SET "+ COLUMN_TOTAL_CLASS + " = " + (Integer.parseInt(t.getTotal())+1) + " WHERE "+ COLUMN_SUBID + " = "+ t.getSubid() + " AND "+ COLUMN_SID + " = " +t.getSid());
        database.close();
    }
}
