package br.com.ag.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <p>
 * Classe &uacute;til para acesso a arquivos.
 * </p>
 * 
 * @author rhuan
 * 
 */
public class FileUtil {

	/**
	 * <p>
	 * Parseia o documento <b>.xml</b> e retorna seu valor como Document
	 * </p>
	 * 
	 * @param caminho
	 *            Caminho para o arquivo em formato URL
	 * @return Retorna um documento do <b>.xml</b> parseado...
	 * @throws MalformedURLException
	 * @throws DocumentException
	 */
	public static Document getDocumento(URL caminho) throws MalformedURLException, DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(caminho);
		return document;
	}

	/**
	 * <p>
	 * Calcula a URL para um arquivo
	 * </p>
	 * 
	 * @param arquivo
	 *            Arquivo ao qual se deseja a URL
	 * @return Retorna uma URL para o arquivo em que&atilde;
	 * @throws MalformedURLException
	 */
	public static URL getURLParaOArquivo(File arquivo) throws MalformedURLException {
		String prefixo = "file:///";
		return new URL(prefixo + arquivo.getAbsolutePath());
	}

	/**
	 * <p>
	 * Retorna uma URL para o arquivo no caminho especificado...
	 * </p>
	 * 
	 * @param caminho
	 *            Caminho para o arquivo ao qual se quer a URL
	 * @return Retorna uma URL para esse arquivo
	 * @see #getURLParaOArquivo(File)
	 * @throws MalformedURLException
	 */
	public static URL getURLParaOArquivo(String caminho) throws MalformedURLException {
		return getURLParaOArquivo(new File(caminho));
	}

	/**
	 * <p>
	 * Retorna o valor de uma tag espec&iacute;fica.
	 * </p>
	 * 
	 * @param elementoXml
	 *            Elemento XML que cont&eacute;m a tag
	 * @param tag
	 *            Tag que se deseja o valor.
	 * @return Retorna o valor desta tag
	 */
	public static String getValorDaTag(Element elementoXml, String tag) {
		return elementoXml.elementText(tag);
	}

	/**
	 * <p>
	 * Calcula o valor de uma propriedade na tag
	 * </p>
	 * 
	 * @param tag
	 *            Tag onde se encontra a propriedade
	 * @param propriedade
	 *            Propriedade da qual se deseja o valor
	 * @return Retorna o valor da propriedade em si e caso este n&atilde;o exista retorna uma String vazia
	 */
	public static String getPropriedadeDaTag(Element tag, String propriedade) {
		return tag.attributeValue(propriedade, "");
	}

	/**
	 * <p>
	 * Calcula um objeto Element para que sejam lidas suas configura&ccedil;&otilde;es de maneira mais simples.
	 * </p>
	 * 
	 * @param caminho
	 *            Caminho para o arquivo XML
	 * @return Retorna o elemento XML em si
	 * @throws MalformedURLException
	 * @throws DocumentException
	 */
	public static Element getElementoXML(String caminho) throws MalformedURLException, DocumentException {
		return getDocumento(getURLParaOArquivo(caminho)).getRootElement();
	}

}
