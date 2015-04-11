package br.com.ag.objetos.fabrica;

import org.apache.log4j.Logger;

import br.com.ag.config.Configuracao;
import br.com.ag.exceptions.InconsistenciaException;
import br.com.ag.objetos.Cidade;
import br.com.ag.objetos.Grafo;

/**
 * <p>
 * Classe respons&aacute;vel por gerar uma instancia consistente da classe {@code Grafo}
 * </p>
 * 
 * @author rhuan
 * 
 */
public class GrafoFactory {

	private static final Logger log = Logger.getLogger(GrafoFactory.class);

	private static Grafo grafo;

	/**
	 * <p>
	 * Instancia um novo Grafo
	 * </p>
	 * 
	 * @param caminho
	 *            Caminho para o arquivo de configura&ccedil;&otilde;es
	 * @return Retorna um Grafo instanciado segundo as configura&ccedil;&otilde;es desejadas
	 */
	public static Grafo getGrafo(String caminho) {
		if (grafo == null) {

			grafo = new Grafo();

			log.info("Instanciando um novo Grafo...");
			Configuracao configuracaoDoGrafo = Configuracao.getInstance(caminho);
			log.info("Configurações carregadas!");

			log.info("Criando Cidades e Rotas...");
			criarCidadesERotas(configuracaoDoGrafo);
			log.info("Rotas e cidades criadas!");

		}

		log.info("Retornando o Grafo...");
		return grafo;
	}

	/**
	 * <p>
	 * Cria as rotas e as cidades de uma s&oacute; vez.F
	 * </p>
	 * 
	 * @param configuracaoDoGrafo
	 *            Configura&ccedil;&otilde;es pr&eacute;-carregadas para o Grafo
	 */
	private static void criarCidadesERotas(Configuracao configuracaoDoGrafo) {
		for (String rota : configuracaoDoGrafo.getRotas()) {

			String[] caminho = rota.split(configuracaoDoGrafo.getRotasPattern());
			Cidade cidadeHead = new Cidade(caminho[0]);
			Cidade cidadeTail = new Cidade(caminho[2]);
			Double distancia = Double.parseDouble(caminho[1]);

			grafo.inserirCidade(cidadeHead);
			grafo.inserirCidade(cidadeTail);
			
			try {
				grafo.inserirRota(cidadeHead, cidadeTail, distancia);
			} catch (InconsistenciaException e) {
				log.error("Erro de inconsistencia.", e);
				grafo = null;
				break;
			}
			
			log.info("Nova rota criada: " + cidadeHead.getIdentificador() + " -> " + cidadeTail.getIdentificador()
					+ " " + distancia);

		}
	}

	
	
}
