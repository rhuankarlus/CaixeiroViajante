package br.com.ag.evolucao.agentes;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Classe respons&aacute;vel por trazer uma so&ccedil;&atilde;o ao problema.
 * </p>
 * 
 * @author rhuan
 * 
 */
public class Cromossomo {

	/**
	 * <p>
	 * Identificador da solu&ccedil;&atilde;o
	 * </p>
	 */
	private Integer id;

	/**
	 * <p>
	 * Genes para esta solu&ccedil;&atilde;o
	 * </p>
	 */
	private List<Gene> genes;

	/**
	 * <p>
	 * Nota avaliativa para este Cromossomo
	 * </p>
	 */
	private Double fitness;

	/**
	 * <p>
	 * Construtor j&aacute; insere o ID
	 * </p>
	 * 
	 * @param id
	 *            ID para o cromossomo.
	 */
	public Cromossomo(Integer id) {
		this.id = id;
		genes = new ArrayList<Gene>();
	}

	/**
	 * <p>
	 * Insere um novo {@code Gene} no {@code Cromossomo}
	 * </p>
	 * 
	 * @param gene
	 *            Gene com a Rota que deve ser inserida.
	 */
	public void inserirGene(Gene gene) {
		this.genes.add(gene);
	}

	public List<Gene> getGenes() {
		return genes;
	}

	public Double getFitness() {
		return fitness;
	}

	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}

	public Integer getId() {
		return id;
	}
}
