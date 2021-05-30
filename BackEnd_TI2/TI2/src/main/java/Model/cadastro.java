package Model;

public class cadastro {
	private int id_cadastro;
	private String login;
	private String senha;
	private String tipo;
	private String nome;
	
	public cadastro() {
		this.id_cadastro = -1;
		this.login = "";
		this.senha = "";
		this.tipo = "*";
		this.nome = "";
	}
	
	public cadastro(int id_cadastro, String login, String senha, String tipo,String nome) {
		this.id_cadastro = id_cadastro;
		this.login = login;
		this.senha = senha;
		this.tipo = tipo;
		this.nome = nome;
	}	

	public int getId_Cadastro(){
		return this.id_cadastro;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public String getSenha() {
		return this.senha;
	}

	public String getTipo() {
		return this.tipo;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setId_cadastro(int id_cadastro) {
		this.id_cadastro = id_cadastro;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return "Cadastro [Id=" + id_cadastro +", Login="+login+ ", Senha=" + senha + ", Tipo=" + tipo +"]" ;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId_Cadastro() == ((cadastro) obj).getId_Cadastro());
	}	
	
}
