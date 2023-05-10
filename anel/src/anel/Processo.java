package anel;

import java.util.LinkedList;


public class Processo {
	
	private int pid;
	private boolean Coordenador;
	
	public Processo (int pid){
		setPid(pid);
	}
	
	public Processo (int pid, boolean Coordenador){
		setPid(pid);
		setCoordenador(Coordenador);
	}
	
	public boolean isCoordenador () {
		return Coordenador;
	}

	public void setCoordenador (boolean Coordenador) {
		this.Coordenador = Coordenador;
	}

	public int getPid () {
		return pid;
	}
	
	public void setPid (int pid) {
		this.pid = pid;
	}
	
	public boolean enviarRequisicao () {
		boolean resultadoRequisicao = false;
		for (Processo p : Anel.processosAtivos) {
			if (p.isCoordenador())
				resultadoRequisicao = p.receberRequisicao(this.pid);
		}
		
		// Se nao existe um coordenador.
		if (!resultadoRequisicao)
			this.realizarEleicao();
		
		System.out.println("Fim da requisicao.");
		return resultadoRequisicao;
	}
	
	private boolean receberRequisicao (int pidOrigemRequisicao) {
		
		/* TRATAMENTO DA REQUISICAO AQUI... */
		
		System.out.println("Requisicao do processo " + pidOrigemRequisicao + " recebida com sucesso.");
		return true;
	}
	
	private void realizarEleicao () {
		
		System.out.println("Processo de eleicao iniciado");
		
		// Primeiro consulta cada processo, adicionando o id de cada um em uma nova lista.
		
		LinkedList<Integer> idProcessosConsultados = new LinkedList<>();
		for (Processo p : Anel.processosAtivos)
			p.consultarProcesso(idProcessosConsultados);
		
		// Depois percorre a lista de id's procurando pelo maior.
		
		int idNovoCoordenador = this.getPid();
		for (Integer id : idProcessosConsultados) {
			if (id > idNovoCoordenador)
				idNovoCoordenador = id;
		}
		
		// E entao atualiza o novo coordenador.
		
		boolean resultadoAtualizacao = false;
		resultadoAtualizacao = atualizarCoordenador(idNovoCoordenador);
		
		if (resultadoAtualizacao)
			System.out.println("Eleicao concluida com sucesso. O novo coordenador eh " + idNovoCoordenador + ".");
		else
			System.out.println("A eleicao falhou. Nao foi encontrado um novo coordenador.");
	}
	
	private void consultarProcesso (LinkedList<Integer> processosConsultados) {
		processosConsultados.add(this.getPid());
	}
	
	private boolean atualizarCoordenador (int idNovoCoordenador) {
		// Garante que nao exista nenhum outro processo cadastrado como coordenador,
		// a nao ser o novo coordenador.
		
		for (Processo p : Anel.processosAtivos) {			
			if (p.getPid() == idNovoCoordenador)
				p.setCoordenador(true);
			else
				p.setCoordenador(false);
		}
		
		return true;
	}
}