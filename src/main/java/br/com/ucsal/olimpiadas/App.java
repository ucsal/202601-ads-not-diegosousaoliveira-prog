package br.com.ucsal.olimpiadas;

import java.util.Scanner;
import java.util.List;
import br.com.ucsal.olimpiadas.repository.*;

public class App {

 
	private static final ParticipanteRepository participanteRepo = new ParticipanteRepository();
	private static final ProvaRepository provaRepo = new ProvaRepository();
	private static final QuestaoRepository questaoRepo = new QuestaoRepository();
	private static final TentativaRepository tentativaRepo = new TentativaRepository();
	
 
	private static final CalculadoraNota calculadora = new CalculadoraOlimpiada();
	private static final VisualizadorTabuleiro visualizador = new ConsoleXadrezVisualizador();
	
	private static final Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		seed();  

		while (true) {
			System.out.println("\n=== OLIMPÍADA SOLID (V1) ===");
			System.out.println("1) Cadastrar participante");
			System.out.println("2) Cadastrar prova");
			System.out.println("3) Cadastrar questão");
			System.out.println("4) Aplicar prova");
			System.out.println("5) Listar tentativas");
			System.out.println("0) Sair");
			System.out.print("> ");

			String opcao = in.nextLine();
			if (opcao.equals("0")) break;

			switch (opcao) {
				case "1" -> cadastrarParticipante();
				case "2" -> cadastrarProva();
				case "3" -> cadastrarQuestao();
				case "4" -> aplicarProva();
				case "5" -> listarTentativas();
				default -> System.out.println("opção inválida");
			}
		}
	}

	static void cadastrarParticipante() {
		System.out.print("Nome: ");
		String nome = in.nextLine();
		if (nome == null || nome.isBlank()) return;

		Participante p = new Participante();
		p.setNome(nome);
		System.out.print("Email: ");
		p.setEmail(in.nextLine());

		participanteRepo.salvar(p);
		System.out.println("Participante cadastrado: " + p.getId());
	}

	static void cadastrarProva() {
		System.out.print("Título da prova: ");
		String titulo = in.nextLine();
		if (titulo == null || titulo.isBlank()) return;

		Prova prova = new Prova();
		prova.setTitulo(titulo);
		provaRepo.salvar(prova);
		System.out.println("Prova criada: " + prova.getId());
	}

	static void cadastrarQuestao() {
		if (provaRepo.listarTodas().isEmpty()) {
			System.out.println("Não há provas.");
			return;
		}
		Long provaId = escolherProva();
		if (provaId == null) return;

		Questao q = new Questao();
		q.setProvaId(provaId);
		System.out.print("Enunciado: ");
		q.setEnunciado(in.nextLine());

		String[] alts = new String[5];
		for (int i = 0; i < 5; i++) {
			char letra = (char) ('A' + i);
			System.out.print("Alternativa " + letra + ": ");
			alts[i] = letra + ") " + in.nextLine();
		}
		q.setAlternativas(alts);

		System.out.print("Correta (A-E): ");
		try {
			q.setAlternativaCorreta(Questao.normalizar(in.nextLine().trim().charAt(0)));
			questaoRepo.salvar(q);
			System.out.println("Questão salva!");
		} catch (Exception e) { System.out.println("Erro na alternativa."); }
	}

	static void aplicarProva() {
		if (participanteRepo.listarTodos().isEmpty() || provaRepo.listarTodas().isEmpty()) return;

		Long pId = escolherParticipante();
		Long prId = escolherProva();
		if (pId == null || prId == null) return;

		List<Questao> questoes = questaoRepo.listarPorProva(prId);
		if (questoes.isEmpty()) return;

		Tentativa t = new Tentativa();
		t.setParticipanteId(pId);
		t.setProvaId(prId);

		for (Questao q : questoes) {
			System.out.println("\nQuestão #" + q.getId() + "\n" + q.getEnunciado());
			visualizador.exibir(q.getFenInicial());
			for (String alt : q.getAlternativas()) System.out.println(alt);
			
			System.out.print("Resposta: ");
			char marcada = 'X';
			try { marcada = Questao.normalizar(in.nextLine().trim().charAt(0)); } catch (Exception e) {}

			Resposta r = new Resposta();
			r.setQuestaoId(q.getId());
			r.setAlternativaMarcada(marcada);
			r.setCorreta(q.isRespostaCorreta(marcada));
			t.getRespostas().add(r);
		}

		tentativaRepo.salvar(t);
		System.out.println("\nNota: " + calculadora.calcular(t));
	}

	static void listarTentativas() {
		for (Tentativa t : tentativaRepo.listarTodas()) {
			System.out.printf("#%d | Part=%d | Prova=%d | Nota=%d%n", 
				t.getId(), t.getParticipanteId(), t.getProvaId(), calculadora.calcular(t));
		}
	}

	static Long escolherParticipante() {
		participanteRepo.listarTodos().forEach(p -> System.out.println(p.getId() + ") " + p.getNome()));
		System.out.print("ID Participante: ");
		try { return Long.parseLong(in.nextLine()); } catch (Exception e) { return null; }
	}

	static Long escolherProva() {
		provaRepo.listarTodas().forEach(p -> System.out.println(p.getId() + ") " + p.getTitulo()));
		System.out.print("ID Prova: ");
		try { return Long.parseLong(in.nextLine()); } catch (Exception e) { return null; }
	}

	static void seed() {
		Prova p = new Prova();
		p.setTitulo("Olimpíada Inicial");
		provaRepo.salvar(p);
		
		Questao q = new Questao();
		q.setProvaId(p.getId());
		q.setEnunciado("Mate em 1?");
		q.setFenInicial("6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1");
		q.setAlternativas(new String[]{"A) Qh7#", "B) Qf5#", "C) Qc8#", "D) Qh8#", "E) Qe6#"});
		q.setAlternativaCorreta('C');
		questaoRepo.salvar(q);
	}
}