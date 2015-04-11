package br.com.ag.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import br.com.ag.evolucao.agentes.Cromossomo;
import br.com.ag.evolucao.agentes.Gene;
import br.com.ag.evolucao.selecao.ElementoRandomizador;
import br.com.ag.objetos.Cidade;
import br.com.ag.objetos.Grafo;
import br.com.ag.objetos.Rota;

public class EvolucaoUtil {

	private static final Logger log = Logger.getLogger(EvolucaoUtil.class);

	/**
	 * <p>
	 * Calcula uma ID para um cromossomo que ser&aacute; criado
	 * </p>
	 * 
	 * @param cromossomosCriados
	 *            Lista de cromossomos j&aacute; existentes na aplica&ccedil;&atilde;o
	 * @return Retorna o ID gerado.
	 */
	public static Integer getIdValidaParaCromossomo(List<Cromossomo> cromossomosCriados) {
		Integer id = 1;
		for (Cromossomo cromossomo : cromossomosCriados) {
			if (cromossomo.getId().equals(id)) {
				id++;
			}
		}
		return id;
	}

	/**
	 * <p>
	 * Calcula uma rota aleat&oacute;ria a partir da cidade Origem usando o m&eacute;todo
	 * {@code Collections#shuffle(List)}
	 * </p>
	 * 
	 * @param grafo
	 *            Grafo com todas cidades e rotas poss&iacute;veis
	 * @param origem
	 *            Cidade a partir da qual se quer sair.
	 * @return Retorna uma rota aleat&oacute;ria.
	 */
	public static Rota getRotaAleatoria(Grafo grafo, Cidade origem) {
		List<Rota> possiveisRotas = getPossiveisRotas(grafo, origem);
		Collections.shuffle(possiveisRotas);
		return possiveisRotas.get(0);
	}

	/**
	 * <p>
	 * Calcula as poss&iacute;veis rotas para uma cidade centrada na Origem.
	 * </p>
	 * 
	 * @param grafo
	 *            Grafo com todas as cidades e rotas
	 * @param origem
	 *            Cidade de Origem a partir de onde se quer sair
	 * @return Retorna a lista de poss&iacute;veis rotas a partir daquele ponto de origem
	 */
	private static List<Rota> getPossiveisRotas(Grafo grafo, Cidade origem) {
		List<Rota> possiveisRotas = new ArrayList<Rota>();
		for (Rota rota : grafo.getRotas()) {
			if (rota.getCidadeHead().equals(origem)) {
				possiveisRotas.add(rota);
			}
		}
		return possiveisRotas;
	}

	/**
	 * <p>
	 * Calcula um elemento qualquer de uma lista de Elementos Randomicos
	 * 
	 * @param elementos
	 *            Elementos de onde se tirar&aacute; um elemento qualquer.
	 * @param valorMaximo
	 *            Valor m&aacute;ximo a ser calculado
	 * @return Retorna um elemento randomico. Caso n&atilde;o encontre um elemento no intervalo retornar&aacute; o
	 *         primeiro elemento da lista de elementos.
	 */
	public static Cromossomo getCromossomoRandomico(List<ElementoRandomizador> elementos, Double valorMaximo) {
		Random gerador = new Random();
		Double intervalo = gerador.nextDouble() * valorMaximo;
		for (ElementoRandomizador elemento : elementos) {
			if (elemento.getLimiteInferior() < intervalo && elemento.getLimiteSuperior() >= intervalo)
				return elemento.getCromossomo();
		}
		return elementos.get(0).getCromossomo();
	}

	/**
	 * <p>
	 * Valida um cromossomo.<br>
	 * <b>ATEN&Ccedil;&Atilde:O:</b> Antes de inserir um novo cromossomo este m&eacute;todo deve ser chamado.
	 * </p>
	 * 
	 * @param cromossomo
	 *            Cromossomo que ser&aacute; validado
	 * @param origem
	 *            Origem escolhida pelo usu&aacute;rio
	 * @param destino
	 *            Destino escolhido pelo usu&aacute;rio
	 * @return Retorna <b>true</b> se o cromossomo for v&aacute;lido ou <b>false</b> em caso contr&aacute;rio
	 */
	public static boolean isCromossomoValido(Cromossomo cromossomo, Cidade origem, Cidade destino) {
		List<String> cidadesPorOndePassou = new ArrayList<String>();

		for (Gene gene : cromossomo.getGenes()) {
			cidadesPorOndePassou.add(gene.getRota().getCidadeHead().getIdentificador());
			cidadesPorOndePassou.add(gene.getRota().getCidadeTail().getIdentificador());
		}

		if (passouPorMaisDeUmaCidade(cidadesPorOndePassou)) {
			log.info("INVÁLIDO! O cromossomo " + cromossomo.getId() + " passou por uma mesma cidade duas vezes.");
			return false;
		}

		if (!saiuDaOrigemEChegouNoDestino(cidadesPorOndePassou, origem, destino)) {
			log.info("INVÁLIDO! O cromossomo " + cromossomo.getId() + " não percorreu a rota desejada.");
			return false;
		}

		return true;
	}

	/**
	 * <p>
	 * Verifica se o cromossomo saiu da origem e atingiu o destino.
	 * </p>
	 * 
	 * @param cidadesPorOndePassou
	 *            Lista de Cidades por onde o cromossomo passou
	 * @param origem
	 *            Origem escolhida pelo usu&aacute;rio
	 * @param destino
	 *            Destino escolhido pelo usu&aacute;rio
	 * @return Retorna <b>true</b> se o cromossomo formou a rota corretamente e <b>false</b> em caso contr&aacute;rio
	 */
	private static boolean saiuDaOrigemEChegouNoDestino(List<String> cidadesPorOndePassou, Cidade origem, Cidade destino) {
		return ((origem.getIdentificador().equals(cidadesPorOndePassou.get(0))) && (destino.getIdentificador()
				.equals(cidadesPorOndePassou.get(cidadesPorOndePassou.size() - 1))));
	}

	/**
	 * <p>
	 * Verifica se o cromossomo apresentou uma rota completa passando por uma cidade mais de uma vez.
	 * </p>
	 * 
	 * @param cidadesPorOndePassou
	 *            Lista de Cidades por onde o cromossomo j&aacute; passou
	 * @return Retorna <b>true</b> se o cromossomo tiver passado duas vezes na mesma cidade e <b>false</b> em caso
	 *         contr&aacute;rio
	 */
	private static boolean passouPorMaisDeUmaCidade(List<String> cidadesPorOndePassou) {
		List<String> cidadesDeOndeSaiu = new ArrayList<String>();

		String cidadeAnterior = cidadesPorOndePassou.get(0);
		String cidadeAtual = "";

		for (int i = 1; i < cidadesPorOndePassou.size(); i++) {
			cidadeAtual = cidadesPorOndePassou.get(i);
			if (!cidadeAtual.equals(cidadeAnterior)) {
				if (cidadesDeOndeSaiu.contains(cidadeAtual)) {
					return true;
				}
				cidadesDeOndeSaiu.add(cidadeAnterior);
				cidadeAnterior = cidadeAtual;
			}
		}

		return false;
	}

	/**
	 * <p>
	 * Fun&ccedil;&atilde;o que retorna a pontua&ccedil;&atilde;o de um cromossomo dada uma pontua&ccedil;&atilde;o
	 * total.
	 * </p>
	 * 
	 * @param fitnessTotal
	 *            Pontua&ccedil;&atilde;o total
	 * @param cromossomo
	 *            Cromossomo que ser&aacute; avaliado
	 * @return Retorna a avalia&ccedil;&atilde;o do cromossomo
	 */
	public static Double avaliaCromossomo(Double fitnessTotal, Cromossomo cromossomo) {
		Double fitness = 0.d;
		for (Gene gene : cromossomo.getGenes()) {
			fitness += gene.getRota().getDistancia();
		}
		fitness = fitnessTotal / fitness;
		return fitness;
	}

	/**
	 * <p>
	 * Clona um Cromossomo
	 * </p>
	 * 
	 * @param cromossomo
	 *            Cromossomo que ser&aacute; clonado
	 * @return Retorna o Ccromossomo clonado
	 */
	public static Cromossomo getCromossomoClone(Cromossomo cromossomo) {
		Cromossomo clone = new Cromossomo(0);
		for (Gene gene : cromossomo.getGenes()) {
			clone.inserirGene(new Gene(gene.getRota()));
		}
		return clone;
	}

	/**
	 * <p>
	 * Verifica se houve uma descontinuidade na rota do cromossomo.
	 * </p>
	 * 
	 * @param cromossomo
	 *            Cromossomo que ter&aacute; a rota validada.
	 * @return Retorna <b>true</b> se a rota do cromossomo tiver descontinuidade e <b>false</b> em caso
	 *         contr&aacute;rio.
	 */
	public static boolean houveDescontinuidade(Cromossomo cromossomo) {
		Rota rotaAnterior = cromossomo.getGenes().get(0).getRota();
		Rota rotaAtual = null;

		for (Gene gene : cromossomo.getGenes()) {
			rotaAtual = gene.getRota();
			if (!rotaAtual.equals(rotaAnterior)) {
				if (!rotaAnterior.getCidadeTail().getIdentificador()
						.equals(rotaAtual.getCidadeHead().getIdentificador())) {
					log.info("INVÁLIDO! O Cromossomo " + cromossomo.getId() + " teve uma descontinuidade na rua rota!");
					return true;
				}
			}
			rotaAnterior = rotaAtual;
		}

		return false;
	}

}
