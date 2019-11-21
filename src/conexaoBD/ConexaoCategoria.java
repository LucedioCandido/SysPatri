package conexaoBD;

import Objetos.Categoria;
import conexaoBD.excecoesBD.AbsenceDriverMSQLException;
import conexaoBD.excecoesBD.DatabaseAccessException;
import conexaoBD.excecoesBD.InvalidInputParametersException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConexaoCategoria extends Conexao {

    public boolean adicionar(String nome, String descricao) throws DatabaseAccessException, AbsenceDriverMSQLException, InvalidInputParametersException {
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


    public boolean excluir(int cod) throws DatabaseAccessException, AbsenceDriverMSQLException, InvalidInputParametersException {
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

    //consultar todas as categorias
    public ArrayList<Categoria> consultar() throws InvalidInputParametersException, DatabaseAccessException, AbsenceDriverMSQLException {
        ArrayList<Categoria> categorias = new ArrayList<Categoria>();
        String sql = "Select * from categoria ;";
        if(conectar()){
            try {
                ResultSet resultado = estado.executeQuery(sql);
                while (resultado.next()) {
                    Categoria bem = new Categoria(
                            resultado.getInt("cod_categoria"),
                            resultado.getString("nome"),
                            resultado.getString("descricao")
                    );

                    categorias.add(bem);
                }
            } catch (SQLException ex) {
                throw new InvalidInputParametersException("Erro na instrucao sql. Não encontrou no BD, tabela ou tupla. Espeficicados: ", ex );
            }
        }
        return categorias;
    }
}
