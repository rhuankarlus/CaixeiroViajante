package br.com.ag.evolucao.control;

import java.util.Set;

import org.apache.log4j.Logger;

import br.com.ag.config.Configuracao;
import br.com.ag.evolucao.Populacao;
import br.com.ag.evolucao.agentes.Cromossomo;
import br.com.ag.evolucao.agentes.Gene;
import br.com.ag.evolucao.selecao.Otimizador;
import br.com.ag.evolucao.selecao.Roleta;
import br.com.ag.evolucao.selecao.Selecionador;
import br.com.ag.objetos.Cidade;
import br.com.ag.objetos.Grafo;
import br.com.ag.util.EvolucaoUtil;

/**
 * <p>
 * Controlador das evolu&ccedil;&otilde;es do algoritmo.
 * </p>
 * 
 * @author rhuan
 * 
 */
public class EvolucaoController {

	private static final Logger log = Logger.getLogger(EvolucaoController.class);

	private static Integer numeroMaximoDeIteracoes;
	private static Integer numeroMaximoDeTentativas;

	private static Double fitnessTotal;

	/**
	 * <p>
	 * Carrega as configura&ccedil;&otilde;es necess&aacute;rias para a evolu&ccedil;&atilde;o dos elementos.
	 * </p>
	 * 
	 * @param caminho
	 *            Caminho para o arquivo de configura&ccedil;&atilde;o
	 */
	public void carregarConfiguracoes(String caminho) {
		log.info("Carregando configurações de evolução...");
		Configuracao config = Configuracao.getInstance(caminho);
		log.info("Configurações de evolução carregadas!");

		numeroMaximoDeIteracoes = config.getNumeroDeIteracoes();
		numeroMaximoDeTentativas = config.getNumeroDeTentativas();
	}

	/**
	 * <p>
	 * Evolui a Popula&ccedil;&atilde;o quantas vezes o usu&aacute;rio desejar de acordo com o arquivo de
	 * configura&ccedil;&atilde;o.
	 * </p>
	 * 
	 * @param populacao
	 *            Popula&ccedil;&atilde;o que ter&aacute; seus elementos evolu&iacute;dos
	 */
	public void evoluirPopulacao(Populacao populacao, Grafo grafo, Cidade origem, Cidade destino) {
		Integer totalDeEvolucoes = 0;
		while (totalDeEvolucoes < numeroMaximoDeIteracoes) {
			avaliarSolucoes(populacao);
			enviarElementosParaRoleta(populacao);
			Set<Cromossomo> cromossomos = selecionarElementos(populacao);
			cromossomos.add(populacao.getCromossomos().get(0));
			otimizarElementos(cromossomos, grafo, origem, destino);
			totalDeEvolucoes++;
		}
	}

	/**
	 * <p>
	 * Chama o {@code Otimizador} para otimizar os cromossomos pr&eacute;-selecionados.
	 * </p>
	 * 
	 * @param cromossomos
	 *            Cromossomos que foram pr&eacute;-selecionados.
	 * @param destino
	 *            Destino do caminho
	 * @param origem
	 *            Origem do caminho
	 * @param grafo
	 *            Grafo com todos os caminhos
	 */
	private void otimizarElementos(Set<Cromossomo> cromossomos, Grafo grafo, Cidade origem, Cidade destino) {
		log.info("Inicializando otimização dos elementos selecionados da roleta...");
		Otimizador otimizador = new Otimizador(grafo, origem, destino);
		otimizador.otimizarElementos(cromossomos, numeroMaximoDeTentativas, fitnessTotal);
		log.info("Elementos otimizados!");
	}

	/**
	 * <p>
	 * Usa o {@code Selecionador} para escolher randomicamente os elementos que sofrer&atilde;o muta&ccedil;&atilde;o
	 * </p>
	 * 
	 * @param populacao
	 *            Popula&ccedil;&atilde;o de cromossomos
	 * @return Retorna o conjunto de elementos que ser&atilde; alterados geneticamente
	 */
	private Set<Cromossomo> selecionarElementos(Populacao populacao) {
		log.info("Inicializando a seleção de cromossomos na roleta...");
		Selecionador selecionador = new Selecionador();
		return selecionador.getElementosAleatorios(PopulacaoController.getPopulacao().getCromossomos().size() / 2);
	}

	/**
	 * <p>
	 * Envia todos os elementos da Popula&ccedil;&atilde;o para a Roleta.<br>
	 * Depois calcula cada intervalo para todos os elementos da Roleta.
	 * </p>
	 * 
	 * @param populacao
	 *            Popula&ccedil;&atilde;o de Cromossomos.
	 */
	private void enviarElementosParaRoleta(Populacao populacao) {
		log.info("Inicializando Roleta...");
		Roleta roleta = new Roleta();
		log.info("Limpando Roleta...");
		roleta.limparRoleta();
		log.info("Inserindo todos os elementos na Roleta...");
		roleta.insiraOsElementosNaRoleta(populacao.getCromossomos());
		log.info("Calculando os intervalos para cada elemento da Roleta...");
		roleta.calculeIntervalo();
	}

	/**
	 * <p>
	 * Avalia cada elemento da Popula&ccedil;&atilde;o.<br>
	 * Aqui cada elemento recebe seu fitness.
	 * </p>
	 * 
	 * @param populacao
	 *            Popula&ccedil;&atilde;o que ser&aacute; avaliada.
	 */
	private void avaliarSolucoes(Populacao populacao) {
		log.info("Avaliando o FITNESS de cada cromossomos...");
		fitnessTotal = 0.d;
		for (Cromossomo cromossomo : populacao.getCromossomos()) {
			for (Gene gene : cromossomo.getGenes()) {
				fitnessTotal += gene.getRota().getDistancia();
			}
		}
		log.info("Fitness total calculado: " + fitnessTotal);
		for (Cromossomo cromossomo : populacao.getCromossomos()) {
			Double fitness = EvolucaoUtil.avaliaCromossomo(fitnessTotal, cromossomo);
			cromossomo.setFitness(fitness);
			log.info("Cromossomo " + cromossomo.getId() + ": " + fitness);
		}
		log.info("Todos os fitness foram calculados!!!");
	}

}
