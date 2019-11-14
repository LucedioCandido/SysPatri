package conexaoBD;

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


    public static boolean conectar() {
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(str_con, usuario, senha);
            estado = conexao.createStatement();
        } catch (ClassNotFoundException ex) {
            System.out.println("Não encontrou o Driver do mysql!");
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar, dados de acesso inválidos");
        }

        return true;
    }

    public static void desconectar() {
        try {
            estado.close();
            conexao.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar conexão!");
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


    public void deletar(int Cod_Produto) {
        String sql = "delete from Produto where Cod_Produto = ?";
        PreparedStatement estadoAtual;
        conectar();
        try {
            estadoAtual = conexao.prepareStatement(sql);
            estadoAtual.setInt(1, Cod_Produto);
            estadoAtual.execute();
            JOptionPane.showMessageDialog(null, "Cadastro deletado!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Deletar produto!");

        } finally {
            fecharConexao();
        }
    }


* */