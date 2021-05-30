package Model;

public class profissional {
	private int id_profissional;
	private String nome;
	private String cpf;
	private String telefone;
	private String email;
	private String competencias;
	private int id_cadastro;
	
	public profissional() {
		this.id_profissional = -1;
		this.nome = "";
		this.cpf = "";
		this.telefone = "";
		this.email = "";
		this.competencias = "";
		this.id_cadastro = - -1;
	}
	
	public profissional(int id_profissional, String nome, String cpf, String telefone, String email, String competencias, int id_cadastro) {
		this.id_profissional = id_profissional;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.competencias = competencias;
		this.id_cadastro = id_cadastro;
	}	

	public int getId_Profissional(){
		return this.id_profissional;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getCpf() {
		return this.cpf;
	}

	public String getTelefone() {
		return this.telefone;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getCompetencias() {
		return this.competencias;
	}
	
	public int getId_Cadastro() {
		return this.id_cadastro;
	}	
	
	public void setId_Profissional(int id_profissional) {
		this.id_profissional = id_profissional;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setCompetencias(String competencias) {
		this.competencias = competencias;
	}
	
	public void setId_Cadastro(int id_cadastro) {
		this.id_cadastro = id_cadastro;
	}
	
	
	
	@Override
	public String toString() {
		return "Empresa [Id=" + id_profissional +", nome="+nome+ ", cpf=" + cpf + ", telefone=" + telefone + " email="+email+" competencias="+competencias+" id_cadastro="+id_cadastro+"]" ;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId_Profissional() == ((profissional) obj).getId_Profissional());
	}	
	
}
