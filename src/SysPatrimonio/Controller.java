package SysPatrimonio;

import SysPatrimonio.Cadastro.CadastraBem;
import SysPatrimonio.Cadastro.CadastraCategoria;
import SysPatrimonio.Cadastro.CadastraLocal;
import com.itextpdf.text.DocumentException;
import conexaoBD.excecoesBD.AbsenceDriverMSQLException;
import conexaoBD.excecoesBD.DatabaseAccessException;
import javafx.fxml.FXML;
import relatorios.Relatorio;

public class Controller {
    public Controller() {
    }

    @FXML
    private void cadastraBem() throws Exception {
        CadastraBem cadastro = new CadastraBem();
        cadastro.start();
    }

    @FXML
    private void deletaBem() {
    }

    @FXML
    private void cadastraCategoria() throws Exception {
        CadastraCategoria cadastro = new CadastraCategoria();
        cadastro.start();
    }

    @FXML
    private void deletaCategoria() {
    }

    @FXML
    private void cadastraLocal() throws Exception {
        CadastraLocal cadastro = new CadastraLocal();
        cadastro.start();
    }

    @FXML
    private void deletaLocal() {
    }

    @FXML
    private void listaBem() {

    }

    @FXML
    private void listaCategoria() {

    }

    @FXML
    private void listaLocal() {

    }

    @FXML
    private void relatorioGeral() throws DocumentException, DatabaseAccessException, AbsenceDriverMSQLException {
        Relatorio rel = new Relatorio();
        rel.exportData();
    }
}
