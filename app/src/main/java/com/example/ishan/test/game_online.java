package com.example.ishan.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        send_button = (Button)findViewById(R.id.button8);
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

        DatabaseReference ref2 = database.getReference().child("conversation").child(my_name);
        ref2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                post p = dataSnapshot.getValue(post.class);
                conversation += p.sender + " : ";
                conversation += p.message + "\n";
                conversation_tv.setText(conversation);
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
