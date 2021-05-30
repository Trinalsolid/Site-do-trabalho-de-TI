package Dao;

import Model.candidatura;

import java.io.IOException;
import java.sql.*;

public class DAOcandidatura {

	private int maxId = 0;
	private Connection conexao;
	
	public int getMaxId() {
		return maxId;
	}

	public DAOcandidatura() throws IOException {
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
	
	public void add(candidatura c) {
		try {  
			this.maxId = (c.getId_Candidatura() > this.maxId) ? c.getId_Candidatura() : this.maxId;
			
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO cadastro "
					       + "VALUES ("+c.getId_Candidatura()+ ", " + c.getId_Vaga() + ", "  
					       + c.getId_Profissional() + ");");
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public candidatura get(int id_candidatura) {
		candidatura c = new candidatura();
		try {  
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM cadastro WHERE id_cadastro = "+ id_candidatura);	
			c.setId_Candidatura(rs.getInt("id_cadastro"));
			c.setId_Vaga(rs.getInt("id_vaga"));
			c.setId_Profissional(rs.getInt("id_profissional"));
			st.close();

		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return c;
	}

	public void update(candidatura c) {
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE candidatura SET id_vaga = " + c.getId_Vaga() 
						+ ", id_profissional = '"+ c.getId_Profissional()
					    + " WHERE id_candidatura = " + c.getId_Candidatura();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public void remove(int id_candidatura) {
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM candidatura WHERE id_candidatura = " + id_candidatura);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public candidatura[] getAll() {
		candidatura[] c = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM candidatura");		
	         if(rs.next()){
	             rs.last();
	             c = new candidatura[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                c[i] = new candidatura(rs.getInt("id_cadastro"), rs.getInt("id_vaga"), rs.getInt("id_profissional"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return c;
	}
}
