package Model;

public class candidatura {
	private int id_candidatura;
	private int id_vaga;
	private int id_profissional;
	
	public candidatura() {
		this.id_candidatura = -1;
		this.id_vaga = -1;
		this.id_profissional = -1;
	}
	
	public candidatura(int id_candidatura, int id_vaga, int id_profissional) {
		this.id_candidatura = id_candidatura;
		this.id_vaga = id_vaga;
		this.id_profissional = id_profissional;
	}	
	
	public int getId_Candidatura(){
		return this.id_candidatura;
	}	
	
	public int getId_Vaga() {
		return this.id_vaga;
	}
	
	public int getId_Profissional() {
		return this.id_profissional;
	}
	
	public void setId_Candidatura(int id_candidatura) {
		this.id_candidatura = id_candidatura;
	}
	
	public void setId_Vaga(int id_vaga) {
		this.id_vaga = id_vaga;
	}
	
	public void setId_Profissional(int id_profissional) {
		this.id_profissional = id_profissional;
	}
	
	@Override
	public String toString() {
		return "Cadastro [Id=" + id_candidatura +", id_vaga="+id_vaga+ ", id_profissional=" + id_profissional + "]" ;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId_Candidatura() == ((candidatura) obj).getId_Candidatura());
	}	
}
