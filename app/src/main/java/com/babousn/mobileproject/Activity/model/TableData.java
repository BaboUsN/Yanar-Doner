package com.babousn.mobileproject.Activity.model;

import java.util.Date;

public class TableData {
    private boolean isBooked;
    private long bookedTime;
    private int tableNum;

    public TableData(int tableNum,boolean isBooked, long bookedTime) {
        this.isBooked = isBooked;
        this.bookedTime = bookedTime;
        this.tableNum = tableNum;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public long getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(long bookedTime) {
        this.bookedTime = bookedTime;
    }

    public int getTableNum() {
        return tableNum;
    }

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }
    public boolean isAvailable(){
        Date date = new Date();
        long currentTime = date.getTime();
        long pastTime = this.bookedTime;
        if(currentTime-pastTime >= 1800000){
            setBooked(false);
            return true;
        }
        return false;
    }
}
