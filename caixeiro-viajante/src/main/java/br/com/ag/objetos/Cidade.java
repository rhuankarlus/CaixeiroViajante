package br.com.ag.objetos;

/**
 * <p>
 * Classe que especifica cada cidade para as rotas do grafo.
 * </p>
 * 
 * @author rhuan
 * 
 */
public class Cidade {

	/**
	 * <p>
	 * Identificador e nome da cidade em ques&atilde;o
	 * </p>
	 */
	private String identificador;

	/**
	 * <p>
	 * For&ccedil;a o parametro para identificar a cidade
	 * </p>
	 * 
	 * @param identificador
	 *            Identificador (nome) da cidade
	 */
	public Cidade(String identificador) {
		this.identificador = identificador;
	}

	public String getIdentificador() {
		return identificador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identificador == null) ? 0 : identificador.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cidade other = (Cidade) obj;
		if (identificador == null) {
			if (other.identificador != null)
				return false;
		} else if (!identificador.equals(other.identificador))
			return false;
		return true;
	}

}
