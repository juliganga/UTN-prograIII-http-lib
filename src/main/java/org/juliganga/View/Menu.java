package org.juliganga.View;

import org.json.JSONArray;
import org.json.JSONObject;
import org.juliganga.Exceptions.BadRequestException;
import org.juliganga.Exceptions.IncorrectParseMethodException;
import org.juliganga.Exceptions.NonExistantOptionException;
import org.juliganga.Model.BackendRequest;
import org.juliganga.Model.JSONConverter;
import org.juliganga.Model.UpdateMenu;

import java.io.IOException;

public class Menu {
    final static String address = "http://127.0.0.1:8080/";

    public void run()
    {
        /*
        BackendRequest getrequest = new BackendRequest(address,"supplier/page1/1");
        try {
            String data = getrequest.searchData();
            JSONArray array = JSONConverter.makeJsonArray(data);
            System.out.println(array);
            JSONObject object = array.getJSONObject(0);

            UpdateMenu updateMenu = new UpdateMenu(object);

            System.out.println(updateMenu.showOptions());


            System.out.println(updateMenu.getJsonObject());

        } catch (BadRequestException | IOException | InterruptedException | IncorrectParseMethodException | NonExistantOptionException e) {
            System.out.println(e.getMessage());
        }*/

        /* POST REQUEST... FUNCIONA!!
        BackendRequest post_request = new BackendRequest(address,"product");

        JSONObject object = new JSONObject();

        object.put("name","Coconut");
        object.put("status","ENABLED");

        try {
            post_request.makeChangeViaForm(object,"POST");
        } catch (BadRequestException | IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        */


    }
}
