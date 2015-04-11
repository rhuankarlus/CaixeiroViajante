package br.com.ag.evolucao.control;

import org.apache.log4j.Logger;

import br.com.ag.config.Configuracao;
import br.com.ag.evolucao.Populacao;
import br.com.ag.evolucao.agentes.Cromossomo;
import br.com.ag.evolucao.agentes.Gene;
import br.com.ag.evolucao.agentes.fabrica.PopulacaoFactory;
import br.com.ag.objetos.Cidade;
import br.com.ag.objetos.Grafo;
import br.com.ag.util.EvolucaoUtil;

/**
 * <p>
 * Classe de controle dos agentes.<br>
 * Esta classe valida cada cromossomo como sendo vi&aacute;vel ou n&atilde;o, al&eacute;m de guardar a instancia da
 * popula&ccedil;&atilde;o que est&aacute; em execu&ccedil;&atilde;o
 * </p>
 * 
 * @author rhuan
 * 
 */
public class PopulacaoController {

	private static final Logger log = Logger.getLogger(PopulacaoController.class);

	private static Populacao populacao;

	private Grafo grafo;

	private static Integer numeroMaximoDeCromossomos;
	private static Integer numeroMaximoDeTentativas;

	public PopulacaoController(Grafo grafo) {
		this.grafo = grafo;
	}

	/**
	 * <p>
	 * Cria um novo cromossomo v&aacute;lido.
	 * </p>
	 * 
	 * @param origem
	 *            Cidade de origem
	 * @param destino
	 *            Cidade de destino
	 * @return Se o cromossomo criado for v&aacute;lido, retorna ele, e se n&atilde;o for, retorna <b>null</b>
	 */
	public Cromossomo crieNovaSolucao(Cidade origem, Cidade destino) {
		Cromossomo cromossomo = null;
		if (getPopulacao().getCromossomos().size() < numeroMaximoDeCromossomos) {

			cromossomo = PopulacaoFactory.getInstanciaDeCromossomo();
			Gene gene = PopulacaoFactory.getInstanciaDeGene(EvolucaoUtil.getRotaAleatoria(grafo, origem));
			cromossomo.inserirGene(gene);

			Cidade ultimaCidadeDaRota = gene.getRota().getCidadeTail();

			int tentativas = 0;
			while (!EvolucaoUtil.isCromossomoValido(cromossomo, origem, destino)) {

				tentativas = verificaTentativas(cromossomo.getId(), tentativas);
				if (tentativas == 0) {
					return null;
				}

				log.info("Adicionando novo gene ao cromossomo.");
				Gene g = PopulacaoFactory.getInstanciaDeGene(EvolucaoUtil.getRotaAleatoria(grafo, ultimaCidadeDaRota));
				cromossomo.inserirGene(g);
				ultimaCidadeDaRota = g.getRota().getCidadeTail();
				log.info("Novo gene adicionado.");

			}

		} else {
			log.info("O limite de cromossomos já foi atingido!");
		}
		return cromossomo;
	}

	/**
	 * <p>
	 * Verifica se as tentativas para gerar uma rota consistente foram ultrapassadas.<br>
	 * Caso tenham sido ultrapassadas a thread dorme por um segundo e zera o n&uacute;mero de tentativas.
	 * </p>
	 * 
	 * @param id
	 *            ID do cromossomo que ter&aacute; suas tentativas verificadas.
	 * @param tentativas
	 *            Tentativas para gera&ccedil;&atilde;o deste cromossomo.
	 * @return Retorna o n&uacute;mero de tentativas incrementado
	 */
	private int verificaTentativas(Integer id, int tentativas) {
		if (tentativas > numeroMaximoDeTentativas) {
			tentativas = 0;
			return 0;
		}
		return tentativas + 1;
	}

	/**
	 * <p>
	 * Verifica quais s&atilde;o as configura&ccedil;&otilde;es da popula&ccedil;&atilde;o.
	 * </p>
	 * 
	 * @param caminho
	 *            Caminho para o XML de configura&ccedil;&atilde;o
	 */
	public void carregueConfiguracoesDaPopulacao(String caminho) {

		log.info("Carregando configurações da população...");
		Configuracao configuracao = Configuracao.getInstance(caminho);
		log.info("Configurações da população carregadas!");

		numeroMaximoDeCromossomos = configuracao.getNumeroDeIndividuos();
		numeroMaximoDeTentativas = configuracao.getNumeroDeTentativas();

		log.info("Número máximo de indivíduos e de iterações setado!");
	}

	/**
	 * <p>
	 * Pega a popula&ccedil;&atilde;o da classe {@code PopulacaoFactory}
	 * </p>
	 * 
	 * @return Retorna a popula&ccedil;&atilde;o encontrada
	 */
	public static Populacao getPopulacao() {
		if (populacao == null) {
			populacao = PopulacaoFactory.getPopulacao();
		}

		return populacao;
	}

	/**
	 * <p>
	 * Inicializa uma popula&ccedil;&atilde;o qualquer.
	 * </p>
	 * 
	 * @param origem
	 *            Cidade de origem da popula&ccedil;&atilde;o
	 * @param destino
	 *            Cidade de destino da popula&ccedil;&atilde;o
	 */
	public void inicializeAPopulacao(Cidade origem, Cidade destino) {
		Cromossomo cromossomo = null;
		for (int i = 0; i < numeroMaximoDeCromossomos; i++) {
			do {
				cromossomo = crieNovaSolucao(origem, destino);
			} while (cromossomo == null);

			getPopulacao().insereCromossomo(cromossomo);
		}
	}
	
}
