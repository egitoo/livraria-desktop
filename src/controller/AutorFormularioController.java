package controller;

import dao.AutorDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Autor;
public class AutorFormularioController {

    @FXML private TextField txfId, txfNome, txfEmail;
    @FXML private Button btnSalvar, btnDeletar, btnPesquisar;
    @FXML private TableView<Autor> tableView = new TableView<>();
    @FXML private TableColumn<Autor,Integer> colId = new TableColumn<>("Id");
    @FXML private TableColumn<Autor,String> colNome = new TableColumn<>("Nome");
    @FXML private TableColumn<Autor,String> colEmail = new TableColumn<>("Email");

    Autor autor = new Autor();
    AutorDAO autorDAO = new AutorDAO();

    public void criaTabela() {
        tableView.setEditable(true);
        colId.setCellValueFactory(new PropertyValueFactory<Autor, Integer>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<Autor, String>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Autor, String>("email"));

        tableView.setItems(autorDAO.listarTodos());
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
        autor.setId(Integer.parseInt(txfId.getText()));

        new AutorDAO().deletar(autor);

        limparCampos();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exclusão de autores");
        alert.setHeaderText("Autor excluído com sucesso");
        alert.showAndWait();
    }

    private void limparCampos() {
        txfId.setText("");
        txfNome.setText("");
        txfEmail.setText("");
        txfId.requestFocus();
    }
}
