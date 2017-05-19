package com.example.ishan.test;

import android.app.Activity;
import android.content.Intent;
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

public class profile extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseUser fb_user;
    TextView username_tv;
    TextView user_tv;
    String users = "Users: \n";
    Button connect_button;
    EditText username_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        username_tv = (TextView)findViewById(R.id.textView2);
        connect_button =(Button)findViewById(R.id.button6);
        username_et = (EditText)findViewById(R.id.editText4);
        user_tv = (TextView)findViewById(R.id.textView5);

        auth = FirebaseAuth.getInstance();
        fb_user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        username_tv.setText("SignedIn as : " + fb_user.getDisplayName());
        connect_button.setOnClickListener(this);
        
        DatabaseReference ref = database.getReference().child("user");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                user u = dataSnapshot.getValue(user.class);
                users += u.name + "\n";
                user_tv.setText(users);
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
        if(v==connect_button) {
            final String username = username_et.getText().toString();
            post p = new post(fb_user.getDisplayName(),"connected");
            database.getReference().child("conversation").child(username).push().setValue(p);
            username_et.setText("");
            hideKeyboard(this);
            Intent i = new Intent(this, game_online.class);
            i.putExtra("username",username);
            startActivity(i);
        }
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
