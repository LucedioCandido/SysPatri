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



    public static boolean conectar() throws AbsenceDriverMSQLException,DatabaseAccessException{
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(str_con, usuario, senha);
            estado = conexao.createStatement();
        } catch (ClassNotFoundException ex) {
            throw new AbsenceDriverMSQLException("Não encontrou o Driver do mysql, falta da biblioteca jdbcConector!");
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