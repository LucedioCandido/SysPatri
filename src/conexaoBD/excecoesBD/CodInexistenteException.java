package conexaoBD.excecoesBD;

public class CodInexistenteException extends Exception {
    public CodInexistenteException(String message, Throwable cause){
        super(message, cause);
    }
}
