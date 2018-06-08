package com.bookinghotels.app.mainActivity;

public enum Locality {
        CHISINAU("Chisinau"),
        COMRAT("Comrat"),
        BALTI("Balti");

        private String city;
        private Locality(String city){this.city = city;}

        @Override public String toString(){return city;}
    }

