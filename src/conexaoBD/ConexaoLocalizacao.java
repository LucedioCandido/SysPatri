package conexaoBD;

import Objetos.Localizacao;
import conexaoBD.excecoesBD.AbsenceDriverMSQLException;
import conexaoBD.excecoesBD.DatabaseAccessException;
import conexaoBD.excecoesBD.InvalidInputParametersException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConexaoLocalizacao extends Conexao {
    public boolean adicionar(String nome, String descricao) throws DatabaseAccessException, AbsenceDriverMSQLException, InvalidInputParametersException {
        String sql = "insert into localizacao(nome, descricao) values(?,?);";
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

    public boolean remover(int cod) throws DatabaseAccessException, AbsenceDriverMSQLException, InvalidInputParametersException {
        String sql = "delete from localizacao where cod_localizacao = ?";
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

    public Localizacao isExistent(String nome) throws InvalidInputParametersException, DatabaseAccessException, AbsenceDriverMSQLException {
        String sql = "Select cod_localizacao from localizacao where nome ='" + nome + "';";
        if (conectar()) {
            try {
                ResultSet resultado = estado.executeQuery(sql);
                while (resultado.next()) {
                    Localizacao local = new Localizacao(
                            resultado.getInt("cod_localizacao"),
                            resultado.getString("nome"),
                            resultado.getString("descricao")
                    );
                    return local;
                }
            } catch (SQLException ex) {
                throw new InvalidInputParametersException("Erro na instrucao sql. Não encontrou no BD, tabela ou tupla. Espeficicados: ", ex);
            }
        }
        return null;
    }

    public ArrayList<Localizacao> consultar() throws InvalidInputParametersException, DatabaseAccessException, AbsenceDriverMSQLException {
        ArrayList<Localizacao> locais = new ArrayList<Localizacao>();
        String sql = "Select * from localizacao;";
        if(conectar()){
            try {
                ResultSet resultado = estado.executeQuery(sql);
                while (resultado.next()) {
                    Localizacao local = new Localizacao(
                            resultado.getInt("cod_localizacao"),
                            resultado.getString("nome"),
                            resultado.getString("descricao")
                    );

                    locais.add(local);
                }
            } catch (SQLException ex) {
                throw new InvalidInputParametersException("Erro na instrucao sql. Não encontrou no BD, tabela ou tupla. Espeficicados: ", ex );
            }
        }

        return locais;
    }

    public int procuraExistenciaLocal(String nome) throws DatabaseAccessException, AbsenceDriverMSQLException, InvalidInputParametersException {

        if(conectar()){
            try {
                String sql = "Select cod_localizacao from localizacao where nome ='"+nome+"' ;";
                ResultSet resultado = estado.executeQuery(sql);
                resultado.next();
                return resultado.getInt("cod_localizacao");
            } catch (SQLException ex) {
                throw new InvalidInputParametersException("Erro na instrucao sql. Não encontrou no BD, tabela ou tupla. Espeficicados: ", ex );
            }
        }
        return -1;
    }
}
