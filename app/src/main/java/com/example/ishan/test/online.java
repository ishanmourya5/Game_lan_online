package com.example.ishan.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.hamza.slidingsquaresloaderview.SlidingSquareLoaderView;

public class online extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseUser fb_user;
    EditText username_et;
    EditText password_et;
    EditText name_et;
    Button signup_button;
    Button signin_button;
    SlidingSquareLoaderView sq;
    String email;
    String password;
    String name;
    TextView loading_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online);
        username_et = (EditText)findViewById(R.id.editText);
        password_et = (EditText)findViewById(R.id.editText2);
        name_et = (EditText)findViewById(R.id.editText5);

        loading_tv = (TextView)findViewById(R.id.textView6);
        signup_button = (Button)findViewById(R.id.button4);
        signin_button = (Button)findViewById(R.id.button5);
        sq = (SlidingSquareLoaderView)findViewById(R.id.progressBar);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        signup_button.setOnClickListener(this);
        signin_button.setOnClickListener(this);

    }
    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        email = username_et.getText().toString();
        password = password_et.getText().toString();
        name = name_et.getText().toString();
        if(v==signup_button){
            hideKeyboard(this);
            sq.setVisibility(View.VISIBLE);
            loading_tv.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        fb_user = auth.getCurrentUser();
                        UserProfileChangeRequest req = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        fb_user.updateProfile(req);
                        user u = new user(name, email);
                        database.getReference().child("user").push().setValue(u);
                        Intent i = new Intent(getApplicationContext(),profile.class);
                        startActivity(i);
                        finish();
                    }
                }
            });
        }
        else if(v==signin_button){
            hideKeyboard(this);
            sq.setVisibility(View.VISIBLE);
            loading_tv.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent i = new Intent(getApplicationContext(),profile.class);
                        startActivity(i);
                        finish();
                    }
                }
            });
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
