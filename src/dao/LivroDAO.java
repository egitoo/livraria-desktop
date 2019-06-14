package dao;

import model.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    private Connection conexao;

    public LivroDAO() {
        conexao = new ConnectionFactory().getConnection();
    }

    public void inserir(Livro livro) {
        String sql = "insert into livros (titulo, data_lancamento, quantidade, preco) values (?, ?, ?, ?)";

        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, livro.getTitulo());
            //stmt.setDate(2, livro.getData_lancamento());
            stmt.setInt(3, livro.getQuantidade());
            stmt.setFloat(4, livro.getPreco());

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
                //livro.setData_lancamento(resultados.getDate("data_lancamento"));
                livro.setQuantidade(resultados.getInt("quantidade"));
                livro.setPreco(resultados.getFloat("preco"));

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
        String sql = "update livros set titulo = ?, data_lancamento = ?, quantidade = ?, preco = ? where id = ?";

        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, livro.getTitulo());
            //stmt.setDate(2, livro.getData_lancamento());
            stmt.setInt(3, livro.getQuantidade());
            stmt.setFloat(4, livro.getPreco());
            stmt.setInt(5, livro.getId());

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
                livro.setData_lancamento(resultados.getDate("data_lancamento"));
                livro.setQuantidade(resultados.getInt("quantidade"));
                livro.setPreco(resultados.getFloat("preco"));
            }

            //Fechar conexão
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return livro;
    }
}
