package SysPatrimonio.Cadastro;

import conexaoBD.ConexaoCategoria;
import conexaoBD.excecoesBD.AbsenceDriverMSQLException;
import conexaoBD.excecoesBD.DatabaseAccessException;
import conexaoBD.excecoesBD.InvalidInputParametersException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CadastraCategoria {
    @FXML
    public javafx.scene.control.TextField fieldNome;
    @FXML
    public javafx.scene.control.TextField fieldDescricao;
    @FXML
    private javafx.scene.control.Button OKButton;
    @FXML
    private javafx.scene.control.Button CancelButton;

    Stage primaryStage = new Stage();

    public void start() throws Exception {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("cadastraCategoria.fxml"));
        primaryStage.setTitle("Cadastrar Categoria");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    protected void OKclique() throws DatabaseAccessException, InvalidInputParametersException, AbsenceDriverMSQLException {
        Stage stage = (Stage) OKButton.getScene().getWindow();
        ConexaoCategoria categoria = new ConexaoCategoria();
        categoria.adicionar(fieldNome.getText(), fieldDescricao.getText());
        stage.close();
    }

    @FXML
    protected void Cancelclique(){
        Stage stages = (Stage) CancelButton.getScene().getWindow();
        stages.close();
    }

}
