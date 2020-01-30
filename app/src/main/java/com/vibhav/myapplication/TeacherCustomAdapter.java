package com.vibhav.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TeacherCustomAdapter extends BaseAdapter {
    public Context mContext;
    public List<TeacherModel> mTeacherList = null;
    public List<TeacherModel> mTeacherFilteredList = null;


    public TeacherCustomAdapter(Context mContext, List<TeacherModel> mList) {
        this.mContext = mContext;
        this.mTeacherList = mList;
        this.mTeacherFilteredList = new ArrayList<>();
        this.mTeacherFilteredList.addAll(mTeacherList);
    }

    @Override
    public int getCount() { return mTeacherList.size(); }

    @Override
    public Object getItem(int position) { return mTeacherList.get(position); }

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
            v = vi.inflate(R.layout.list_teacher_item, null);
        }

        TeacherModel p = (TeacherModel) getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.teach_name);
            ImageView iv1 = (ImageView) v.findViewById(R.id.teach_photo);

            if (tt1 != null) {
                tt1.setText(p.getTeachName());
            }

          if (iv1 != null) {
                byte[] data = p.getTeachphoto();
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                iv1.setImageBitmap(bmp);
            }
        }
        return v;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mTeacherList.clear();
        if (charText.length() == 0) {
            mTeacherList.addAll(mTeacherFilteredList);
        }
        else
        {
            for (TeacherModel wp : mTeacherFilteredList) {
                if (wp.getTeachName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mTeacherList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
