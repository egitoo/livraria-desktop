package dao;

import model.Livro;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    private Connection conexao;

    public void conectar() {
        conexao = new ConnectionFactory().getConnection();
    }

    public void inserir(Livro livro) {
        String sql = "insert into livros (titulo, data_lancamento, quantidade, preco, editora_id) values (?, ?, ?, ?, ?)";
        conectar();
        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, livro.getTitulo());
            stmt.setDate(2, Date.valueOf(livro.getData_lancamento()));
            stmt.setInt(3, livro.getQuantidade());
            stmt.setFloat(4, livro.getPreco());
            stmt.setInt(5, livro.getEditora_id());

            //Executar o comando
            stmt.execute();

            //Fechar conexao
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Livro> listarTodos() {
        String sql = "select * from livros";
        List<Livro> livros = new ArrayList<>();
        conectar();
        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);

            //Executar
            ResultSet resultados = stmt.executeQuery();

            //Percorrer os resultados
            while (resultados.next()){
                Livro livro = new Livro();
                livro.setId(resultados.getInt("id"));
                livro.setTitulo(resultados.getString("titulo"));
                livro.setData_lancamento(LocalDate.parse(resultados.getString("data_lancamento")));
                livro.setQuantidade(resultados.getInt("quantidade"));
                livro.setPreco(resultados.getFloat("preco"));
                livro.setEditora_id((resultados.getInt("editora_id")));

                livros.add(livro);
            }

            //Fechar conexão
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return livros;
    }

    public void alterar(Livro livro) {
        String sql = "update livros set titulo = ?, data_lancamento = ?, quantidade = ?, preco = ?, editora_id = ? where id = ?";
        conectar();
        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, livro.getTitulo());
            stmt.setDate(2, Date.valueOf(livro.getData_lancamento()));
            stmt.setInt(3, livro.getQuantidade());
            stmt.setFloat(4, livro.getPreco());
            stmt.setInt(5, livro.getEditora_id());
            stmt.setInt(6, livro.getId());


            //Executar
            stmt.execute();

            //Fechar conexão
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Livro livro) {
        String sql = "delete from livros where id = ?";
        conectar();
        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, livro.getId());

            //Executar
            stmt.execute();

            //Fechar conexão
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Livro listarPorId(int id) {
        String sql = "select * from livros where id = ?";
        Livro livro = new Livro();
        conectar();
        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            //Executar
            ResultSet resultados = stmt.executeQuery();

            //Encontra resultado
            while(resultados.next()) {
                livro.setId(resultados.getInt("id"));
                livro.setTitulo(resultados.getString("titulo"));
                livro.setData_lancamento(LocalDate.parse(resultados.getDate("data_lancamento").toString()));
                livro.setQuantidade(resultados.getInt("quantidade"));
                livro.setPreco(resultados.getFloat("preco"));
                livro.setEditora_id(resultados.getInt("editora_id"));
            }

            //Fechar conexão
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return livro;
    }
}
