package com.example.gamegroupproject;

public class Event {
    int maxMembers,currentMembers;
    String date,eventName,city,id,userId;
    boolean isHappening;

    public Event(){

    }

    public Event(String eventName,int maxMembers,String date,int currentMembers, String city,String id,String userId){
        this.eventName=eventName;
        this.currentMembers=currentMembers;
        this.maxMembers=maxMembers;
        this.date=date;
        this.city=city;
        this.id=id;
        this.userId=userId;
        changeHappening();
    }
    public boolean maxOrNot(){
        if (currentMembers==maxMembers){
            return true;
        }
        return false;
    }

    public void changeHappening(){
        isHappening=(double)currentMembers/maxMembers>=0.5;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public int getCurrentMembers() {
        return currentMembers;
    }

    public void setCurrentMembers(int currentMembers) {
        this.currentMembers = currentMembers;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public boolean isHappening() {
        return isHappening;
    }

    public void setHappening(boolean happening) {
        isHappening = happening;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
