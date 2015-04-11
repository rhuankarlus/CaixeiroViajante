package br.com.ag.objetos;

import java.util.HashSet;
import java.util.Set;

import br.com.ag.exceptions.InconsistenciaException;

/**
 * <p>
 * Classe respons&aacute;vel por armazenas todo o grafo do problema.<br>
 * Aqui inserimos todas as cidades e todas as rotas.
 * </p>
 * 
 * @author rhuan
 * 
 */
public class Grafo {

	/**
	 * <p>
	 * Mensagem para erro de inconsistencia de rotas.
	 * <p>
	 */
	private static String inconsistentMessage = "";

	private Set<Cidade> cidades;
	private Set<Rota> rotas;

	public Grafo() {
		this.cidades = new HashSet<Cidade>();
		this.rotas = new HashSet<Rota>();
	}

	public Grafo(Set<Cidade> cidades, Set<Rota> rotas) {
		this.cidades = cidades;
		this.rotas = rotas;
	}

	public Set<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(Set<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Set<Rota> getRotas() {
		return rotas;
	}

	public void setRotas(Set<Rota> rotas) {
		this.rotas = rotas;
	}

	/**
	 * <p>
	 * Insere uma nova cidade no grafo.
	 * </p>
	 * 
	 * @param cidade
	 *            Cidade a ser inserida.
	 */
	public void inserirCidade(Cidade cidade) {
		cidades.add(cidade);
	}

	/**
	 * <p>
	 * Insere uma nova rota no grafo.
	 * </p>
	 * 
	 * @param cidadeHead
	 *            Primeira cidade da rota
	 * @param cidadeTail
	 *            Segunda cidade da rota
	 * @param distancia
	 *            Distancia entre as rotas
	 * @throws InconsistenciaException
	 *             Caso alguma rota seja inserida duas vezes com distancia diferente teremos um problema de
	 *             inconsistencia
	 */
	public void inserirRota(Cidade cidadeHead, Cidade cidadeTail, Double distancia) throws InconsistenciaException {

		if (isConsistente(cidadeHead, cidadeTail, distancia)) {
			if (!isExistente(cidadeHead, cidadeTail, distancia)) {

				Rota primeiraRota = new Rota(cidadeHead, cidadeTail, distancia);
				Rota segundaRota = new Rota(cidadeTail, cidadeHead, distancia);
				rotas.add(primeiraRota);
				rotas.add(segundaRota);

			}
		} else {

			throw new InconsistenciaException(inconsistentMessage);

		}
	}

	/**
	 * <p>
	 * Verifica se a rota j&aacute; existe no grafo
	 * </p>
	 * 
	 * @param cidadeHead
	 *            Cidade de origem
	 * @param cidadeTail
	 *            Cidade de destino
	 * @param distancia
	 *            Distancia entre as cidades
	 * @return Se a rota j&aacute; tiver sido inserida no grafo retorna <b>true</b>, em caso contr&aacute;rio retorna
	 *         <b>false</b>
	 */
	public boolean isExistente(Cidade cidadeHead, Cidade cidadeTail, Double distancia) {

		for (Rota rota : rotas) {
			if ((rota.getCidadeHead().equals(cidadeHead) && rota.getCidadeTail().equals(cidadeTail))
					|| (rota.getCidadeHead().equals(cidadeTail) && rota.getCidadeTail().equals(cidadeHead))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * <p>
	 * Verifica se a rota &eacute; consistente.
	 * </p>
	 * 
	 * @param cidadeHead
	 *            Cidade de origem
	 * @param cidadeTail
	 *            Cidade de destino
	 * @param distancia
	 *            Distancia entre ambas
	 * @return Se a rota j&aacute; tiver sido inserida anteriormente com uma distancia diferente da atual
	 *         retornar&aacute; <b>false</b>, caso contr&aacute;rio retornar&aacute; <b>true</b>
	 */
	private boolean isConsistente(Cidade cidadeHead, Cidade cidadeTail, Double distancia) {
		for (Rota rota : rotas) {
			if ((rota.getCidadeHead().equals(cidadeHead) && rota.getCidadeTail().equals(cidadeTail))
					|| (rota.getCidadeHead().equals(cidadeTail) && rota.getCidadeTail().equals(cidadeHead))) {
				if (!rota.getDistancia().equals(distancia)) {
					inconsistentMessage = "A rota (" + cidadeHead.getIdentificador() + " -> "
							+ cidadeTail.getIdentificador() + " " + distancia
							+ ") ja havia sido inserida com os seguintes valores: "
							+ rota.getCidadeHead().getIdentificador() + " -> "
							+ rota.getCidadeTail().getIdentificador() + " " + rota.getDistancia();
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * <p>
	 * Calcula uma Rota dada uma cidade origem e uma cidade destino
	 * </p>
	 * 
	 * @param origem
	 *            Cidade de origem da Rota desejada
	 * @param destino
	 *            Cidade de destino da Rota desejada
	 * @return Retorna a Rota encontrada ou <b>null</b> caso encontre nada
	 */
	public Rota getRota(Cidade origem, Cidade destino) {
		for (Rota rota : getRotas()) {
			if (rota.getCidadeHead().equals(origem) && rota.getCidadeTail().equals(destino))
				return rota;
		}
		return null;
	}
}
