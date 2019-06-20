package controller;

import com.sun.javafx.binding.StringFormatter;
import dao.EditoraDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Editora;

import java.net.URL;
import java.util.ResourceBundle;

public class EditoraFormularioController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML private TextField txfId, txfNome, txfSite, txfEndereco, txfBairro, txfTelefone, txfMunicipio;
    @FXML private Button btnSalvar, btnDeletar, btnPesquisar, btnAlterar;
    @FXML private TableView<Editora> tableView = new TableView<>();
    @FXML private TableColumn<Editora,Integer> colId = new TableColumn<>("Codigo");
    @FXML private TableColumn<Editora,String> colNome = new TableColumn<>("Nome");
    @FXML private TableColumn<Editora,String> colSite = new TableColumn<>("Site");
    @FXML private TableColumn<Editora,String> colEndereco = new TableColumn<>("Endereco");
    @FXML private TableColumn<Editora,String> colBairro = new TableColumn<>("Bairro");
    @FXML private TableColumn<Editora, String> colTelefone = new TableColumn<>("Telefone");
    @FXML private TableColumn<Editora, String> colMunicipio = new TableColumn<>("Municipio");
    @FXML private ObservableList <Editora> editoras;

    Editora editora = new Editora();
    EditoraDAO editoraDAO = new EditoraDAO();

    public void pesquisar() {
        tableView.setItems(null);
        if(txfId.getText().isEmpty()) {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colSite.setCellValueFactory(new PropertyValueFactory<>("site"));
            colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
            colBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
            colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
            colMunicipio.setCellValueFactory(new PropertyValueFactory<>("municipio_id"));
            editoras = FXCollections.observableArrayList(editoraDAO.listarTodos());

            tableView.setItems(editoras);
        }
        else {
            editora = editoraDAO.listarPorId(Integer.parseInt(txfId.getText()));
            txfNome.setText(editora.getNome());
            txfSite.setText(editora.getSite());
            txfEndereco.setText(editora.getEndereco());
            txfBairro.setText(editora.getBairro());
            txfTelefone.setText(editora.getTelefone());
            txfMunicipio.setText(Integer.toString(editora.getMunicipio_id()));
        }
    }
    public void salvar() {

        editora.setNome(txfNome.getText());
        editora.setSite(txfSite.getText());
        editora.setEndereco(txfEndereco.getText());
        editora.setBairro(txfBairro.getText());
        editora.setTelefone(txfTelefone.getText());
        editora.setMunicipio_id(Integer.valueOf(txfMunicipio.getText()));

        new EditoraDAO().inserir(editora);

        limparCampos();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de editoras");
        alert.setHeaderText("Editora cadastrada com sucesso");
        alert.showAndWait();
    }

    public void deletar() {
        if(txfId.getText().isEmpty()) {
            System.out.println("Insira um código válido!");
        }
        else {
            editora.setId(Integer.parseInt(txfId.getText()));
            new EditoraDAO().deletar(editora);

            limparCampos();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exclusão de editoras");
            alert.setHeaderText("Editora excluída com sucesso");
            alert.showAndWait();
        }
    }

    public void alterar() {
        editora.setNome(txfNome.getText());
        editora.setSite(txfSite.getText());
        editora.setEndereco(txfEndereco.getText());
        editora.setBairro(txfBairro.getText());
        editora.setTelefone(txfTelefone.getText());
        editora.setMunicipio_id(Integer.valueOf(txfMunicipio.getText()));
        editoraDAO.alterar(editora);

        limparCampos();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de editoras");
        alert.setHeaderText("Editora alterada com sucesso");
        alert.showAndWait();
    }

    private void limparCampos() {
        txfId.setText("");
        txfNome.setText("");
        txfSite.setText("");
        txfEndereco.setText("");
        txfBairro.setText("");
        txfTelefone.setText("");
        txfMunicipio.setText("");
        txfId.requestFocus();
    }
}