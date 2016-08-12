package api.cadastro;

import org.joda.time.DateTime;

public class PesquisaDTO {
	
	private String nome;
	private String cpf;
	private String email;
	
	private DateTime dataInicio;
	private DateTime dataFim;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public DateTime getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(DateTime dataInicio) {
		this.dataInicio = dataInicio;
	}
	public DateTime getDataFim() {
		return dataFim;
	}
	public void setDataFim(DateTime dataFim) {
		this.dataFim = dataFim;
	}
	
	
}
