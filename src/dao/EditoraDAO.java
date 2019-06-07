package dao;

import model.Editora;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditoraDAO {

    private Connection conexao;

    public EditoraDAO() {
        conexao = new ConnectionFactory().getConnection();
    }

    public void inserir(Editora editora) {
        String sql = "insert into editoras (nome, site, endereco, bairro, telefone) values (?, ?, ?, ?, ?)";

        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, editora.getNome());
            stmt.setString(2, editora.getSite());
            stmt.setString(3, editora.getEndereco());
            stmt.setString(4, editora.getBairro());
            stmt.setString(5, editora.getTelefone());

            //Executar o comando
            stmt.execute();

            //Fechar conexao
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Editora> listarTodos() {
        String sql = "select * from editoras";
        List<Editora> editoras = new ArrayList<>();


        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);

            //Executar
            ResultSet resultados = stmt.executeQuery();

            //Percorrer os resultados
            while (resultados.next()){
                Editora editora = new Editora();
                editora.setId(resultados.getInt("id"));
                editora.setNome(resultados.getString("nome"));
                editora.setSite(resultados.getString("site"));
                editora.setEndereco(resultados.getString("endereco"));
                editora.setBairro(resultados.getString("bairro"));
                editora.setTelefone(resultados.getString("telefone"));

                editoras.add(editora);
            }

            //Fechar conexão
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return editoras;
    }

    public void alterar(Editora editora) {
        String sql = "update editoras set nome = ?, site = ?, endereco = ?, bairro = ?, telefone = ? where id = ?";

        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, editora.getNome());
            stmt.setString(2, editora.getSite());
            stmt.setString(3, editora.getEndereco());
            stmt.setString(4, editora.getBairro());
            stmt.setString(5, editora.getTelefone());
            stmt.setInt(7, editora.getId());

            //Executar
            stmt.execute();

            //Fechar conexão
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Editora editora) {
        String sql = "delete from editoras where id = ?";

        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, editora.getId());

            //Executar
            stmt.execute();

            //Fechar conexão
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Editora listarPorId(int id) {
        String sql = "select id, nome, site, endereco, bairro, telefone from editoras where id = ?";
        Editora editora = new Editora();

        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            //Executar
            ResultSet resultados = stmt.executeQuery();

            //Encontra resultado
            while(resultados.next()) {
                editora.setId(resultados.getInt("id"));
                editora.setNome(resultados.getString("nome"));
                editora.setSite(resultados.getString("site"));
                editora.setEndereco(resultados.getString("endereco"));
                editora.setBairro(resultados.getString("bairro"));
                editora.setTelefone(resultados.getString("telefone"));
            }

            //Fechar conexão
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return editora;
    }
}