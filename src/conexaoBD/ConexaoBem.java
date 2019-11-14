package conexaoBD;

import Objetos.Bem;
import conexaoBD.excecoesBD.CodInexistenteException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexaoBem extends Conexao {

    public boolean cadastrar(Bem newBem) {
        String sql = "insert into bens(nome, descricao, cod_Categoria, cod_Localizacao) values(?,?,?,?);";
        PreparedStatement estadoAtual;
        if(conectar()){
            try {
                estadoAtual = conexao.prepareStatement(sql);
                estadoAtual.setString(1,newBem.getNome());
                estadoAtual.setString(2,newBem.getDescricao());
                estadoAtual.setInt(3,newBem.getCodCategoria());
                estadoAtual.setInt(4,newBem.getCodLocalizacao());
                estadoAtual.execute();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                desconectar();
            }
        }
        return false;
    }

    public boolean deletar(int cod) throws CodInexistenteException {
        String sql = "delete from bens where cod_bem = "+cod+";";
        PreparedStatement estadoAtual;
        if(conectar()){
            try {
                estadoAtual = conexao.prepareStatement(sql);
                return true;
            } catch ( SQLException ex) {
                throw new CodInexistenteException("codNotExistentOfTableException", ex);
            } finally {
                desconectar();
            }
        }
        return false;
    }
}
