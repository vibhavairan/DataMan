package com.vibhav.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StudentCustomAdapter extends BaseAdapter {
    public Context mContext;
    public List<StudentModel> mStudentList = null;
    public List<StudentModel> mStudentFilteredlist = null;

    public StudentCustomAdapter(Context mContext, List<StudentModel> mList) {

        this.mContext = mContext;
        this.mStudentList = mList;
        this.mStudentFilteredlist = new ArrayList<>();
        this.mStudentFilteredlist.addAll(mStudentList);
    }

    @Override
    public int getCount() {
        return mStudentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mStudentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.list_student_item, null);
        }

        StudentModel p = (StudentModel) getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.stud_name);
            ImageView iv1 = (ImageView) v.findViewById(R.id.stud_photo);

            if (tt1 != null) {
                tt1.setText(p.getStudentName());
            }

            if (iv1 != null) {
                byte[] data = p.getPhoto();
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                iv1.setImageBitmap(bmp);
            }


        }

        return v;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mStudentList.clear();
        if (charText.length() == 0) {
            mStudentList.addAll(mStudentFilteredlist);
        }
        else
        {
            for (StudentModel wp : mStudentFilteredlist) {
                if (wp.getStudentName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mStudentList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
