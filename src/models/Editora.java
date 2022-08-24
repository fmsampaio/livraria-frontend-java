package models;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AcessoAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Editora {
    private static Map<Integer, Editora> mapEditoras = new HashMap();

    private int id;
    private String nome;
    private String site;

    public Editora(int id, String nome, String site) {
        this.id = id;
        this.nome = nome;
        this.site = site;
    }

    public Editora(JSONObject jsonObj) {
        this.id = jsonObj.getInt("id");
        this.nome = jsonObj.getString("nome");
        this.site = jsonObj.getString("site");
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", nome='" + nome + '\'';
    }

    public static void fetchAll() throws UnirestException {
        JSONArray array = AcessoAPI.getAll("editoras").getArray();
        mapEditoras.clear();
        for (int i = 0; i < array.length(); i++) {
            JSONObject atorJson = array.getJSONObject(i);
            mapEditoras.put(atorJson.getInt("id"), new Editora(atorJson));
        }
    }

    public static ArrayList<Editora> getAll() {
        return new ArrayList<>(mapEditoras.values());

    }
}
