package org.example;

import org.example.Exceptions.BadRequestException;
import org.example.Exceptions.IncorrectRequestType;
import org.example.Model.BackendRequest;
import org.example.Model.GetRequestType;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //BackendRequest pedido = new BackendRequest("https://postman-echo.com","/get");
        BackendRequest pedido2 = new BackendRequest("https://postman-echo.com","/get");
        try {
            //System.out.println(pedido2.searchData(GetRequestType.ARRAY));
            //JsonObject object = new JsonObject();
            //object.addProperty("client_id","1");
            System.out.println(pedido2.prettifyData(pedido2.searchData(),GetRequestType.OBJECT));
            //pedido2.makeChangeViaForm(object,"POST");
        } catch (BadRequestException | IOException | InterruptedException | IncorrectRequestType e)
        {
            System.out.println(e.getMessage());
        }
    }
}