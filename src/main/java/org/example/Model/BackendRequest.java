package org.example.Model;

import com.google.gson.*;
import org.example.Exceptions.BadRequestException;
import org.example.Exceptions.IncorrectRequestType;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


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
     * Un metodo que se usa para buscar datos en el servidor.
     * @return La información recibida en formato JSON
     * @throws BadRequestException En caso de que el servidor lanze un error HTTP, se lanzara una excepcion
     * @throws IOException
     * @throws InterruptedException
     */
    public String searchData() throws BadRequestException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", user_agent)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<InputStream> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofInputStream());

        checkHTTPCode(response);

        BufferedReader responseBodyStream = new BufferedReader(new InputStreamReader(response.body()));
        StringBuilder responseContent = new StringBuilder();
        String inputLine;

        while ((inputLine = responseBodyStream.readLine()) != null) {
            responseContent.append(inputLine);
        }
        responseBodyStream.close();

        return responseContent.toString();
    }

    /**
     * Un metodo que se usa para mostrar datos recibidos del servidor de manera legible.
     *
     * @param requestType El tipo de dato que se espera recibir del servidor, aplica formato
     * @return La información recibida con el formato dado
     * @throws IncorrectRequestType En caso de que el formato de datos recibidos no sea el especificado por parametro
     */
    public String prettifyData(String responseContent, GetRequestType requestType) throws IncorrectRequestType {
        String info = "";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            if (requestType == GetRequestType.OBJECT) {
                JsonObject jsonObject = JsonParser.parseString(responseContent).getAsJsonObject(); // solo funciona con un solo objeto
                info = gson.toJson(jsonObject);
            }

            if (requestType == GetRequestType.ARRAY) {
                JsonArray jsonArray = JsonParser.parseString(responseContent).getAsJsonArray();
                info = gson.toJson(jsonArray);
            }
        } catch (IllegalStateException e) {
            throw new IncorrectRequestType("ERROR: El tipo de datos recibidos no es el tipo esperado");
        }

        return info;
    }

    /**
     * <p>Este metodo se usa para cualquier tipo de request que no sea GET y necesite de un tipo de formulario.</p>
     * <p>Apropiado para UPDATE</p>
     * @param requestBody Un objeto JSON que contiene el cuerpo del request
     * @param requestMethod Un metodo HTTP, PUT, DELETE, UPDATE
     * @throws BadRequestException En caso de que el servidor lanze un error HTTP, se lanzara una excepcion
     * @throws IOException
     * @throws InterruptedException
     */
    public void makeChangeViaForm(JsonObject requestBody, String requestMethod) throws BadRequestException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method(requestMethod, HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .header("User-Agent", user_agent)
                .header("Content-Type","application/json; charset=utf-8")
                .build();

        HttpResponse<InputStream> response = HttpClient.newHttpClient().send(request,HttpResponse.BodyHandlers.ofInputStream());

        checkHTTPCode(response);
    }


    /**
     * <p>Esto se usa cuando se necesita hacer una llamada al servidor para realizar cambios a datos segun parametros</p>
     * <p>Por ejemplo, un DELETE segun id</p>
     * <blockquote><pre>
     *     http://localhost:8080/nombres/1
     * </pre></blockquote>
     * <p>Un UPDATE con uno o mas parametros.</p>
     * @param requestMethod Un metodo HTTP, PUT, DELETE, UPDATE
     * @throws BadRequestException En caso de que el servidor lanze un error HTTP, se lanzara una excepcion
     * @throws IOException
     * @throws InterruptedException
     */
    public void makeChangeByParam(String requestMethod) throws BadRequestException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method(requestMethod, HttpRequest.BodyPublishers.noBody())
                .header("User-Agent", user_agent)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<Void> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.discarding());

        checkHTTPCode(response);
    }

    /**
     * Revisa el estado HTTP que el servidor lanza al enviar una request
     * @param response La respuesta del servidor al request enviado
     * @throws BadRequestException Si hay algun error de conexion
     */
    public void checkHTTPCode(HttpResponse response) throws BadRequestException {
        int responseCode = response.statusCode();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new BadRequestException("Hubo un error de conexion al servicio.\nHTTP: " + responseCode);
        }
    }
}
