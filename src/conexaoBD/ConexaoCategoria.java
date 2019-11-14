package conexaoBD;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexaoCategoria extends Conexao   {



    public boolean cadastrar(String nome, String descricao) {
        String sql = "insert into categoria(nome, descricao) values(?,?);";
        PreparedStatement estadoAtual;
        if(conectar()){
            try {
                estadoAtual = conexao.prepareStatement(sql);
                estadoAtual.setString(1,nome);
                estadoAtual.setString(2,descricao);
                estadoAtual.execute();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return false;
    }


    public boolean deletar(int cod) {
        String sql = "delete from categoria where cod_categoria = ?";
        PreparedStatement estadoAtual;
        if(conectar()){
            try {
                estadoAtual = conexao.prepareStatement(sql);
                estadoAtual.setInt(1, cod);
                estadoAtual.execute();
                return true;
            } catch ( SQLException ex) {
                System.out.println("errro ao deletar");
            } finally {
                desconectar();
            }
        }
        return false;
    }
}
