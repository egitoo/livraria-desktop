package dao;

import model.Autor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {

    private Connection conexao;

    public void conectar() {
        conexao = new ConnectionFactory().getConnection();
    }

    public void inserir(Autor autor) {
        String sql = "insert into autores (nome, email) values (?, ?)";
        conectar();
        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, autor.getNome());
            stmt.setString(2, autor.getEmail());

            //Executar o comando
            stmt.execute();

            //Fechar conexao
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Autor> listarTodos() {
        String sql = "select * from autores";
        List<Autor> autores = new ArrayList<>();
        conectar();
        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);

            //Executar
            ResultSet resultados = stmt.executeQuery();

            //Percorrer os resultados
            while (resultados.next()){
                Autor autor = new Autor();
                autor.setId(resultados.getInt("id"));
                autor.setNome(resultados.getString("nome"));
                autor.setEmail(resultados.getString("email"));

                autores.add(autor);
            }

            //Fechar conexão
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return autores;
    }

    public void alterar(Autor autor) {
        String sql = "update autores set nome = ?, email = ? where id = ?";
        conectar();
        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, autor.getNome());
            stmt.setString(2, autor.getEmail());
            stmt.setInt(3, autor.getId());

            //Executar
            stmt.execute();

            //Fechar conexão
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Autor autor) {

            String sql = "delete from autores where id = ?";
            conectar();
            try {
                //Prepara conexão
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, autor.getId());

                //Executar
                stmt.execute();

                //Fechar conexão
                conexao.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


    public Autor listarPorId(int id) {
        String sql = "select id, nome, email from autores where id = ?";
        Autor autor = new Autor();
        conectar();
        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            //Executar
            ResultSet resultados = stmt.executeQuery();

            //Encontra resultado
            while(resultados.next()) {
                autor.setId(resultados.getInt("id"));
                autor.setNome(resultados.getString("nome"));
                autor.setEmail(resultados.getString("email"));
            }

            //Fechar conexão
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return autor;
    }
}
