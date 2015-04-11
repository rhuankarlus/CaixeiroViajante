package br.com.ag.evolucao.agentes;

import br.com.ag.objetos.Rota;

/**
 * <p>
 * Classe mais especializada para o {@code Cromossomo}.<br>
 * Basicamente &eacute; a classe respons&aacute;vel por carregar cada rota da solu&&ccedil;&atilde;o final que seria o
 * cromossomo.
 * </p>
 * 
 * @author rhuan
 * 
 */
public class Gene {

	/**
	 * <p>
	 * Rota armazenada por um Gene
	 * </p>
	 */
	private Rota rota;

	/**
	 * <p>
	 * Um {@code Gene} s&oacute; pode ser instanciado se for vinculado a uma {@code Rota}.
	 * </p>
	 * 
	 * @param rota
	 *            Rota vinculada a este gene
	 */
	public Gene(Rota rota) {
		this.rota = rota;
	}

	public Rota getRota() {
		return rota;
	}

}
