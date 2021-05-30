package Model;

public class vaga {
	private int id_vaga;
	private String descricao;
	private String requisitos;
	private String tipo;
	private int id_empresa;
	
	public vaga() {
		this.id_vaga = -1;
		this.descricao = "";
		this.requisitos = "";
		this.tipo = "*";
		this.id_empresa = -1;
	}
	
	public vaga(int id_vaga, String descricao, String requisitos, String tipo, int id_empresa) {
		this.id_vaga = id_vaga;
		this.descricao = descricao;
		this.requisitos = requisitos;
		this.tipo = tipo;
		this.id_empresa = id_empresa;
	}	
	
	public int getId_Vaga(){
		return this.id_vaga;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public String getRequisitos() {
		return this.requisitos;
	}

	public String getTipo() {
		return this.tipo;
	}
	
	public int getId_Empresa() {
		return this.id_empresa;
	}
	
	public void setId_Vaga(int id_vaga) {
		this.id_vaga = id_vaga;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setId_Empresa(int id_empresa) {
		this.id_empresa = id_empresa;
	}
	
	@Override
	public String toString() {
		return "Cadastro [Id=" + id_vaga +", descricao="+descricao+ ", requisitos=" + requisitos + ", Tipo=" + tipo + " id_empresa="+id_empresa+ "]" ;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId_Vaga() == ((vaga) obj).getId_Vaga());
	}	
	
}
