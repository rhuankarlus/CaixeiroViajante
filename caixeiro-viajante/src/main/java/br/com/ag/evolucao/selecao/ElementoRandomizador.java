package br.com.ag.evolucao.selecao;

import br.com.ag.evolucao.agentes.Cromossomo;

/**
 * <p>
 * Classe respons&aacute;vel por armazenar um cromossomo e seus respectivos intervalos na roleta.
 * </p>
 * 
 * @author rhuan
 * 
 */
public class ElementoRandomizador {

	private Cromossomo cromossomo;
	private Double limiteInferior;
	private Double limiteSuperior;

	/**
	 * <p>
	 * Instancia um novo elemento randomizador com o cromossomo e limites definidos
	 * </p>
	 * 
	 * @param cromossomo
	 *            Cromossomo a ser inserido
	 * @param limiteInferior
	 *            Limite inferior da inser&ccedil;&atilde;o
	 * @param limiteSuperior
	 *            Limite superior da inser&ccedil;&atilde;o
	 */
	public ElementoRandomizador(Cromossomo cromossomo, Double limiteInferior, Double limiteSuperior) {
		this.cromossomo = cromossomo;
		this.limiteInferior = limiteInferior;
		this.limiteSuperior = limiteSuperior;
	}

	public Cromossomo getCromossomo() {
		return cromossomo;
	}

	public Double getLimiteInferior() {
		return limiteInferior;
	}

	public Double getLimiteSuperior() {
		return limiteSuperior;
	}

}
