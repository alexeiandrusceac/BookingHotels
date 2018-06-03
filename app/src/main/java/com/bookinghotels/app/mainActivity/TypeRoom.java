package com.bookinghotels.app.mainActivity;

public enum TypeRoom {
    LUX("lux"),
    STANDARD("standard");

    private String type;
    private TypeRoom(String type){this.type = type;}

    @Override public String toString(){return type;}
}
