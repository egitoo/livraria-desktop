package controller;

import dao.LivroDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Livro;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class LivrosFormularioController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML private TextField txfId, txfTitulo, txfData, txfQuantidade, txfPreco, txfEditora;
    @FXML private Button btnSalvar, btnDeletar, btnPesquisar, btnAlterar;
    @FXML private TableView<Livro> tableView = new TableView<>();
    @FXML private TableColumn<Livro,Integer> colId = new TableColumn<>("Codigo");
    @FXML private TableColumn<Livro,String> colTitulo = new TableColumn<>("Titulo");
    @FXML private TableColumn<Livro,String> colData = new TableColumn<>("Data lançamento");
    @FXML private TableColumn<Livro,String> colQuantidade = new TableColumn<>("Quantidade");
    @FXML private TableColumn<Livro,String> colPreco = new TableColumn<>("Preço");
    @FXML private TableColumn<Livro, String> colEditora = new TableColumn<>("Editora");
    @FXML private ObservableList <Livro> livros;

    Livro livro = new Livro();
    LivroDAO livroDAO = new LivroDAO();
    DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyy");

    public void pesquisar() {
        tableView.setItems(null);
        if(txfId.getText().isEmpty()) {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            colData.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getData_lancamento().format(data)));
            colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
            colEditora.setCellValueFactory(new PropertyValueFactory<>("editora_id"));
            livros = FXCollections.observableArrayList(livroDAO.listarTodos());

            tableView.setItems(livros);
        }
        else {
            livro = livroDAO.listarPorId(Integer.parseInt(txfId.getText()));
            txfTitulo.setText(livro.getTitulo());
            txfData.setText(data.format(livro.getData_lancamento()));
            txfQuantidade.setText(Integer.toString(livro.getQuantidade()));
            txfPreco.setText(Float.toString(livro.getPreco()));
            txfEditora.setText(Integer.toString(livro.getEditora_id()));
        }
    }
    public void salvar() {

        livro.setTitulo(txfTitulo.getText());
        livro.setQuantidade(Integer.valueOf(txfQuantidade.getText()));
        livro.setPreco(Float.valueOf(txfPreco.getText()));
        livro.setEditora_id(Integer.valueOf(txfEditora.getText()));
        livro.setData_lancamento(LocalDate.parse(txfData.getText(), data));

        new LivroDAO().inserir(livro);

        limparCampos();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de livros");
        alert.setHeaderText("Livro cadastrado com sucesso");
        alert.showAndWait();
    }

    public void deletar() {
        if(txfId.getText().isEmpty()) {
            System.out.println("Insira um código válido!");
        }
        else {
            livro.setId(Integer.parseInt(txfId.getText()));
            new LivroDAO().deletar(livro);

            limparCampos();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exclusão de livros");
            alert.setHeaderText("Livro excluído com sucesso");
            alert.showAndWait();
        }
    }

    public void alterar() {
        livro.setTitulo(txfTitulo.getText());
        livro.setData_lancamento(LocalDate.parse(txfData.getText(), data));
        livro.setQuantidade(Integer.valueOf(txfQuantidade.getText()));
        livro.setPreco(Float.valueOf(txfPreco.getText()));
        livro.setEditora_id(Integer.valueOf(txfEditora.getText()));
        livroDAO.alterar(livro);

        limparCampos();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de livros");
        alert.setHeaderText("Livro alterado com sucesso");
        alert.showAndWait();
    }

    private void limparCampos() {
        txfId.setText("");
        txfTitulo.setText("");
        txfData.setText("");
        txfQuantidade.setText("");
        txfPreco.setText("");
        txfEditora.setText("");
        txfId.requestFocus();
    }
}