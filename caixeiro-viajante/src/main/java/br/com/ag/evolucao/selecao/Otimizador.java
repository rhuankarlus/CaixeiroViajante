package br.com.ag.evolucao.selecao;

import java.util.Set;

import org.apache.log4j.Logger;

import br.com.ag.evolucao.agentes.Cromossomo;
import br.com.ag.evolucao.agentes.Gene;
import br.com.ag.evolucao.agentes.fabrica.PopulacaoFactory;
import br.com.ag.objetos.Cidade;
import br.com.ag.objetos.Grafo;
import br.com.ag.objetos.Rota;
import br.com.ag.util.EvolucaoUtil;

/**
 * <p>
 * Classe que aplica a muta&ccedil;&atilde;o e otimiza os elementos (Cromossomos)
 * </p>
 * 
 * @author rhuan
 * 
 */
public class Otimizador {

	private static final Logger log = Logger.getLogger(Otimizador.class);

	private Grafo grafo;
	private Cidade origem;
	private Cidade destino;

	public Otimizador(Grafo grafo, Cidade origem, Cidade destino) {
		this.grafo = grafo;
		this.origem = origem;
		this.destino = destino;
	}

	/**
	 * <p>
	 * Otimiza todos os cromossomos da lista em quest&atilde;o
	 * </p>
	 * 
	 * @param cromossomos
	 *            Cromossomos que ser&atilde;o otimizados.
	 */
	public void otimizarElementos(Set<Cromossomo> cromossomos, Integer maximoDeTentativas, Double fitnessTotal) {
		log.info("Iniciando otimização da lista de cromossomos...");
		for (Cromossomo cromossomo : cromossomos) {
			otimizarCromossomo(cromossomo, maximoDeTentativas, fitnessTotal);
		}
		log.info("Geração otimizada!");
	}

	/**
	 * <p>
	 * Otimiza um cromossomo.<br>
	 * Se ao final da muta&ccedil;&atilde;o o pai tiver menor <b>fitness</b> que o filho, ent&atilde;o o pai ser&aacute;
	 * subtituido. Em caso contr&aacute;rio o pai se manter&aacute; na popula&ccedil;&atilde;o.
	 * </p>
	 * 
	 * @param cromossomo
	 *            Cromossomo que sofrer&aacute; a muta&ccedil;&atilde;o
	 * @param maximoDeTentativas
	 *            N&uacute;mero m&aacute;ximo de tentativas
	 * @param fitnessTotal
	 *            Total do fitness calculado para os elementos da popula&ccedil;&atilde;o
	 */
	private void otimizarCromossomo(Cromossomo cromossomo, Integer maximoDeTentativas, Double fitnessTotal) {
		log.info("Otimizando o elemento " + cromossomo.getId());

		Integer tentativas = 0;
		Cromossomo mutacao = null;
		boolean isCromossomoValido = false;

		if (cromossomo.getGenes().size() > 2) {
			do {

				log.info("Criando o Cromossomo clone...");
				mutacao = EvolucaoUtil.getCromossomoClone(cromossomo);
				log.info("Cromossomo clone criado!");

				log.info("Aplicando mutação...");
				mutacao = aplicarMutacao(mutacao);
				log.info("Mutação aplicada!");

				isCromossomoValido = EvolucaoUtil.isCromossomoValido(mutacao, origem, destino)
						&& !EvolucaoUtil.houveDescontinuidade(mutacao);
				tentativas++;
			} while ((tentativas < maximoDeTentativas) && (!isCromossomoValido));
		} else {
			log.warn("O elemento não pode ser alterado porque possui apenas " + cromossomo.getGenes().size()
					+ " gene(s).");
		}

		if (isCromossomoValido) {
			log.info("A mutação gerada foi válida!");
			Double fitness = EvolucaoUtil.avaliaCromossomo(fitnessTotal, mutacao);
			mutacao.setFitness(fitness);
			log.info("Pontuação do PAI: " + cromossomo.getFitness());
			log.info("Pontuação do MUTAÇÃO: " + mutacao.getFitness());
			if (cromossomo.getFitness() < mutacao.getFitness()) {
				cromossomo.getGenes().removeAll(cromossomo.getGenes());
				for (Gene gene : mutacao.getGenes()) {
					cromossomo.getGenes().add(gene);
				}
				cromossomo.setFitness(mutacao.getFitness());
				log.info("O PAI foi substituído pelo FILHO.");
			} else {
				log.info("O PAI se manteve na população.");
			}
		}

	}

	/**
	 * <p>
	 * Gera uma muta&ccedil;&atilde;o de um cromossomo.
	 * </p>
	 * 
	 * @param cromossomo
	 *            Cromossomo ao qual ser&aacute; aplicada a muta&ccedil;&atilde;o
	 * @return Retorna o elemento mutante
	 */
	private Cromossomo aplicarMutacao(Cromossomo cromossomo) {
		Cidade origemDaRota = cromossomo.getGenes().get(1).getRota().getCidadeHead();
		Cidade destinoDaRota = cromossomo.getGenes().get(1).getRota().getCidadeTail();

		Gene primeiroGene = PopulacaoFactory.getInstanciaDeGene(EvolucaoUtil.getRotaAleatoria(grafo, origemDaRota));
		Rota rota = grafo.getRota(primeiroGene.getRota().getCidadeTail(), destinoDaRota);
		if (rota == null) {
			log.warn("A rota gerada após a mutação foi inválida.");
			return cromossomo;
		}

		Gene segundoGene = PopulacaoFactory.getInstanciaDeGene(rota);

		cromossomo.getGenes().remove(1);
		cromossomo.getGenes().add(1, primeiroGene);
		cromossomo.getGenes().add(2, segundoGene);

		return cromossomo;
	}
}
