package com.vibhav.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    ImageView cl,sub,teach,map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cl = findViewById(R.id.ivclass);
        sub = findViewById(R.id.ivsub);
        teach = findViewById(R.id.ivteach);
        map = findViewById(R.id.ivmap);
        cl.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent intent = new Intent(MainActivity.this, ClassActivity.class);
                                        startActivity(intent);
                                    }
                                }
        );
        sub.setOnClickListener(new View.OnClickListener() {
                                  public void onClick(View v) {
                                      Intent intent = new Intent(MainActivity.this, SubjectActivity.class);
                                      startActivity(intent);
                                  }
                              }
        );
        teach.setOnClickListener(new View.OnClickListener() {
                                  public void onClick(View v) {
                                      Intent intent = new Intent(MainActivity.this, TeacherActivity.class);
                                      startActivity(intent);
                                  }
                              }
        );
        map.setOnClickListener(new View.OnClickListener() {
                                  public void onClick(View v) {
                                      Intent intent = new Intent(MainActivity.this, MapActivityList.class);
                                      startActivity(intent);
                                  }
                              }
        );
    }

}
