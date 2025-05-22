package org.juliganga.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.juliganga.Exceptions.IncorrectParseMethodException;

/**
 * Una clase que se usa para crear un String desde un {@link org.json} para mostrar al frontend
 */
public class JSONConverter {
    /**
     * Retorna un String con json formateado
     * @param jsonObject Un objecto JSON
     * @return Retorna el String formateado
     */
    static public String prettifyData(JSONObject jsonObject)
    {
        return jsonObject.toString(2);
    }

    /**
     * Retorna un String con json formateado
     * @param jsonArray Un arreglo JSON
     * @return Retorna el String con todos los elementos de array formateados
     */
    static public String prettifyData(JSONArray jsonArray)
    {
        return jsonArray.toString(2);
    }


    /**
     * Crea un json Object en base a un String
     * @param content El json en String
     * @return Un {@link JSONObject} del contenido
     * @throws IncorrectParseMethodException Una excepcion si lo que se le pasa, no es un objecto
     */
    static public JSONObject makeJsonObject(String content) throws IncorrectParseMethodException
    {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(content);
        } catch (JSONException e) {
            throw new IncorrectParseMethodException("El metodo de parseo no es el adecuado!");
        }
        return jsonObject;
    }

    /**
     * Crea un json Array en base a un String
     * @param content El json en String
     * @return Un {@link JSONArray} del contenido
     * @throws IncorrectParseMethodException Una excepcion si lo que se le pasa, no es un array
     */
    static public JSONArray makeJsonArray(String content) throws IncorrectParseMethodException
    {
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(content);
        } catch (JSONException e)
        {
            throw new IncorrectParseMethodException("El metodo de parseo no es el adecuado!");
        }
        return jsonArray;
    }
}
