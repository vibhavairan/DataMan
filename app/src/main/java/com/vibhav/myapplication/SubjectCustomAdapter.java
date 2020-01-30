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

public class SubjectCustomAdapter extends BaseAdapter {
    public Context mContext;
    public List<SubjectModel> mSubjectList = null;
    public List<SubjectModel> mSubjectFilteredList = null;

    public SubjectCustomAdapter(Context mContext, List<SubjectModel> mList) {
        this.mContext = mContext;
        this.mSubjectList = mList;
        this.mSubjectFilteredList = new ArrayList<>();
        this.mSubjectFilteredList.addAll(mSubjectList);
    }

    @Override
    public int getCount() { return mSubjectList.size(); }

    @Override
    public Object getItem(int position) {
        return mSubjectList.get(position);
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
            v = vi.inflate(R.layout.list_subject_item, null);
        }

       SubjectModel p = (SubjectModel) getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.sub_name);
            TextView tt2 = (TextView) v.findViewById(R.id.sub_id);

            if (tt1 != null) {
                tt1.setText(p.getSubjectName());
            }

            if (tt2 != null) {
                tt2.setText(p.getsubID());

            }
        }
        return v;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mSubjectList.clear();
        if (charText.length() == 0) {
            mSubjectList.addAll(mSubjectFilteredList);
        }
        else
        {
            for (SubjectModel wp : mSubjectFilteredList) {
                if (wp.getSubjectName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mSubjectList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
