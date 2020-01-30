package com.vibhav.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class MapCustomAdapter extends BaseAdapter {


    public Context mContext;
    public List<MapModel> mMapList = null;
    private SQLiteHelper sqLiteHelper;
    boolean z;

    public MapCustomAdapter(Context mContext, List<MapModel> mList, SQLiteHelper sqLiteHelper, boolean z) {
        this.mContext = mContext;
        this.mMapList = mList;
        this.sqLiteHelper = sqLiteHelper;
        this.z = z;
    }


    @Override
    public int getCount() {
        return mMapList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMapList.get(position);
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
                v = vi.inflate(R.layout.list_map_full_item,null);
            else
                v = vi.inflate(R.layout.list_map_item, null);
        }

        MapModel p = (MapModel) getItem(position);

        if (p != null) {
            TextView tt1,tt2,tt3,tt4;
            if(z)
            {
                tt1 = (TextView) v.findViewById(R.id.class_name_main);
                tt2 = (TextView) v.findViewById(R.id.subject_name_main);
                tt3 = (TextView) v.findViewById(R.id.teacher_name_main);
                String tt1s,tt2s,tt3s,tt4s;
                if (tt1 != null) {
                    tt1s = "Class: " + sqLiteHelper.getClassName(p);
                    tt1.setText(tt1s);
                }
                if (tt2 != null) {
                    tt2s = "Subject: "+sqLiteHelper.getSubjectName(p);
                    tt2.setText(tt2s);
                }
                if (tt3 != null) {
                    tt3s = "Teacher: "+sqLiteHelper.getTeacherName(p);
                    tt3.setText(tt3s);
                }
            }
            else
            {
                String tt1s,tt2s;
                tt1 = (TextView) v.findViewById(R.id.class_name);
                tt2 = (TextView) v.findViewById(R.id.subject_name);
                if (tt1 != null) {
                    tt1s = "Class: "+sqLiteHelper.getClassName(p);
                    tt1.setText(tt1s);
                }
                if (tt2 != null) {
                    tt2s = "Subject: "+sqLiteHelper.getSubjectName(p);
                    tt2.setText(tt2s);
                }
            }
        }
        return v;
    }

  /*  public Filter getFilter() {
        return null;
    }*/
}
