package models;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.AcessoAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Livro implements JSONParsed {
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
        this.categoria = Categoria.getById(jsonObj.getJSONObject("categoria").getInt("id"));
        this.editora = Editora.getById(jsonObj.getJSONObject("editora").getInt("id"));
        this.autores = new ArrayList<>();
        JSONArray arrayAutores = jsonObj.getJSONArray("autores");
        for (int i = 0; i < arrayAutores.length(); i++) {
            int autorId = arrayAutores.getJSONObject(i).getInt("id");
            this.getAutores().add(Autor.getById(autorId));
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

    public static void create(Livro livro) throws UnirestException {
        AcessoAPI.create("livros", livro);
    }

    public static void destroy(Livro livro) throws UnirestException {
        AcessoAPI.destroy("livros", livro.getId());
    }

    public static ArrayList<Livro> getAll() {
        return new ArrayList<>(mapLivros.values());
    }

    @Override
    public String toString() {
        return "(" + getId() + ") " + getTitulo();
    }

    @Override
    public JSONObject geraJSON() {
        JSONObject json = new JSONObject();
        json.put("titulo", this.getTitulo());
        json.put("ISBN", this.getISBN());
        json.put("quantidade", this.getQuantidade());
        json.put("preco", this.getPreco());
        json.put("categoria_id", this.getCategoria().getId());
        json.put("editora_id", this.getEditora().getId());

        ArrayList<Integer> autoresIds = new ArrayList<>();
        for(Autor autor : this.getAutores()) {
            autoresIds.add(autor.getId());
        }
        json.put("autores", autoresIds);

        System.out.println(json);
        return json;
    }

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getISBN() {
        return ISBN;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getPreco() {
        return preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Editora getEditora() {
        return editora;
    }

    public ArrayList<Autor> getAutores() {
        return autores;
    }
}
