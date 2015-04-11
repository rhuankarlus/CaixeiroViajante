package br.com.ag.config;

/**
 * <p>
 * Enum com os atributos de cada destino
 * </p>
 * 
 * @author rhuan
 * 
 */
public enum ConfigAtributos {

	/**
	 * Nome do destino
	 */
	NOME("nome"),

	/**
	 * Distancia da origem at&eacute; o destino.
	 */
	DISTANCIA("distancia");

	private String atributo;

	ConfigAtributos(String atributo) {
		this.atributo = atributo;
	}

	public String atributo() {
		return atributo;
	}

}
