package com.vibhav.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClassCustomAdapter extends BaseAdapter {

    public Context mContext;
    public boolean z;
    public List<ClassModel> mClassList = null;
    public List<ClassModel> mClassFilteredList = null;

    public ClassCustomAdapter(Context mContext, List<ClassModel> mList, boolean z) {

        this.mContext = mContext;
        this.mClassList = mList;
        this.z = z;
        this.mClassFilteredList = new ArrayList<>();
        this.mClassFilteredList.addAll(mClassList);
    }


    @Override
    public int getCount() {
        return mClassList.size();
    }

    @Override
    public Object getItem(int position) {
        return mClassList.get(position);
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
            if(z)
                v = vi.inflate(R.layout.spinner_list_class, null);
            else
            v = vi.inflate(R.layout.list_item, null);
        }

        ClassModel p = (ClassModel) getItem(position);

        if (p != null) { TextView tt1,tt2;
        if(z)
        {tt1 = (TextView) v.findViewById(R.id.classnamespin);
            tt2 = (TextView) v.findViewById(R.id.classidspin);}
        else
        {tt1 = (TextView) v.findViewById(R.id.classname);
            tt2 = (TextView) v.findViewById(R.id.classid);}

            if (tt1 != null) {
                tt1.setText(p.getClassName());
            }

            if (tt2 != null) {
                tt2.setText(p.getMaxStu());

            }
        }

        return v;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mClassList.clear();
        if (charText.length() == 0) {
            mClassList.addAll(mClassFilteredList);
        }
        else
        {
            for (ClassModel wp : mClassFilteredList) {
                if (wp.getClassName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mClassList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
