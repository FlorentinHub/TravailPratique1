package com.example.travailpratique1;
import android.app.Application;

public class MyApp extends Application {
    private ReservationList reservationList = new ReservationList();

    public ReservationList getReservationList() {
        return reservationList;
    }
}
