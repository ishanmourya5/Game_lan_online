package com.example.ishan.test;

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
    CheckBox send_cb;
    TextView server_ip_tv;
    TextView server_port_tv;
    TextView conversation_tv;
    EditText server_ip_et;
    EditText server_port_et;
    EditText message_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lan);
        send_cb= (CheckBox) findViewById(R.id.checkBox);

        create_server_button = (Button)findViewById(R.id.button);
        connect_server_button = (Button)findViewById(R.id.button2);
        connect_button = (Button)findViewById(R.id.button3);

        server_ip_tv = (TextView)findViewById(R.id.textView);
        server_port_tv = (TextView)findViewById(R.id.textView2);
        conversation_tv = (TextView)findViewById(R.id.textView3);

        server_ip_et = (EditText)findViewById(R.id.editText);
        server_port_et = (EditText)findViewById(R.id.editText2);
        message_et = (EditText)findViewById(R.id.editText3);

        create_server_button.setOnClickListener(this);
        connect_server_button.setOnClickListener(this);
        connect_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==create_server_button){
            Server server = new Server(this);
            server_ip_tv.setText("IP : " + server.getIP());
            server_port_tv.setText("PORT : " + server.getPort());
            server_ip_tv.setVisibility(View.VISIBLE);
            server_port_tv.setVisibility(View.VISIBLE);
            send_cb.setVisibility(View.VISIBLE);
            message_et.setVisibility(View.VISIBLE);
        }
        else if(v==connect_server_button){
            connect_button.setVisibility(View.VISIBLE);
            server_ip_et.setVisibility(View.VISIBLE);
            server_port_et.setVisibility(View.VISIBLE);
            server_ip_tv.setVisibility(View.INVISIBLE);
            server_port_tv.setVisibility(View.INVISIBLE);
        }
        else if(v==connect_button){
            Client client = new Client(this);
            connect_server_button.setEnabled(false);
            send_cb.setVisibility(View.VISIBLE);
            message_et.setVisibility(View.VISIBLE);
        }
    }
}