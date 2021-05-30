package Dao;

import Model.profissional;

import java.io.IOException;
import java.sql.*;

public class DAOprofissional {
	
	private int maxId = 0;
	private Connection conexao;
	
	public int getMaxId() {
		return maxId;
	}

	public DAOprofissional() throws IOException {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "dbTI2";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NãO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NãO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	public void add(profissional p) {
		try {  
			this.maxId = (p.getId_Profissional() > this.maxId) ? p.getId_Profissional() : this.maxId;
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO profissional "
					       + "VALUES ("+p.getId_Profissional()+ ", '" + p.getCpf() + "', '"  
					       + p.getNome() + "', '" + p.getTelefone() + "', '"+ p.getEmail() + "', '"
					       + p.getCompetencias() + "', " + p.getId_Cadastro() + ");");
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public profissional get(int id_profissional) {
		profissional p = new profissional();
		try {  
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM profissional WHERE id_empresa = "+ id_profissional);	
			p.setId_Profissional(rs.getInt("id_profissional"));
			p.setCpf(rs.getString("cpf"));
			p.setNome(rs.getString("nome"));
			p.setTelefone(rs.getString("telefone"));
			p.setEmail(rs.getString("email"));
			p.setCompetencias(rs.getString("competencias"));
			p.setId_Cadastro(rs.getInt("id_cadastro"));
			st.close();

		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return p;
	}

	public void update(profissional p) {
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE profissional SET cpf = '" + p.getCpf() + "', nome = '"  
				       + p.getNome() + "', telefone = '" + p.getTelefone() + "', email = '"
				       + p.getEmail() + "', competencias = '" + p.getCompetencias() + "', id_cadastro = "
				       + p.getId_Cadastro() + " WHERE id_empresa = " + p.getId_Profissional();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public void remove(int id_profissional) {
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM profissional WHERE id_profissional = " + id_profissional);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public profissional[] getAll() {
		profissional[] p = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM profissional");		
	         if(rs.next()){
	             rs.last();
	             p = new profissional[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                p[i] = new profissional(rs.getInt("id_profissional"), rs.getString("cpf"), 
	                		           rs.getString("nome"), rs.getString("telefone"),
	                		           rs.getString("email"),rs.getString("competencias"),rs.getInt("id_cadastro"));
	             }
	          }
	          st.close();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		return p;
	}
}
