package org.example.Modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.Exceptions.BadRequestException;
import org.example.Exceptions.IncorrectRequestType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BackendRequestTest {

    @Test
    void searchDataFailure() {
        BackendRequest testReq = new BackendRequest("https://httpstat.us","/404");
        assertThrows(BadRequestException.class, testReq::searchData);
    }

    @Test
    void searchDataFound() {
        BackendRequest testReq = new BackendRequest("https://httpstat.us","/200");
        assertDoesNotThrow(testReq::searchData);
    }

    @Test
    void invalidUrl() {
        BackendRequest testReq = new BackendRequest("https://343!4t.sdsd","/");
        assertThrows(IllegalArgumentException.class,testReq::searchData);
    }

    @Test
    void prettifyDataObjectWhenExpectingArray() {
        String objectExample = "{'property':'value'}";
        BackendRequest emptyTestReq = new BackendRequest("","");

        assertThrows(IncorrectRequestType.class,() -> {
            emptyTestReq.prettifyData(objectExample,GetRequestType.ARRAY);
        });
    }

    @Test
    void prettifyDataArrayWhenExpectingObject() {
        String objectExample = "[{'property':'value'},{'property':'value'}]";
        BackendRequest emptyTestReq = new BackendRequest("","");

        assertThrows(IncorrectRequestType.class,() -> {
            emptyTestReq.prettifyData(objectExample,GetRequestType.OBJECT);
        });
    }

    @Test
    void prettifyDataEmptyObject() {
        String objectExample = "";
        BackendRequest emptyTestReq = new BackendRequest("","");

        assertThrows(IncorrectRequestType.class,() -> {
            emptyTestReq.prettifyData(objectExample,GetRequestType.OBJECT);
        });
    }
}