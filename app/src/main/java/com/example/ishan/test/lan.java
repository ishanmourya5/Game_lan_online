package com.example.ishan.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class lan extends AppCompatActivity implements View.OnClickListener{

    Button create_server_button;
    Button connect_server_button;
    Button connect_button;
    EditText server_ip_et;
    EditText server_port_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lan);

        create_server_button = (Button)findViewById(R.id.button);
        connect_server_button = (Button)findViewById(R.id.button2);
        connect_button = (Button)findViewById(R.id.button3);

        server_ip_et = (EditText)findViewById(R.id.editText);
        server_port_et = (EditText)findViewById(R.id.editText2);

        create_server_button.setOnClickListener(this);
        connect_server_button.setOnClickListener(this);
        connect_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==create_server_button){
            Intent i = new Intent(this, game_lan.class);
            i.putExtra("MODE","server");
            startActivity(i);
        }
        else if(v==connect_server_button){
            connect_button.setVisibility(View.VISIBLE);
            server_ip_et.setVisibility(View.VISIBLE);
            server_port_et.setVisibility(View.VISIBLE);
        }
        else if(v==connect_button){
            Intent i = new Intent(this, game_lan.class);
            i.putExtra("MODE","client");
            i.putExtra("IP",server_ip_et.getText().toString());
            i.putExtra("port",server_port_et.getText().toString());
            startActivity(i);
        }
    }
}