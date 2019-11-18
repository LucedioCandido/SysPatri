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

    //consultar todas as localizacoes
    public ArrayList<Localizacao> consultar() throws InvalidInputParametersException, DatabaseAccessException, AbsenceDriverMSQLException {
        ArrayList<Localizacao> locais = new ArrayList<Localizacao>();
        String sql = "Select * from categoria ;";
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

}
