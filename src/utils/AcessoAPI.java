package utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import models.JSONParsed;
import org.json.JSONObject;

public class AcessoAPI {
    public static JsonNode getAll(String recurso) throws UnirestException {
        String url = "http://localhost:8000/" + recurso + "/";
        HttpResponse<JsonNode> response = Unirest.get(url).asJson();

        System.out.println(response.getStatus());
        return response.getBody();
    }

    public static void create(String recurso, JSONParsed dados) throws UnirestException {
        JSONObject json = dados.geraJSON();

        String url = "http://localhost:8000/" + recurso + "/";
        HttpResponse<JsonNode> response = Unirest.post(url)
                .header("Content-Type", "application/json")
                .body(json.toString())
                .asJson();

        System.out.println(response.getStatus());
    }

    public static void destroy(String recurso, int id) throws UnirestException {
        String url = "http://localhost:8000/" + recurso + "/" + id;

        HttpResponse<String> response = Unirest.delete(url).asString();

        System.out.println(response.getStatus());
    }

    public static void update(String recurso, int id, JSONParsed dados) throws UnirestException {
        JSONObject json = dados.geraJSON();

        String url = "http://localhost:8000/" + recurso + "/" + id;
        HttpResponse<JsonNode> response = Unirest.put(url)
                .header("Content-Type", "application/json")
                .body(json.toString())
                .asJson();

        System.out.println(response.getStatus());
    }

}
