package br.com.ag.config;

/**
 * <p>
 * Tags de configura&ccedil;&atilde;o
 * </p>
 * 
 * @author rhuan
 * 
 */
public enum ConfigTags {

	/**
	 * Ra&iacute;z do xml
	 */
	ROOT("algoritmo-genetico"),

	/**
	 * Tag para as configura&ccedil;&otilde;es do xml
	 */
	CONFIG("config"),

	/**
	 * Tag com a quantidade de indiv&iacute;duos
	 */
	INDIVIUOS("individuos"),

	/**
	 * Tag com a quantidade de itera&ccedil;&otilde;es
	 */
	ITERACOES("iteracoes"),

	/**
	 * Tag com as rotas do grafo
	 */
	ROTAS("rotas"),

	/**
	 * Tag com uma rota para o grafo
	 */
	ROTA("rota"),

	/**
	 * Tag com a origem de uma rota do grafo
	 */
	ORIGEM("origem"),

	/**
	 * Tag para os destinos de uma origem
	 */
	DESTINOS("destinos"),

	/**
	 * Tag com um destino de uma determinada rota.
	 */
	DESTINO("destino"),

	/**
	 * Tag com o n&uacute;mero de tentativas
	 */
	TENTATIVAS("tentativas");

	private String tag;

	ConfigTags(String tag) {
		this.tag = tag;
	}

	/**
	 * <p>
	 * Calcula qual o ENUM para a tag especificada
	 * </p>
	 * 
	 * @param tag
	 *            Tag da qual se quer o ENUM
	 * @return Retorna o Enum da tag
	 */
	public ConfigTags fromTag(String tag) {
		if (tag != null && !tag.equals("")) {
			for (ConfigTags ctag : ConfigTags.values()) {
				if (ctag.tag.equals(tag)) {
					return ctag;
				}
			}
		}
		return null;
	}

	public String tag() {
		return tag;
	}

}
