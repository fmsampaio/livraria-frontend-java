package controllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import models.Autor;
import models.Categoria;
import models.Editora;
import models.Livro;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LivroManageController implements Initializable {

    @FXML
    ChoiceBox<Livro> livroChoiceBox;
    @FXML
    ChoiceBox<Categoria> categoriaChoiceBox;
    @FXML
    ChoiceBox<Editora> editoraChoiceBox;
    @FXML
    ListView<Autor> autoresListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Livro.fetchAll();
            Categoria.fetchAll();
            Editora.fetchAll();
            Autor.fetchAll();

            this.fillChoiceBoxes();
            this.fillAutorListView();
        }
        catch(UnirestException e) {
            e.printStackTrace();
        }
    }

    private void fillAutorListView() {
        this.autoresListView.setItems(FXCollections.observableList(Autor.getAll()));
    }

    private void fillChoiceBoxes() {
        this.livroChoiceBox.setItems(FXCollections.observableList(Livro.getAll()));
        this.categoriaChoiceBox.setItems(FXCollections.observableList(Categoria.getAll()));
        this.editoraChoiceBox.setItems(FXCollections.observableList(Editora.getAll()));
    }

}
