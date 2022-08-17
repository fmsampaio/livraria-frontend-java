package models;

import java.util.ArrayList;

public class Livro {
    private int id;
    private String titulo;
    private String ISBN;
    private int quantidade;
    private double preco;
    private Categoria categoria;
    private Editora editora;
    private ArrayList<Autor> autores;

}
