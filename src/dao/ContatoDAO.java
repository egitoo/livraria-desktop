package dao;

import model.Contato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {

    private Connection conexao;

    public ContatoDAO() {
        conexao = new ConnectionFactory().getConnection();
    }

    public void inserir(Contato contato) {
        String sql = "insert into contatos (nome, contato) values  (?, ?)";

        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getContato());

            //Executa comando
            stmt.execute();

            //Encerra conexão
            conexao.close();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Contato> listarTodos() {
        String sql = "select * from contatos";
        List<Contato> contatos = new ArrayList<>();

        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);

            //Executar
            ResultSet resultados = stmt.executeQuery();

            //Percorrer os resultados
            while (resultados.next()){
                Contato contato = new Contato();
                contato.setId(resultados.getInt("id"));
                contato.setNome(resultados.getString("nome"));
                contato.setContato(resultados.getString("contato"));

                contatos.add(contato);
            }

            //Fechar conexão
            conexao.close();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return contatos;
    }

    public void alterar(Contato contato) {
        String sql = "update contatos set nome = ?, contato = ? where id = ?";

        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getContato());
            stmt.setInt(3, contato.getId());


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(Contato contato) {
        String sql = "delete from contatos where id = ?";

        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, contato.getId());

            //Executar
            stmt.execute();

            //Fechar conexão
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Contato listarPorId(int id) {
        String sql = "select id, nome, contato from contatos where id = ?";
        Contato contato = new Contato();

        try {
            //Prepara conexão
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            //Executar
            ResultSet resultados = stmt.executeQuery();

            //Encontra resultado
            while(resultados.next()) {
                contato.setId(resultados.getInt("id"));
                contato.setNome(resultados.getString("nome"));
                contato.setContato(resultados.getString("contato"));
            }

            //Fechar conexão
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return contato;
    }
}
