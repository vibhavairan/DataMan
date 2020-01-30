package com.vibhav.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.List;

public class AttendanceCustomAdapter extends BaseAdapter {
    public Context mContext;
    public List<AttendanceModel> mAttendanceList = null;
    private SQLiteHelper sqLiteHelper;
    RadioGroup radioAttendance;
    boolean z;

    public AttendanceCustomAdapter(Context mContext, List<AttendanceModel> mList, SQLiteHelper sqLiteHelper,boolean z) {
        this.mContext = mContext;
        this.mAttendanceList = mList;
        this.sqLiteHelper = sqLiteHelper;
        this.z = z;
    }


    @Override
    public int getCount() {
        return mAttendanceList.size();
    }

    @Override public Object getItem(int position) { return mAttendanceList.get(position); }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            if(z)
            v = vi.inflate(R.layout.list_attendance_item, null);
            else
                v = vi.inflate(R.layout.list_student_attendance_item,null);
        }
        final AttendanceModel p = (AttendanceModel) getItem(position);
        if (p != null) {
            if (z)
            {
                TextView tt1;
                ImageView iv1;
                tt1 = (TextView) v.findViewById(R.id.nameStudent);
                iv1 = (ImageView) v.findViewById(R.id.imageStudent);
                if (tt1 != null) {
                    tt1.setText(sqLiteHelper.getStudentName(p));
                }
                if (iv1 != null) {
                    byte[] data = sqLiteHelper.getStudentPhoto(p);
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    iv1.setImageBitmap(bmp);
                }
                radioAttendance = (RadioGroup) v.findViewById(R.id.rg_attendance);
                radioAttendance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.p_radio:
                                p.setIspresent(true);
                                break;
                                case R.id.a_radio:
                                    p.setIspresent(false);
                                    break;
                           }
                       }
                   });
               }
            else
            {
                TextView tt1;
                TextView tt2;
                TextView tt3;
                tt1 = (TextView) v.findViewById(R.id.subject_name_attendance);
                tt2 = (TextView) v.findViewById(R.id.classes_number);
                tt3 = (TextView) v.findViewById(R.id.percentage_attendance);
                if(tt1 != null)
                {
                    tt1.setText(sqLiteHelper.getSubjectName2(p));
                }
                if(tt2 != null)
                {   String s = "Classes Attended: "+p.getPresent()+" / "+p.getTotal();
                    tt2.setText(s);
                }
                if(tt3 != null)
                {
                    float pr = (float) Integer.parseInt(p.getPresent());
                    float ab = (float) Integer.parseInt(p.getTotal());
                    float f = (pr/ab)*100.0f;
                    String s = f+" %";
                    tt3.setText(s);
                }
            }
           }

        return v;
    }

  /*  public Filter getFilter() {
        return null;
    }*/
}
