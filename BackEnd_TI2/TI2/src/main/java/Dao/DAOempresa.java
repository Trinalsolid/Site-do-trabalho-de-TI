package Dao;

import Model.empresa;

import java.io.IOException;
import java.sql.*;

public class DAOempresa {
	
	private int maxId = 0;
	private Connection conexao;
	
	public int getMaxId() {
		return maxId;
	}

	public DAOempresa() throws IOException {
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
	
	public void add(empresa e) {
		try {  
			this.maxId = (e.getId_Empresa() > this.maxId) ? e.getId_Empresa() : this.maxId;
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO empresa "
					       + "VALUES ("+e.getId_Empresa()+ ", '" + e.getNome() + "', '"  
					       + e.getCnpj() + "', '" + e.getTelefone() + "', '"+ e.getEmail() + "', '"
					       + e.getSite() + "', " + e.getId_Cadastro() + ");");
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public empresa get(int id_empresa) {
		empresa e = new empresa();
		try {  
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM empresa WHERE id_empresa = "+ id_empresa);	
			e.setId_Empresa(rs.getInt("id_empresa"));
			e.setNome(rs.getString("nome"));
			e.setCnpj(rs.getString("cnpj"));
			e.setTelefone(rs.getString("telefone"));
			e.setEmail(rs.getString("email"));
			e.setSite(rs.getString("site"));
			e.setId_Cadastro(rs.getInt("id_cadastro"));
			st.close();

		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return e;
	}

	public void update(empresa e) {
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE empresa SET nome = '" + e.getNome() + "', cnpj = '"  
				       + e.getCnpj() + "', telefone = '" + e.getTelefone() + "', email = '"
				       + e.getEmail() + "', site = '" + e.getSite() + "', id_cadastro = "+e.getId_Cadastro()
					   + " WHERE id_empresa = " + e.getId_Empresa();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public void remove(int id_empresa) {
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM empresa WHERE id_empresa = " + id_empresa);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public empresa[] getAll() {
		empresa[] e = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM empresa");		
	         if(rs.next()){
	             rs.last();
	             e = new empresa[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                e[i] = new empresa(rs.getInt("id_empresa"), rs.getString("nome"), 
	                		           rs.getString("cnpj"), rs.getString("telefone"),
	                		           rs.getString("email"),rs.getString("site"),rs.getInt("id_cadastro"));
	             }
	          }
	          st.close();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		return e;
	}
	
}
