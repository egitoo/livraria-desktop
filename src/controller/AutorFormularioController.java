package controller;

import dao.AutorDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Autor;
import java.net.URL;
import java.util.ResourceBundle;

public class AutorFormularioController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML private TextField txfId, txfNome, txfEmail;
    @FXML private Button btnSalvar, btnDeletar, btnPesquisar, btnAlterar;
    @FXML private TableView<Autor> tableView = new TableView<>();
    @FXML private TableColumn<Autor,Integer> colId = new TableColumn<>("Codigo");
    @FXML private TableColumn<Autor,String> colNome = new TableColumn<>("Nome");
    @FXML private TableColumn<Autor,String> colEmail = new TableColumn<>("Email");
    @FXML private ObservableList <Autor> autores;

    Autor autor = new Autor();
    AutorDAO autorDAO = new AutorDAO();


    public void pesquisar() {
        tableView.setItems(null);
        if(txfId.getText().isEmpty()) {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            autores = FXCollections.observableArrayList(autorDAO.listarTodos());

            tableView.setItems(autores);
        }
        else {
            autor = autorDAO.listarPorId(Integer.parseInt(txfId.getText()));
            txfNome.setText(autor.getNome());
            txfEmail.setText(autor.getEmail());
        }
    }
    public void salvar() {

        autor.setNome(txfNome.getText());
        autor.setEmail(txfEmail.getText());

        new AutorDAO().inserir(autor);

        limparCampos();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de autores");
        alert.setHeaderText("Autor cadastrado com sucesso");
        alert.showAndWait();
    }

    public void deletar() {
        if(txfId.getText().isEmpty()) {
            System.out.println("Insira um código válido!");
        }
        else {
            autor.setId(Integer.parseInt(txfId.getText()));
            new AutorDAO().deletar(autor);

            limparCampos();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exclusão de autores");
            alert.setHeaderText("Autor excluído com sucesso");
            alert.showAndWait();
        }
    }

    public void alterar() {
        autor.setNome(txfNome.getText());
        autor.setEmail(txfEmail.getText());
        autorDAO.alterar(autor);

        limparCampos();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de autores");
        alert.setHeaderText("Autor alterado com sucesso");
        alert.showAndWait();
    }

    private void limparCampos() {
        txfId.setText("");
        txfNome.setText("");
        txfEmail.setText("");
        txfId.requestFocus();
    }
}