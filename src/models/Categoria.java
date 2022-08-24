package models;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AcessoAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Categoria {
    private static Map<Integer, Categoria> mapCategorias = new HashMap();

    private int id;
    private String descricao;

    public Categoria(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Categoria(JSONObject jsonObj) {
        this.id = jsonObj.getInt("id");
        this.descricao = jsonObj.getString("descricao");
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", descricao='" + descricao + '\'';
    }

    public static void fetchAll() throws UnirestException {
        JSONArray array = AcessoAPI.getAll("categorias").getArray();
        mapCategorias.clear();
        for (int i = 0; i < array.length(); i++) {
            JSONObject atorJson = array.getJSONObject(i);
            mapCategorias.put(atorJson.getInt("id"), new Categoria(atorJson));
        }
    }

    public static ArrayList<Categoria> getAll() {
        return new ArrayList<>(mapCategorias.values());

    }
}
