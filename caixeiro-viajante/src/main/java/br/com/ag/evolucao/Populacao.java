package br.com.ag.evolucao;

import java.util.ArrayList;
import java.util.List;

import br.com.ag.evolucao.agentes.Cromossomo;

/**
 * <p>
 * Classe respons&aacute;vel por armazenar a popula&ccedil;&atilde;o de Cromossomos do algoritmo.
 * </p>
 * 
 * @author rhuan
 * 
 */
public class Populacao {

	/**
	 * <p>
	 * Lista de Cromossomos na popula&ccedil;&atilde;o
	 * </p>
	 */
	private List<Cromossomo> cromossomos;

	public Populacao() {
		this.cromossomos = new ArrayList<Cromossomo>();
	}

	/**
	 * <p>
	 * Insere um novo {@code Cromossomo} na popula&ccedil;&atilde;o
	 * </p>
	 * 
	 * @param cromossomo
	 *            Poss&iacute;vel solu&ccedil;&atilde;o ao problema
	 */
	public void insereCromossomo(Cromossomo cromossomo) {
		this.cromossomos.add(cromossomo);
	}

	public List<Cromossomo> getCromossomos() {
		return cromossomos;
	}

	/**
	 * <p>
	 * Calcula qual o Cromossomo com maior fitness na popula&ccedil;&atilde;o
	 * </p>
	 * 
	 * @return Retorna o ganhador em quest&atilde;o de fitness
	 */
	public Cromossomo getCromossomoDeMaiorFitness() {
		Cromossomo ganhador = cromossomos.get(0);
		for (Cromossomo cromossomo : cromossomos) {
			if (ganhador.getFitness() < cromossomo.getFitness())
				ganhador = cromossomo;
		}
		return ganhador;
	}
}
