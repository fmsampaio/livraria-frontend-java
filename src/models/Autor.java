package models;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AcessoAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Autor {
    private static Map<Integer, Autor> mapAutores = new HashMap();

    private int id;
    private String nome;

    public Autor(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Autor(JSONObject jsonObj) {
        this.id = jsonObj.getInt("id");
        this.nome = jsonObj.getString("nome");
    }

    public static void fetchAll() throws UnirestException {
        JSONArray array = AcessoAPI.getAll("autores").getArray();
        mapAutores.clear();
        for (int i = 0; i < array.length(); i++) {
            JSONObject atorJson = array.getJSONObject(i);
            mapAutores.put(atorJson.getInt("id"), new Autor(atorJson));
        }
    }

    public static ArrayList<Autor> getAll() {
        return new ArrayList<>(mapAutores.values());
    }

    public static Autor getById(int id) {
        return mapAutores.get(id);
    }

    @Override
    public String toString() {
        return "id=" + getId() +
                ", nome='" + nome + '\'';
    }

    public int getId() {
        return id;
    }
}
