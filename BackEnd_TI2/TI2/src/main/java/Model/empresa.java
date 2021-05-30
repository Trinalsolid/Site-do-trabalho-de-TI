package Model;

public class empresa {
	private int id_empresa;
	private String nome;
	private String cnpj;
	private String telefone;
	private String email;
	private String site;
	private int id_cadastro;
	
	public empresa() {
		this.id_empresa = -1;
		this.nome = "";
		this.cnpj = "";
		this.telefone = "";
		this.email = "";
		this.site = "";
		this.id_cadastro = - -1;
	}
	
	public empresa(int id_empresa, String nome, String cnpj, String telefone, String email, String site, int id_cadastro) {
		this.id_empresa = id_empresa;
		this.nome = nome;
		this.cnpj = cnpj;
		this.telefone = telefone;
		this.email = email;
		this.site = site;
		this.id_cadastro = id_cadastro;
	}	

	public int getId_Empresa(){
		return this.id_empresa;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getCnpj() {
		return this.cnpj;
	}

	public String getTelefone() {
		return this.telefone;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getSite() {
		return this.site;
	}
	
	public int getId_Cadastro() {
		return this.id_cadastro;
	}	
	
	public void setId_Empresa(int id_empresa) {
		this.id_empresa = id_empresa;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	
	public void setId_Cadastro(int id_cadastro) {
		this.id_cadastro = id_cadastro;
	}
	
	
	
	@Override
	public String toString() {
		return "Empresa [Id=" + id_empresa +", nome="+nome+ ", cnpj=" + cnpj + ", telefone=" + telefone + " email="+email+" site="+site+" id_cadastro="+id_cadastro+"]" ;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId_Empresa() == ((empresa) obj).getId_Empresa());
	}	
	
}
