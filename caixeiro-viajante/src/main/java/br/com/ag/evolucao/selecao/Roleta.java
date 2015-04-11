package br.com.ag.evolucao.selecao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.ag.evolucao.agentes.Cromossomo;

/**
 * <p>
 * Classe respons&aacute;vel por armazenar nossa roleta.<br>
 * Essa classe usa um Map de elementos onde a chave &eacute; o cromossomo e o valor &eacute; seu fitness
 * </p>
 * 
 * @author rhuan
 * 
 */
public class Roleta {

	/**
	 * log...
	 */
	private static final Logger log = Logger.getLogger(Roleta.class);

	/**
	 * Mapa de elementos tendo como chave o <b>Cromossomo</b> e tendo como valor o seu percentual.
	 */
	private static Map<Cromossomo, Double> elementos = new HashMap<Cromossomo, Double>();

	/**
	 * Intervalos entre os elementos para posterior sele&ccedil;&atilde;o dos mesmos.
	 */
	private static List<ElementoRandomizador> elementosEmIntervalos = new ArrayList<ElementoRandomizador>();

	/**
	 * Limite superior m&aacute;ximo da roleta
	 */
	private static Double limiteSuperiorMaximo;

	/**
	 * Total de fitness dos elementos
	 */
	private static Double fitnessTotal;
	
	/**
	 * <p>
	 * Insere um novo cromossomo na roleta e depois recalcula os percentuais de todos os cromossomos baseando-se neste.
	 * </p>
	 * 
	 * @param cromossomo
	 *            Cromossomo que ser&aacute; inserido.
	 */
	public void insiraNovoElemento(Cromossomo cromossomo) {
		getElementos().put(cromossomo, 0.d);
		recalculePercentuais();
	}

	/**
	 * <p>
	 * Calcula o intervalo para cada elmento (Cromossomo) da roleta.
	 * </p>
	 */
	public void calculeIntervalo() {
		log.info("Iniciando o cálculo de intervalos...");
		Double limiteInferior = 0.d;
		for (Cromossomo cromossomo : elementos.keySet()) {
			getElementosEmIntervalos().add(
					new ElementoRandomizador(cromossomo, limiteInferior, limiteInferior + elementos.get(cromossomo)));
			log.info("O limite do elemento " + cromossomo.getId() + " será " + limiteInferior + " - "
					+ limiteInferior + elementos.get(cromossomo));
			limiteInferior += elementos.get(cromossomo);
		}
		limiteSuperiorMaximo = limiteInferior;
		log.info("Todos os intervalos da roleta foram calculados.");
	}

	/**
	 * <p>
	 * Insere todos os cromossomos de uma lista na roleta calculando seus percentuais.
	 * </p>
	 * 
	 * @param cromossomos
	 *            Lista de Cromossomos que devem ser inseridos.
	 */
	public void insiraOsElementosNaRoleta(List<Cromossomo> cromossomos) {
		for (Cromossomo cromossomo : cromossomos) {
			this.insiraNovoElemento(cromossomo);
		}
	}

	/**
	 * <p>
	 * Recalcula o percentual de cada elemento na roleta.
	 * </p>
	 */
	private void recalculePercentuais() {
		fitnessTotal = 0.d;
		for (Cromossomo cromossomo : getElementos().keySet()) {
			fitnessTotal += cromossomo.getFitness();
		}
		for (Cromossomo cromossomo : getElementos().keySet()) {
			getElementos().put(cromossomo, cromossomo.getFitness() / fitnessTotal);
		}
	}

	/**
	 * <p>
	 * Limpa completamente os elementos da roleta.
	 * </p>
	 */
	public void limparRoleta() {
		log.info("Limpando a Roleta...");
		elementos.clear();
		elementosEmIntervalos.clear();
		log.info("Roleta limpa!");
	}

	public static Map<Cromossomo, Double> getElementos() {
		return elementos;
	}

	public static List<ElementoRandomizador> getElementosEmIntervalos() {
		return elementosEmIntervalos;
	}

	public static Double getLimiteSuperiorMaximo() {
		return limiteSuperiorMaximo;
	}
	
}
