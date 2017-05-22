package com.example.ishan.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class game_lan extends AppCompatActivity{

    TextView address_tv, conversation_tv;
    CheckBox send_cb;
    EditText message_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        address_tv = (TextView)findViewById(R.id.textView3);
        conversation_tv = (TextView)findViewById(R.id.textView4);
        send_cb = (CheckBox)findViewById(R.id.checkBox2);
        send_cb.setVisibility(View.VISIBLE);
        message_et = (EditText)findViewById(R.id.editText6);

        Bundle b = getIntent().getExtras();
        String IP = (String) b.get("IP");
        String port = (String)b.get("port");
        String mode = (String)b.get("MODE");
        if(mode.equals("server")){
            Server server = new Server(this);
            String Address = server.getIP() +" : " + server.getPort();
            address_tv.setText(Address);
        }
        else if(mode.equals("client")){
            Client client = new Client(this, IP, port);

        }
    }
}
