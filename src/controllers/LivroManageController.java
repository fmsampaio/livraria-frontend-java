package controllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import models.Autor;
import models.Categoria;
import models.Editora;
import models.Livro;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
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

    @FXML
    TextField tituloTextField, isbnTextField, precoTextField, quantidadeTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.autoresListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            this.refreshData();
        }
        catch(UnirestException e) {
            e.printStackTrace();
        }

        this.handleLivroChoiceBoxEvent();

    }

    private void handleLivroChoiceBoxEvent() {
        this.livroChoiceBox.valueProperty().addListener(new ChangeListener<Livro>() {
            @Override
            public void changed(ObservableValue<? extends Livro> observable, Livro oldValue, Livro newValue) {
                System.out.println(newValue.getCategoria());

                tituloTextField.setText(newValue.getTitulo());
                isbnTextField.setText(newValue.getISBN());
                precoTextField.setText(newValue.getPreco().toString());
                quantidadeTextField.setText(newValue.getQuantidade().toString());

                categoriaChoiceBox.getSelectionModel().select(newValue.getCategoria());
                editoraChoiceBox.getSelectionModel().select(newValue.getEditora());

                int[] autoresIdxArr = new int[newValue.getAutores().size()];
                ArrayList<Integer> autoresIdx = new ArrayList<>();
                for (Autor autor: newValue.getAutores()) {
                    int id = 0;
                    for (Autor aux : Autor.getAll()) {
                        if(autor.getId() == aux.getId()) {
                            autoresIdx.add(id);
                            break;
                        }
                        id += 1;
                    }
                }

                for (int id = 0; id < autoresIdx.size(); id++) {
                    autoresIdxArr[id] = autoresIdx.get(id);
                }
                autoresListView.getSelectionModel().clearSelection();
                autoresListView.getSelectionModel().selectIndices(-1, autoresIdxArr);

                /*
                int idx = 0;
                for (Categoria cat: Categoria.getAll()) {

                    if(cat.getId() == newValue.getCategoria().getId()) {
                        break;
                    }
                    idx += 1;
                }
                categoriaChoiceBox.getSelectionModel().select(idx);*/
            }
        });
    }

    private void fillAutorListView() {
        this.autoresListView.setItems(FXCollections.observableList(Autor.getAll()));
    }

    private void fillChoiceBoxes() {
        this.livroChoiceBox.setItems(FXCollections.observableList(Livro.getAll()));
        this.categoriaChoiceBox.setItems(FXCollections.observableList(Categoria.getAll()));
        this.editoraChoiceBox.setItems(FXCollections.observableList(Editora.getAll()));
    }

    @FXML
    public void handleRemoverButton(ActionEvent e) {
        Livro livroRemover = this.livroChoiceBox.getSelectionModel().getSelectedItem();
        try {
            Livro.destroy(livroRemover);
            this.refreshData();
            this.clearFields();
        } catch (UnirestException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleLimparButton(ActionEvent e) {
        this.clearFields();
    }

    @FXML
    public void handleCadastrarButton(ActionEvent e) {
        String titulo = this.tituloTextField.getText();
        String isbn = this.isbnTextField.getText();
        Integer quantidade = Integer.parseInt(this.quantidadeTextField.getText());
        Double preco = Double.parseDouble(this.precoTextField.getText());

        Categoria categoria = this.categoriaChoiceBox.getSelectionModel().getSelectedItem();
        Editora editora = this.editoraChoiceBox.getSelectionModel().getSelectedItem();

        ObservableList<Integer> indices = this.autoresListView.getSelectionModel().getSelectedIndices();

        ArrayList<Autor> autores = new ArrayList<>();
        for(Integer id : indices) {
            Autor autor = this.autoresListView.getItems().get(id);
            autores.add(autor);
        }

        Livro livroCadastro = new Livro(-1, titulo, isbn, quantidade, preco, categoria, editora, autores);

        try {
            Livro.create(livroCadastro);
            this.refreshData();
            this.clearFields();
        }
        catch(UnirestException ex) {
            ex.printStackTrace();
        }

    }

    private void clearFields() {
        this.isbnTextField.clear();
        this.tituloTextField.clear();
        this.precoTextField.clear();
        this.quantidadeTextField.clear();

        this.livroChoiceBox.getSelectionModel().clearSelection();
        this.autoresListView.getSelectionModel().clearSelection();
        this.categoriaChoiceBox.getSelectionModel().clearSelection();
        this.editoraChoiceBox.getSelectionModel().clearSelection();
    }

    private void refreshData() throws UnirestException {
        Categoria.fetchAll();
        Editora.fetchAll();
        Autor.fetchAll();
        Livro.fetchAll();

        this.fillChoiceBoxes();
        this.fillAutorListView();
    }

}
