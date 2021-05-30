package Dao;

import Model.vaga;

import java.io.IOException;
import java.sql.*;


public class DAOvaga {
	
	private int maxId = 0;
	private Connection conexao;
	
	public int getMaxId() {
		return maxId;
	}

	public DAOvaga() throws IOException {
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
	
	public void add(vaga v) {
		try {  
			this.maxId = (v.getId_Vaga() > this.maxId) ? v.getId_Vaga() : this.maxId;
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO vaga "
					       + "VALUES ("+v.getId_Vaga()+ ", '" + v.getDescricao() + "', '"  
					       + v.getTipo() + "', '" + v.getRequisitos() + v.getId_Empresa() + ");");
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public vaga get(int id_vaga) {
		vaga v = new vaga();
		try {  
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM profissional WHERE id_empresa = "+ id_vaga);	
			v.setId_Vaga(rs.getInt("id_vaga"));
			v.setDescricao(rs.getString("descricao"));
			v.setTipo(rs.getString("tipo"));
			v.setRequisitos(rs.getString("requisitos"));
			v.setId_Empresa(rs.getInt("id_empresa"));
			st.close();

		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return v;
	}

	public void update(vaga v) {
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE vaga SET descricao = '" + v.getDescricao() + "', tipo = '"  
				       + v.getTipo() + "', id_empresa = " + v.getId_Empresa() 
				       + " WHERE id_vaga = " + v.getId_Vaga();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public void remove(int id_vaga) {
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM vaga WHERE id_vaga = " + id_vaga);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public vaga[] getAll() {
		vaga[] v = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM vaga");		
	         if(rs.next()){
	             rs.last();
	             v = new vaga[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                v[i] = new vaga(rs.getInt("id_vaga"), rs.getString("descricao"), 
	                		           rs.getString("tipo"), rs.getString("requisitos"),
	                		           rs.getInt("id_empresa"));
	             }
	          }
	          st.close();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		return v;
	}
}
