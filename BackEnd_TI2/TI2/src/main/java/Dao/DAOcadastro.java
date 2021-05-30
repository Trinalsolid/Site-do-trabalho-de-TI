package Dao;

import Model.cadastro;

import java.io.IOException;
import java.sql.*;

public class DAOcadastro {
	
	private int maxId = 0;
	private Connection conexao;
	
	
	public int getMaxId() {
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT COALESCE(MAX(id_cadastro),0) as maxId from cadastro");	
			rs.next();
			maxId = rs.getInt(1);	
			st.close();
		}
		catch(SQLException u) {
			System.out.println("ERRO -> "+u.getMessage() );
		}
		return maxId;
	}

	public DAOcadastro() throws IOException {
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
	
	public void add(cadastro c) {
		try {  
			this.maxId = (c.getId_Cadastro() > this.maxId) ? c.getId_Cadastro() : this.maxId;
			
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO cadastro "
					       + "VALUES ("+c.getId_Cadastro()+ ", '" + c.getLogin() + "', '"  
					       + c.getSenha() + "', '"+c.getTipo()+ "','" +c.getNome()+"');");
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException("Erro add cadastro" + u);
		}
	}

	public boolean verificaLogin(String login, String senha) {
		boolean result = false;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM cadastro WHERE login = '"+login+"'");
			
			if (rs.next()) {
				result = ( rs.getString("senha").equals(senha) ) ? true : result;
			}
			
			
			st.close();	
		} catch (SQLException u) {
			System.out.println("Erro" + u.getMessage());
		}
		
		return result;
	}
	
	
	public cadastro get(int id_cadastro) {
		cadastro c = new cadastro();
		try {  
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM cadastro WHERE id_cadastro = "+ id_cadastro);	
			c.setId_cadastro(rs.getInt("id_cadastro"));
			c.setLogin(rs.getString("login"));
			c.setSenha(rs.getString("senha"));
			c.setTipo(rs.getString("tipo"));
			st.close();

		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return c;
	}

	public void update(cadastro c) {
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE cadastro SET login = '" + c.getLogin() + "', senha = '"  
				       + c.getSenha() + "', tipo = '" + c.getTipo() + "'"
					   + " WHERE id_cadastro = " + c.getId_Cadastro();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public void remove(int id_cadastro) {
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM cadastro WHERE id_cadastro = " + id_cadastro);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}

	public cadastro[] getAll() {
		cadastro[] c = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM cadastro");		
	         if(rs.next()){
	             rs.last();
	             c = new cadastro[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                c[i] = new cadastro(rs.getInt("id_cadastro"), rs.getString("login"), 
	                		            rs.getString("senha"), rs.getString("tipo"),rs.getString("nome"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return c;
	}
	
}
