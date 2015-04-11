package br.com.ag.config;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import br.com.ag.util.FileUtil;

/**
 * <p>
 * Classe respons&aacute;vel por ler o arquivo de configura&ccedil;&otilde;es
 * </p>
 * 
 * @author rhuan
 * 
 */
@SuppressWarnings("unchecked")
public class Configuracao {

	private static final Logger log = Logger.getLogger(Configuracao.class);

	/**
	 * <p>
	 * Total de itera&ccedil;&otilde;es que ser&atilde;o realizadas.
	 * </p>
	 */
	private Integer numeroDeIteracoes;

	/**
	 * <p>
	 * Total de indiv&iacute;duos em cada itera&ccedil;&atilde;o
	 * </p>
	 */
	private Integer numeroDeIndividuos;

	/**
	 * <p>
	 * N&uacute;mero m&aacute;ximo de tentativas para gerar um cromossomo consistente.
	 * </p>
	 */
	private Integer numeroDeTentativas;

	/**
	 * <p>
	 * Lista de cidades
	 * </p>
	 */
	private List<String> cidades = new ArrayList<String>();

	/**
	 * <p>
	 * Lista de rotas
	 * </p>
	 */
	private List<String> rotas = new ArrayList<String>();

	/**
	 * <p>
	 * Pattern para armazenamento de cada rota.
	 * </p>
	 * <p>
	 * <b>Exemplo:</b><br>
	 * Na rota da cidade A para a cidade B com a distancia 150 entre ambas teremos a rota <b>A_150_B</b> ent&atilde;o o
	 * pattern ser&aacute; o caractere underscore <b>"_"</b>
	 * </p>
	 */
	private final String rotasPattern = "_";

	// singleton
	private static Configuracao configuracao;

	/**
	 * Singleton
	 */
	private Configuracao() {
	}

	/**
	 * <p>
	 * Calcula as configura&ccedil;&otilde;es segundo o caminho passado ou usa o arquivo de configura&ccedil;&otilde;es
	 * padr&atilde;o
	 * </p>
	 * 
	 * @param caminho
	 *            Caminho para o arquivo de configura&ccedil;&atilde;o
	 * @return Retorna o leitor de configura&ccedil;&otilde;es com suas propriedades setadas.
	 */
	public static Configuracao getInstance(String caminho) {
		if (configuracao == null) {

			configuracao = new Configuracao();
			Element xml = null;

			xml = getXmlDeConfiguracao(caminho, xml);
			if (xml == null)
				return null;

			try {
				log.info("Lendo configurações...");
				Element config = xml.element(ConfigTags.CONFIG.tag());
				carregarConfiguracoesGerais(config);
				log.info("Configurações gerais carregadas!");
			} catch (Exception e) {
				log.fatal("Erro ao ler as propriedades do arquivo de configuração...", e);
				return null;
			}

			try {
				log.info("Lendo cidades e rotas...");
				carregarCidadesERotas(xml);
				log.info("Cidades e Rotas carregadas com sucesso!");
			} catch (Exception e) {
				log.fatal("Erro ao ler as rotas do arquivo de configuração!", e);
				return null;
			}

		}

		return configuracao;
	}

	private static Element getXmlDeConfiguracao(String caminho, Element xml) {
		try {
			log.info("Carregando configurações do documento " + caminho);
			xml = FileUtil.getElementoXML(caminho);
			log.info("XML de configuração carregado...");
		} catch (MalformedURLException e) {
			log.error("A URL não pode ser formada!", e);
			return null;
		} catch (DocumentException e) {
			log.error("Exceção ao criar o documento XML.", e);
			return null;
		}
		return xml;
	}

	/**
	 * <p>
	 * Carrega as cidades e as rotas do grafo
	 * </p>
	 * 
	 * @param xml
	 *            XML que cont&eacute;m as configura&ccedil;&otilde;es
	 */
	private static void carregarCidadesERotas(Element xml) {
		Element xrotas = xml.element(ConfigTags.ROTAS.tag());
		List<Element> rotasXml = xrotas.elements(ConfigTags.ROTA.tag());
		for (Element xrota : rotasXml) {

			String cidade = FileUtil.getValorDaTag(xrota, ConfigTags.ORIGEM.tag());
			configuracao.cidades.add(cidade);
			log.info("Cidade adicionada: " + cidade);

			log.info("Carregando os destinos da cidade...");
			Element xdestinos = xrota.element(ConfigTags.DESTINOS.tag());
			List<Element> destinos = xdestinos.elements(ConfigTags.DESTINO.tag());
			for (Element destino : destinos) {
				String destinoNome = FileUtil.getPropriedadeDaTag(destino, ConfigAtributos.NOME.atributo());
				String distancia = FileUtil.getPropriedadeDaTag(destino, ConfigAtributos.DISTANCIA.atributo());
				String rota = cidade + configuracao.rotasPattern + distancia + configuracao.rotasPattern + destinoNome;
				configuracao.rotas.add(rota);
				log.info("Nova rota encontrado: " + rota);
			}

		}
	}

	/**
	 * <p>
	 * Carrega as configura&ccedil;&otilde;es gerais
	 * </p>
	 * 
	 * @param config
	 *            XML que cont&eacute;m as configura&ccedil;&otilde;es
	 */
	private static void carregarConfiguracoesGerais(Element config) {
		configuracao.numeroDeIndividuos = Integer.parseInt(FileUtil.getValorDaTag(config, ConfigTags.INDIVIUOS.tag()));
		log.info("Número de indivíduos: " + configuracao.numeroDeIndividuos);
		configuracao.numeroDeIteracoes = Integer.parseInt(FileUtil.getValorDaTag(config, ConfigTags.ITERACOES.tag()));
		log.info("Número de iterações: " + configuracao.numeroDeIteracoes);
		configuracao.numeroDeTentativas = Integer.parseInt(FileUtil.getValorDaTag(config, ConfigTags.TENTATIVAS.tag()));
		log.info("Número máximo de tentativas para gerar um cromossomo consistente: " + configuracao.numeroDeTentativas);
	}

	public Integer getNumeroDeIteracoes() {
		return numeroDeIteracoes;
	}

	public Integer getNumeroDeIndividuos() {
		return numeroDeIndividuos;
	}

	public List<String> getCidades() {
		return cidades;
	}

	public List<String> getRotas() {
		return rotas;
	}

	public String getRotasPattern() {
		return rotasPattern;
	}

	public Integer getNumeroDeTentativas() {
		return numeroDeTentativas;
	}
}
