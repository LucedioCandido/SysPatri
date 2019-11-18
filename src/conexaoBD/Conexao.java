package conexaoBD;

import conexaoBD.excecoesBD.*;;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/*

Classe responsável por estabelecer a conexão do sistema com o banco de dados mysql
@author Lucédio candido
*/


public abstract class Conexao{
    private static String driver = "com.mysql.jdbc.Driver";
    private static String str_con = "jdbc:mysql://localhost:3306/syspatrimonio";
    private static String usuario = "root";
    private static String senha = "";

    public static Connection conexao;
    public static Statement estado;


    public static boolean conectar() throws FaltaDriverMSQLException,DatabaseAccessException{
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(str_con, usuario, senha);
            estado = conexao.createStatement();
        } catch (ClassNotFoundException ex) {
            throw new FaltaDriverMSQLException("Não encontrou o Driver do mysql, falta da biblioteca jdbcConector!");
        } catch (SQLException ex) {
            throw new DatabaseAccessException("Dados de acesso ao banco de dados incorretos");
        }

        return true;
    }

    public static void desconectar() throws DatabaseAccessException {
        try {
            estado.close();
            conexao.close();
        } catch (SQLException ex) {
            throw new DatabaseAccessException("Erro ao fechar conexao, conexao perdida:", ex);
        }

    }

}

/*
  public ArrayList<Produto> consultar() {
        ArrayList<Produto> lista = new ArrayList<>();
        String sql = "Select * from Produto";
        try {
            ResultSet resultado = estado.executeQuery(sql);
            while (resultado.next()) {
                Produto produto = new Produto();
                produto.setCod_Produto(resultado.getInt("Cod_Produto"));
                produto.setDescricao(resultado.getString("Descricao"));
                produto.setQuantidade(resultado.getInt("Quantidade"));
                produto.setTamanho(resultado.getDouble("Tamanho"));
                produto.setMedida(resultado.getString("Medida"));
                produto.setPreco(resultado.getDouble("Preco"));
                produto.setPreco_Revenda(resultado.getDouble("Preco_Revenda"));
                lista.add(produto);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar consultar dados do Produto!");
        }
        return lista;
    }
* */