package SysPatrimonio.Cadastro;

import conexaoBD.ConexaoLocalizacao;
import conexaoBD.excecoesBD.AbsenceDriverMSQLException;
import conexaoBD.excecoesBD.DatabaseAccessException;
import conexaoBD.excecoesBD.InvalidInputParametersException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CadastraLocal {
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
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("cadastraLocal.fxml"));
        primaryStage.setTitle("Cadastrar Local");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    protected void OKclique() throws DatabaseAccessException, InvalidInputParametersException, AbsenceDriverMSQLException {
        Stage stage = (Stage) OKButton.getScene().getWindow();
        ConexaoLocalizacao local = new ConexaoLocalizacao();
        local.adicionar(fieldNome.getText(), fieldDescricao.getText());
        stage.close();
    }

    @FXML
    protected void Cancelclique(){
        Stage stages = (Stage) CancelButton.getScene().getWindow();
        stages.close();
    }

}
