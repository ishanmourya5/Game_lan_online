package com.example.ishan.test;

public class user {
    public String email;
    public String name;

    public user(String name, String email){
        this.name=name;
        this.email= email;
    }
    public user(){

    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }
}
