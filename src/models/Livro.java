package models;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AcessoAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Livro {
    private static Map<Integer, Livro> mapLivros = new HashMap<>();

    private Integer id;
    private String titulo;
    private String ISBN;
    private Integer quantidade;
    private Double preco;
    private Categoria categoria;
    private Editora editora;
    private ArrayList<Autor> autores;

    public Livro(JSONObject jsonObj) {
        this.id = jsonObj.getInt("id");
        this.titulo = jsonObj.getString("titulo");
        this.ISBN = jsonObj.getString("ISBN");
        this.quantidade = jsonObj.getInt("quantidade");
        this.preco = jsonObj.getDouble("preco");
        //this.categoria = Categoria.mapCategorias.get((jsonObj.getJSONObject("categoria").getInt("id")));
        //this.editora = editora;
        this.autores = new ArrayList<>();
        JSONArray arrayAutores = jsonObj.getJSONArray("autores");
        for (int i = 0; i < arrayAutores.length(); i++) {
            int autorId = arrayAutores.getJSONObject(i).getInt("id");
            this.autores.add(Autor.getAutor(autorId));
        }
    }

    public Livro(int id, String titulo, String ISBN, int quantidade, double preco, Categoria categoria, Editora editora, ArrayList<Autor> autores) {
        this.id = id;
        this.titulo = titulo;
        this.ISBN = ISBN;
        this.quantidade = quantidade;
        this.preco = preco;
        this.categoria = categoria;
        this.editora = editora;
        this.autores = autores;
    }

    public static void fetchAll() throws UnirestException {
        JSONArray array = AcessoAPI.getAll("livros").getArray();
        mapLivros.clear();
        for (int i = 0; i < array.length(); i++) {
            JSONObject atorJson = array.getJSONObject(i);
            mapLivros.put(atorJson.getInt("id"), new Livro(atorJson));
        }
    }

    public static ArrayList<Livro> getAll() {
        return new ArrayList<>(mapLivros.values());
    }

    @Override
    public String toString() {
        return "(" + id + ") " + titulo;
    }
}
