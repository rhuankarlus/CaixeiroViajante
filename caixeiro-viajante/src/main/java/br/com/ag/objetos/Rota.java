package br.com.ag.objetos;

/**
 * <p>
 * Rota de cada cidade para qualquer outra<br>
 * Basicamente a Rota possui uma cidade numa ponta e outra cidade na outra ponta e uma distancia igando ambas.
 * </p>
 * 
 * @author rhuan
 * 
 */
public class Rota {

	private Cidade cidadeHead;
	private Cidade cidadeTail;

	private Double distancia;

	public Rota(Cidade cidadeHead, Cidade cidadeTail, Double distancia) {
		this.cidadeHead = cidadeHead;
		this.cidadeTail = cidadeTail;
		this.distancia = distancia;
	}

	public Cidade getCidadeHead() {
		return cidadeHead;
	}

	public Cidade getCidadeTail() {
		return cidadeTail;
	}

	public Double getDistancia() {
		return distancia;
	}

}
