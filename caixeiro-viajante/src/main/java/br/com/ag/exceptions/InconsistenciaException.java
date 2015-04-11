package br.com.ag.exceptions;

/**
 * <p>
 * Exception lan&ccedil;ada quando se tenta inserir uma mesma rota duas vezes com valores de distancia distintos.
 * </p>
 * 
 * @author rhuan
 * 
 */
public class InconsistenciaException extends Exception {

	/**
	 * Serial para tr&aacute;fego na rede
	 */
	private static final long serialVersionUID = 2732313596426623298L;

	/**
	 * <p>
	 * Lan&ccedil;a uma nova exception para a inconsistencia em algum canto da aplica&ccedil;&atilde;o
	 * </p>
	 * 
	 * @param msg
	 *            Mensagem de erro
	 */
	public InconsistenciaException(String msg) {
		super(msg);
	}
}
