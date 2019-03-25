package com.example.ravisundar.ola;

public class user_signup {
   String name,number,id;

    public  user_signup(String id,String name,String number) {
this.name=name;
this.number=number;
this.id=id;
    }
    public user_signup() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
