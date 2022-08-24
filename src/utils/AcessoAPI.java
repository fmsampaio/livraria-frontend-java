package utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import models.JSONParsed;
import org.json.JSONObject;

public class AcessoAPI {
    public static JsonNode getAll(String recurso) throws UnirestException {
        String url = "http://localhost:8000/" + recurso + "/";
        HttpResponse<JsonNode> response = Unirest.get(url).asJson();

        System.out.println(response.getStatus());
        System.out.println(response.getBody());

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
        System.out.println(response.getBody());
    }

}
