package controllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Autor;
import models.Livro;

import javafx.scene.control.TableColumn;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable  {

    @FXML
    TableView<Livro> dataTableView;

    @FXML
    TableColumn<Livro, Integer> idTableColumn;

    @FXML
    TableColumn<Livro, String> tituloTableColumn;

    @FXML
    TableColumn<Livro, Double> precoTableColumn;

    @FXML
    TableColumn<Livro, Integer> quantidadeTableColumn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Autor.fetchAll();
            Livro.fetchAll();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAtualizarLivros(ActionEvent e) {
        ArrayList<Livro> livros = Livro.getAll();
        System.out.println(livros);

        this.idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.tituloTableColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        this.precoTableColumn.setCellValueFactory(new PropertyValueFactory<>("preco"));
        this.quantidadeTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        this.dataTableView.setItems(FXCollections.observableList(livros));

        this.dataTableView.refresh();

    }



}
