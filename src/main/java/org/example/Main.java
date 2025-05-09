package org.example;

import com.google.gson.JsonObject;
import org.example.Exceptions.BadRequestException;
import org.example.Exceptions.IncorrectRequestType;
import org.example.Modelo.BackendRequest;
import org.example.Modelo.GetRequestType;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        //BackendRequest pedido = new BackendRequest("https://postman-echo.com","/get");
        BackendRequest pedido2 = new BackendRequest("https://postman-echo.com","/GET");
        try {
            //System.out.println(pedido2.searchData(GetRequestType.ARRAY));
            System.out.println(pedido2.searchData(GetRequestType.ARRAY));
        } catch (BadRequestException | IOException | IncorrectRequestType e)
        {
            System.out.println(e.getMessage());
        }
    }
}