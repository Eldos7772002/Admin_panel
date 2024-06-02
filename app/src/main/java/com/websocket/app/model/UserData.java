package com.websocket.app.model;

import lombok.Data;

@Data
public class UserData {
    private String expoid;
    private String firebaseToken;
    private double latitude;
    private double longitude;

}
