package conexaoBD;

import conexaoBD.excecoesBD.DatabaseAccessException;
import conexaoBD.excecoesBD.FaltaDriverMSQLException;
import conexaoBD.excecoesBD.InvalidInputParametersException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexaoCategoria extends Conexao   {

    public boolean adicionar(String nome, String descricao) throws DatabaseAccessException, FaltaDriverMSQLException, InvalidInputParametersException {
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
                throw new InvalidInputParametersException("Parametro invalido:",e);
            }

        }

        return false;
    }


    public boolean excluir(int cod) throws DatabaseAccessException, FaltaDriverMSQLException, InvalidInputParametersException {
        String sql = "delete from categoria where cod_categoria = ?";
        PreparedStatement estadoAtual;
        if(conectar()){
            try {
                estadoAtual = conexao.prepareStatement(sql);
                estadoAtual.setInt(1, cod);
                estadoAtual.execute();
                return true;
            } catch ( SQLException ex) {
                throw new InvalidInputParametersException("Elemento possui dependencias e nao pode ser removido, remover dependencias primeiro:",ex);
            } finally {
                desconectar();
            }
        }
        return false;
    }
}
