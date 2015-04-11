package br.com.ag.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import br.com.ag.config.constants.Paths;
import br.com.ag.evolucao.agentes.Cromossomo;
import br.com.ag.evolucao.agentes.Gene;
import br.com.ag.evolucao.control.EvolucaoController;
import br.com.ag.evolucao.control.PopulacaoController;
import br.com.ag.objetos.Cidade;
import br.com.ag.objetos.Grafo;
import br.com.ag.objetos.fabrica.GrafoFactory;

/**
 * <p>
 * Classe main do algoritmo.
 * </p>
 * 
 * @author rhuan
 * 
 */
public class Main {

	public static void main(String[] args) {

		if ((args != null) && !(args[0] == null || args[0].equals("")) && !(args[1] == null || args[1].equals(""))) {

			Cidade origem = new Cidade(args[0]);
			Cidade destino = new Cidade(args[1]);

			Grafo grafo = GrafoFactory.getGrafo(Paths.CONFIG_PATH);
			PopulacaoController populacaoController = new PopulacaoController(grafo);
			populacaoController.carregueConfiguracoesDaPopulacao(Paths.CONFIG_PATH);
			populacaoController.inicializeAPopulacao(origem, destino);

			EvolucaoController evolucaoController = new EvolucaoController();
			evolucaoController.carregarConfiguracoes(Paths.CONFIG_PATH);
			evolucaoController.evoluirPopulacao(PopulacaoController.getPopulacao(), grafo, origem, destino);

			try {
				gerarSaida();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {

			System.out.println("Ta me zoando seu fanfarrao? ¬¬'");
			System.out.println("A sintaxe correta e: java -jar <meu-nome> <CIDADE-ORIGEM> <CIDADE-DESTINO>");

		}

	}

	private static void gerarSaida() throws IOException, UnsupportedEncodingException, FileNotFoundException {
		Cromossomo ganhador = PopulacaoController.getPopulacao().getCromossomoDeMaiorFitness();

		File solucao = new File(Paths.OUT_PATH);
		if (solucao.exists()) {
			solucao.delete();
		}
		solucao.createNewFile();

		String pulaLinha = System.getProperty("line.separator");

		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(solucao.getCanonicalPath()),
				"UTF-8"));
		out.append("----------------------------------------------------" + pulaLinha);
		out.append("SOLUÇÃO ENCONTRADA" + pulaLinha);
		out.append("----------------------------------------------------" + pulaLinha);
		out.append("O melhor Cromossomo foi o de ID " + ganhador.getId() + " com um FITNESS de "
				+ ganhador.getFitness() + pulaLinha);
		out.append("ROTA PERCORRIDA: (origem) -> (destino) (distancia)" + pulaLinha);

		anunciarRota(ganhador, out, pulaLinha);
		out.append("----------------------------------------------------" + pulaLinha);
		out.append("LISTA DE SOLUÇÕES:" + pulaLinha);
		for (Cromossomo cromossomo : PopulacaoController.getPopulacao().getCromossomos()) {
			out.append("Elemento " + cromossomo.getId() + pulaLinha);
			out.append("Fitness = " + cromossomo.getFitness() + pulaLinha);
			anunciarRota(cromossomo, out, pulaLinha);
		}
		out.flush();
		out.close();

	}

	private static void anunciarRota(Cromossomo ganhador, Writer out, String pulaLinha) throws IOException {
		for (Gene gene : ganhador.getGenes()) {
			out.append(gene.getRota().getCidadeHead().getIdentificador() + " -> "
					+ gene.getRota().getCidadeTail().getIdentificador() + " " + gene.getRota().getDistancia()
					+ pulaLinha);
		}
		out.append("----------------------------------------------------" + pulaLinha);
	}
}
