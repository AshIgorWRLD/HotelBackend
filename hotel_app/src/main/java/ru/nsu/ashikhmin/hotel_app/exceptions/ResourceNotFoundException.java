package ru.nsu.ashikhmin.hotel_app.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String msg){
        super(msg);
    }
}

