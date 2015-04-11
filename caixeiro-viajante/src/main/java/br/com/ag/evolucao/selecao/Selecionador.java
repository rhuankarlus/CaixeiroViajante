package br.com.ag.evolucao.selecao;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import br.com.ag.evolucao.agentes.Cromossomo;
import br.com.ag.util.EvolucaoUtil;

/**
 * <p>
 * Classe respons&aacute;vel por procurar uma lista de elementos aleatoriamente na {@code Roleta} e retorn&aacute;-la.
 * </p>
 * 
 * @author rhuan
 * 
 */
public class Selecionador {

	/**
	 * log
	 */
	private static final Logger log = Logger.getLogger(Selecionador.class);
	
	/**
	 * Elementos selecionados.
	 */
	private static Set<Cromossomo> cromossomosEscolhidos = new HashSet<Cromossomo>();

	/**
	 * <p>
	 * Calcula um elemento aleat&oacute;rio da roleta.
	 * </p>
	 * 
	 * @return Retorna o elemento.
	 */
	public Cromossomo getElementoAleatorio() {
		Cromossomo cromossomo = null;
		do {
			cromossomo = EvolucaoUtil.getCromossomoRandomico(Roleta.getElementosEmIntervalos(),
					Roleta.getLimiteSuperiorMaximo());
		} while (cromossomo == null);
		return cromossomo;
	}

	/**
	 * <p>
	 * Calcula uma lista de elementos randomicos.
	 * </p>
	 * 
	 * @param quantidadeMaxima
	 *            Quantidade m&aacute;xima de elementos que se quer.
	 * @return Retorna a lista.
	 */
	public Set<Cromossomo> getElementosAleatorios(Integer quantidadeMaxima) {
		log.info("Inicializando a busca por uma lista de Cromossomos aleatória.");
		for (int i = 0; i < quantidadeMaxima; i++) {
			cromossomosEscolhidos.add(getElementoAleatorio());
		}
		log.info("Busca finalizada!");
		return cromossomosEscolhidos;
	}

}
