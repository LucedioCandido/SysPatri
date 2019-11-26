package SysPatrimonio.Cadastro;

import Objetos.Bem;
import conexaoBD.ConexaoBem;
import conexaoBD.ConexaoCategoria;
import conexaoBD.ConexaoLocalizacao;
import conexaoBD.excecoesBD.AbsenceDriverMSQLException;
import conexaoBD.excecoesBD.DatabaseAccessException;
import conexaoBD.excecoesBD.InvalidInputParametersException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CadastraBem {
    @FXML
    public javafx.scene.control.TextField fieldNome;
    @FXML
    public javafx.scene.control.TextField fieldDescricao;
    @FXML
    public javafx.scene.control.TextField fieldCategoria;
    @FXML
    public javafx.scene.control.TextField fieldLocal;
    @FXML
    private javafx.scene.control.Button OKButton;
    @FXML
    private javafx.scene.control.Button CancelButton;

    Stage primaryStage = new Stage();

    public void start() throws Exception {
        Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("cadastraBem.fxml"));
        primaryStage.setTitle("Cadastrar Bem");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    protected void OKclique() throws DatabaseAccessException, InvalidInputParametersException, AbsenceDriverMSQLException {
        Stage stage = (Stage) OKButton.getScene().getWindow();
        int codCat, codLoc;
        ConexaoCategoria Cat = new ConexaoCategoria();
        ConexaoLocalizacao Loc = new ConexaoLocalizacao();
        codCat = Cat.procuraExistenciaCategoria(fieldCategoria.getText()) ;
        codLoc = Loc.procuraExistenciaLocal(fieldLocal.getText());
        Bem bemCadastro = new Bem(fieldNome.getText(), fieldDescricao.getText(), codCat, codLoc);
        ConexaoBem bemCadastrar = new ConexaoBem();
        bemCadastrar.adicionar(bemCadastro);
        stage.close();
    }

    @FXML
    protected void Cancelclique(){
        Stage stages = (Stage) CancelButton.getScene().getWindow();
        stages.close();
    }

}
