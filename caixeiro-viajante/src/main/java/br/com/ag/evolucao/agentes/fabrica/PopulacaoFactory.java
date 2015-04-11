package br.com.ag.evolucao.agentes.fabrica;

import org.apache.log4j.Logger;

import br.com.ag.evolucao.Populacao;
import br.com.ag.evolucao.agentes.Cromossomo;
import br.com.ag.evolucao.agentes.Gene;
import br.com.ag.objetos.Rota;
import br.com.ag.util.EvolucaoUtil;

/**
 * <p>
 * F&aacute;brica da Popula&ccedil;&atilde;o.<br>
 * Use esta classe para conseguir uma instancia consistente de um cromossomo, gene ou popula&ccedil;&atilde;o.
 * </p>
 * 
 * @author rhuan
 * 
 */
public class PopulacaoFactory {

	/**
	 * Log para acesso a erros ou informa&ccedil;&otilde;es
	 */
	private static final Logger log = Logger.getLogger(PopulacaoFactory.class);

	private static Populacao populacao;

	/**
	 * <p>
	 * Cria um novo cromossomo com uma ID v&aacute;lida
	 * </p>
	 * 
	 * @param populacao
	 *            Popula&ccedil;&atilde;o de cromossomos.
	 * @return Retorna o Cromossomo criado.
	 */
	public static Cromossomo getInstanciaDeCromossomo() {
		log.info("Criando um novo Cromossomo...");
		Cromossomo cromossomo = new Cromossomo(EvolucaoUtil.getIdValidaParaCromossomo(populacao.getCromossomos()));
		log.info("Cromossomo criado com ID: " + cromossomo.getId());
		return cromossomo;
	}

	/**
	 * <p>
	 * Calcula uma instancia de um Gene
	 * </p>
	 * 
	 * @param rota
	 *            Rota do Gene
	 * @return Retorna o Gene
	 */
	public static Gene getInstanciaDeGene(Rota rota) {
		log.info("Criando um novo Gene...");
		Gene gene = new Gene(rota);
		log.info("Gene criado com a rota: " + gene.getRota().getCidadeHead().getIdentificador() + " -> "
				+ gene.getRota().getCidadeTail().getIdentificador() + " " + gene.getRota().getDistancia());
		return gene;
	}

	/**
	 * <p>
	 * Calcula uma instancia da popula&ccedil;&atilde;o
	 * </p>
	 * 
	 * @return Retorna o singleton da Popula&ccedil;&atilde;o
	 */
	public static Populacao getPopulacao() {
		if (populacao == null) {
			populacao = new Populacao();
		}
		return populacao;
	}

}
