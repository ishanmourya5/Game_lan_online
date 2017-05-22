package com.example.ishan.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class game_online extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseUser fb_user;
    Button send_button;
    TextView conversation_tv;
    EditText message_et;
    String my_name;
    String peer_name;
    String conversation = "Conversation : \n";
    CheckBox c1,c2,c3,c4,c5,c6,c7,c8,c9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        send_button = (Button)findViewById(R.id.button8);
        send_button.setVisibility(View.VISIBLE);
        send_button.setOnClickListener(this);
        conversation_tv = (TextView)findViewById(R.id.textView4);
        message_et = (EditText)findViewById(R.id.editText6);

        auth = FirebaseAuth.getInstance();
        fb_user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        my_name = fb_user.getDisplayName();
        Bundle b = getIntent().getExtras();
        peer_name = (String)b.get("username");
        conversation_tv.setText(my_name + peer_name);

        c1 = (CheckBox)findViewById(R.id.cb1);
        c2 = (CheckBox)findViewById(R.id.cb2);
        c3 = (CheckBox)findViewById(R.id.cb3);
        c4 = (CheckBox)findViewById(R.id.cb4);
        c5 = (CheckBox)findViewById(R.id.cb5);
        c6 = (CheckBox)findViewById(R.id.cb6);
        c7 = (CheckBox)findViewById(R.id.cb7);
        c8 = (CheckBox)findViewById(R.id.cb8);
        c9 = (CheckBox)findViewById(R.id.cb9);
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
        c6.setOnClickListener(this);
        c7.setOnClickListener(this);
        c8.setOnClickListener(this);
        c9.setOnClickListener(this);

        DatabaseReference ref2 = database.getReference().child("conversation").child(my_name);
        ref2.removeValue();
        ref2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                post p = dataSnapshot.getValue(post.class);
                conversation += p.sender + " : ";
                conversation += p.message + "\n";
                conversation_tv.setText(conversation);
                try{
                    CheckBox cb = (CheckBox)findViewById(Integer.parseInt(p.message));
                    cb.setBackgroundResource(R.drawable.logo);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==send_button){
            post p = new post(fb_user.getDisplayName(), message_et.getText().toString());
            database.getReference().child("conversation").child(peer_name).push().setValue(p);
            message_et.setText("");
            hideKeyboard(this);
        }
        else{
            int c = v.getId();
            CheckBox cb = (CheckBox)findViewById(c);
            cb.setBackgroundResource(R.drawable.logo);
            cb.setEnabled(false);
            post p = new post(fb_user.getDisplayName(), Integer.toString(c));
            database.getReference().child("conversation").child(peer_name).push().setValue(p);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        auth = FirebaseAuth.getInstance();
        fb_user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("conversation").child(fb_user.getDisplayName());
        ref.removeValue();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
