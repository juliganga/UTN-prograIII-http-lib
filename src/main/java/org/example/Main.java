package org.example;

import com.google.gson.JsonObject;
import org.example.Exceptions.BadRequestException;
import org.example.Modelo.BackendRequest;
import org.example.Modelo.GetRequestType;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        //BackendRequest pedido = new BackendRequest("https://postman-echo.com","/get");
        BackendRequest pedido2 = new BackendRequest("http://127.0.0.1:8080","/nombre/49");
        try {
            //System.out.println(pedido2.searchData(GetRequestType.ARRAY));
            pedido2.makeChangeByParam("DELETE");
        } catch (BadRequestException | IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}