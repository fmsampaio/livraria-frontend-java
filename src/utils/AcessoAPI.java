package utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class AcessoAPI {
    public static JsonNode getAll(String recurso) throws UnirestException {
        String url = "http://localhost:8000/" + recurso + "/";
        HttpResponse<JsonNode> response = Unirest.get(url).asJson();

        System.out.println(response.getStatus());
        System.out.println(response.getBody());

        return response.getBody();
    }

}
