package conexaoBD;

import Objetos.Bem;
import Objetos.Localizacao;
import conexaoBD.excecoesBD.AbsenceDriverMSQLException;
import conexaoBD.excecoesBD.DatabaseAccessException;
import conexaoBD.excecoesBD.InvalidInputParametersException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConexaoBem extends Conexao {

    //Cadastro de um novo bem
    public boolean adicionar(Bem newBem) throws DatabaseAccessException, AbsenceDriverMSQLException, InvalidInputParametersException {
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
                throw new InvalidInputParametersException("Parametro invalido:", e);
            }finally {
                desconectar();
            }
        }
        return false;
    }

    //remoção de um bem
    public boolean remover(int cod) throws InvalidInputParametersException, AbsenceDriverMSQLException, DatabaseAccessException {
        String sql = "delete from bens where cod_bem = "+cod+";";
        PreparedStatement estadoAtual;
        if(conectar()){
            try {
                estadoAtual = conexao.prepareStatement(sql);
                estadoAtual.execute();
                return true;
            } catch ( SQLException ex) {
                throw new InvalidInputParametersException("Elemento possui dependencia e nao pode ser removido, remover dependencias primeiro:",ex);
            } finally {
                desconectar();
            }
        }
        return false;
    }

    //movimentar bem entre localizacao, de uma para outra.
    public boolean mover(Bem bem, Localizacao destino)throws InvalidInputParametersException,DatabaseAccessException,AbsenceDriverMSQLException  {
        String sql = "update bens set cod_localizacao ="+destino.getCodLocalizacao()+" from bens where cod_bem = "+bem.getCodBem()+";";
        PreparedStatement estadoAtual;
        if(conectar()){
            try {
                estadoAtual = conexao.prepareStatement(sql);
                estadoAtual.execute();
                return true;
            } catch ( SQLException ex) {
                throw new InvalidInputParametersException("Erro na operação: ",ex);
            } finally {
                desconectar();
            }
        }
        return false;
    }

    //consultar por localização
    public ArrayList<Bem> consultar(Localizacao local) throws InvalidInputParametersException {
        ArrayList<Bem> bens = new ArrayList<Bem>();
        String sql = "Select * from bens where cod_localizacao = "+local.getCodLocalizacao()+";";
        try {
            ResultSet resultado = estado.executeQuery(sql);
            while (resultado.next()) {
                Bem bem = new Bem(
                        resultado.getInt("cod_bem"),
                        resultado.getString("nome"),
                        resultado.getString("descricao"),
                        resultado.getInt("cod_localizacao"),
                        resultado.getInt("cod_categoria")
                );

                bens.add(bem);
            }
        } catch (SQLException ex) {
            throw new InvalidInputParametersException("Erro na instrucao sql. Não encontrou no BD, tabela ou tupla. Espeficicados: ", ex );
        }
        return bens;
    }

    //consultar por código do bem
    public ArrayList<Bem> consultar(int codBem) throws InvalidInputParametersException {
        ArrayList<Bem> bens = new ArrayList<Bem>();
        String sql = "Select * from bens where cod_bem = "+codBem+";";
        try {
            ResultSet resultado = estado.executeQuery(sql);
            while (resultado.next()) {
                Bem bem = new Bem(
                        resultado.getInt("cod_bem"),
                        resultado.getString("nome"),
                        resultado.getString("descricao"),
                        resultado.getInt("cod_localizacao"),
                        resultado.getInt("cod_categoria")
                );

                bens.add(bem);
            }
        } catch (SQLException ex) {
            throw new InvalidInputParametersException("Erro na instrução sql. Não encontrou no BD, tabela ou tupla, espeficicados.: ", ex );
        }
        return bens;
    }

    //consultar por nome ou descricao do bem.
    // Paramentro: string a ser buscada, inteiro com representando a tupla na tabela, 0 para nome, qualquero outro valor  inteiro para descricao.
    public ArrayList<Bem> consultar(String valorBusca, int Colfiltro) throws InvalidInputParametersException {
        String tuplaDaBusca = (Colfiltro == 0) ? "nome" : "descricao";
        ArrayList<Bem> bens = new ArrayList<Bem>();
        String sql = "Select * from bens where "+ tuplaDaBusca+" like '"+valorBusca+"%';";
        try {
            ResultSet resultado = estado.executeQuery(sql);
            while (resultado.next()) {
                Bem bem = new Bem(
                        resultado.getInt("cod_bem"),
                        resultado.getString("nome"),
                        resultado.getString("descricao"),
                        resultado.getInt("cod_localizacao"),
                        resultado.getInt("cod_categoria")
                );
                bens.add(bem);
            }
        } catch (SQLException ex) {
            throw new InvalidInputParametersException("Erro na instrução sql. Não encontrou no BD, tabela ou tupla, espeficicados.: ", ex );
        }
        return bens;
    }

    public ResultSet gerarRelatorio() throws InvalidInputParametersException {
        try {
            String sql = "Select * from bens wh%';";
            ResultSet resultado = estado.executeQuery(sql);
            return  resultado;
        } catch (SQLException ex) {
            throw new InvalidInputParametersException("Erro na instrução sql. Não encontrou no BD, tabela ou tupla, espeficicados.: ", ex );
        }
    }

}
