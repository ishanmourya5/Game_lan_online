package com.example.ishan.test;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button lan_button;
    Button online_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lan_button = (Button)findViewById(R.id.button2);
        online_button = (Button)findViewById(R.id.button3);
        lan_button.setOnClickListener(this);
        online_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==lan_button){
            Intent i = new Intent(this, lan.class);
            startActivity(i);
        }
        else if(v==online_button){
            Intent i = new Intent(this, online.class);
            startActivity(i);
        }
    }
}
