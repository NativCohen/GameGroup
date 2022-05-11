package com.example.gamegroupproject;

public class Member {
String name,city,memberId;

public Member(){

}

public Member(String name,String city,String memberId){
this.name=name;
this.city=city;
this.memberId=memberId;
}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;   
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
