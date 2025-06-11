package org.juliganga.View;

import org.json.JSONArray;
import org.json.JSONObject;
import org.juliganga.Exceptions.BadRequestException;
import org.juliganga.Exceptions.IncorrectParseMethodException;
import org.juliganga.Exceptions.NonExistantOptionException;
import org.juliganga.Model.MenuModel.UpdateMenu;
import org.juliganga.Model.Request.BackendRequest;
import org.juliganga.Model.Request.JSONConverter;

import java.io.IOException;

public class Menu {
    final static String address = "https://dolarapi.com/";

    public void run()
    {
        BackendRequest getrequest = new BackendRequest(address,"v1/dolares/oficiaal");
        try {
            String data = getrequest.searchData();
            JSONObject object = JSONConverter.makeJsonObject(data);
            System.out.println(JSONConverter.prettifyData(object));
        } catch (BadRequestException | IOException | InterruptedException | IncorrectParseMethodException e){
            System.out.println(e.getMessage());
        }

        /* POST REQUEST... FUNCIONA!!
        BackendRequest post_request = new BackendRequest(address,"product");

        JSONObject object = new JSONObject();

        object.put("name","Coconut");
        object.put("status","ENABLED");

        try {
            post_request.makeChangeViaForm(object,"POST");
        } catch (BadRequestException | IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }*/


    }
}
