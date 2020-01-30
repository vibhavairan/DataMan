package com.vibhav.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.List;

public class TeacherSubjectCustomAdapter extends BaseAdapter {
    public Context mContext;
    public TeacherModel t;
    public List<SubjectModel> mTeacherSubjectList = null;
    private SQLiteHelper sqLiteHelper;

    public TeacherSubjectCustomAdapter(Context mContext, List<SubjectModel> mList,SQLiteHelper sqLiteHelper,TeacherModel t) {
        this.mContext = mContext;
        this.mTeacherSubjectList = mList;
        this.sqLiteHelper = sqLiteHelper;
        this.t = t;
    }

    @Override
    public int getCount() {
        return mTeacherSubjectList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTeacherSubjectList.get(position);
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
            v = vi.inflate(R.layout.list_teacher_subject_item, null);
        }

        final SubjectModel p = (SubjectModel) getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.teacher_subject_name);
            CheckBox ch = (CheckBox) v.findViewById(R.id.teacher_subject_check);

            if (tt1 != null) {
                tt1.setText(p.getSubjectName());
                if(sqLiteHelper.checkTeacherSubject(p,t))
                    ch.setChecked(true);
                else
                    ch.setChecked(false);

                if(ch.isChecked())
                    p.setMap(true);
                else
                    p.setMap(false);
            }
            ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {
                        p.setMap(true);
                    }
                    else
                        p.setMap(false);
                }
            });
        }
        return v;
    }

  /*  public Filter getFilter() {
        return null;
    }*/
}
