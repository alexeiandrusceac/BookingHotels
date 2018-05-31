package com.bookinghotels.app.mainActivity.Rooms;

public class Rooms {

    public int Id_Room;
    public int Id_Hotel;
    public int RoomNumber;
    public int RoomPrice;
    public String RoomType;

    public enum RoomType {
        LUX,
        STANDARD
    }

    public String enum2String(RoomType type) {
        switch (type) {
            case LUX:
                return  this.RoomType = "lux";
            case STANDARD:
                return this.RoomType = "standard";
            default:
                return "";
        }
    }

    public Rooms() {
    }

}
