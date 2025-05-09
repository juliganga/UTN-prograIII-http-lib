package org.example.Modelo;

import com.google.gson.*;
import org.example.Exceptions.BadRequestException;
import org.example.Exceptions.IncorrectRequestType;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;


/**
 * Una clase que se usa para lanzar peticiones HTTP hacia el backend del proyecto
 * <p>Se puede utilizar de las siguientes maneras por ejemplo:</p>
 * <ul>
 *     <li>Usar un endpoint de GET con todos sus datos, o de un solo ID</li>
 *     <li>Usar un endpoint de DELETE que tiene un parametro de ID</li>
 *     <li>Usar metodos PUT O POST para agregar/modificar datos de un objeto usando {@link com.google.gson.JsonObject}</li>
 * </ul>
 * @see #makeChangeByParam(String)
 * @see #makeChangeViaForm(JsonObject, String)
 */
public class BackendRequest {
    private String url;
    final private String user_agent = "Mozilla/5.0";


    /**
     * Genera un objeto que puede hacer un pedido a un servidor.
     * @param url El dominio que el servidor está hosteado, por ejemplo:
     *  http://127.0.0.1:8080/
     *
     * @param endpoint El endpoint del dominio, por ejemplo:
     * /nombres
     *
     */
    public BackendRequest(String url,String endpoint) {
        this.url = url + endpoint;
    }


    /**
     * Crea un objeto {@link java.net.HttpURLConnection HttpURLConnection} para comenzar a realizar una transacción de cualquier tipo
     * @param type Tipo de metodo HTTP <ul>
     *             <li>POST</li>
     *             <li>GET</li>
     *             <li>PUT</li>
     *             <li>DELETE</li>
     * </ul>
     * @return {@link java.net.HttpURLConnection HttpURLConnection} Un pedido HTTP
     * @throws BadRequestException
     */
    private HttpURLConnection startRequest(String type) throws BadRequestException
    {
        HttpURLConnection con = null;
        try {
            URL obj = URI.create(url).toURL();
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(type);
            con.setRequestProperty("User-Agent", user_agent);
            con.setRequestProperty("Content-Type","application/json");
        } catch (IOException e)
        {
            throw new BadRequestException("Hubo un error conectandose al servidor:\n" + e.getMessage());
        }


        return con;
    }


    /**
     * Un metodo que se usa para buscar y luego mostrar datos recividos del servidor.
     * @param requestType El tipo de dato que se espera recivir del servidor, aplica formato
     * @return La información recivida
     * @throws BadRequestException En caso de que el servidor lanze un error HTTP, se lanzara una excepcion
     * @throws IOException
     */
    public String searchData(GetRequestType requestType) throws BadRequestException, IOException, IncorrectRequestType {
        HttpURLConnection con = startRequest("GET");
        checkHTTPCode(con);


        StringBuilder response = new StringBuilder();
        BufferedReader data_recieved = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;

        while ((inputLine = data_recieved.readLine()) != null) {
            response.append(inputLine);
        }
        data_recieved.close();


        String info = "";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            if(requestType == GetRequestType.OBJECT)
            {
                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject(); // solo funciona con un solo objeto
                info = gson.toJson(jsonObject);
            }

            if(requestType == GetRequestType.ARRAY)
            {
                JsonArray jsonArray = JsonParser.parseString(response.toString()).getAsJsonArray();
                info = gson.toJson(jsonArray);
            }
        } catch (IllegalStateException e)
        {
            throw new IncorrectRequestType("ERROR: El tipo de datos recividos no es el tipo esperado");
        }

        return info;
    }


    /**
     * <p>Este metodo se usa para cualquier tipo de request que no sea GET y necesite de un tipo de formulario.</p>
     * <p>Apropiado para UPDATE</p>
     * @param requestBody Un objeto JSON que contiene el cuerpo del request
     * @param request_method Un metodo HTTP, PUT, DELETE, UPDATE
     * @throws BadRequestException En caso de que el servidor lanze un error HTTP, se lanzara una excepcion
     * @throws IOException
     */
    public void makeChangeViaForm(JsonObject requestBody, String request_method) throws BadRequestException, IOException
    {
        HttpURLConnection con = startRequest(request_method);
        con.setDoOutput(true);
        con.setDoInput(true);


        OutputStream os = con.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
        writer.write(requestBody.toString());
        writer.flush();
        writer.close();
        os.close();
        con.connect();

        checkHTTPCode(con);
    }


    /**
     * <p>Esto se usa cuando se necesita hacer una llamada al servidor para realizar cambios a datos segun parametros</p>
     * <p>Por ejemplo, un DELETE segun id</p>
     * <blockquote><pre>
     *     http://localhost:8080/nombres/1
     * </pre></blockquote>
     * <p>Un UPDATE con uno o mas parametros.</p>
     * @param request_method Un metodo HTTP, PUT, DELETE, UPDATE
     * @throws BadRequestException En caso de que el servidor lanze un error HTTP, se lanzara una excepcion
     * @throws IOException
     */
    public void makeChangeByParam(String request_method) throws BadRequestException, IOException
    {
        HttpURLConnection con = startRequest(request_method);
        con.setDoOutput(true);
        con.setDoInput(true);
        con.connect();

        checkHTTPCode(con);
    }

    /**
     * Revisa el estado HTTP que el servidor lanza
     * @param connection la conexion al servidor
     * @throws IOException
     * @throws BadRequestException
     */
    public void checkHTTPCode(HttpURLConnection connection) throws IOException, BadRequestException
    {
        int responseCode = connection.getResponseCode();

        if(responseCode != HttpURLConnection.HTTP_OK)
        {
            throw new BadRequestException("Hubo un error de conexion al servicio.\nHTTP: " + responseCode);
        }
    }
}
