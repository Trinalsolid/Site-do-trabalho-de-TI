package Service;

import java.io.IOException;

import Dao.DAOcadastro;
import Model.cadastro;
import spark.Request;
import spark.Response;

public class cadastroService {

	private DAOcadastro cadastroDAO;
	
	public cadastroService() {
		try {
			cadastroDAO = new DAOcadastro();
			cadastroDAO.conectar();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public Object login(Request request, Response response, boolean isUsuario) {
		String login = request.queryParams("login");
		String senha = MD5.toMD5(request.queryParams("senha"));
		
		if (cadastroDAO.verificaLogin(login,senha) ){
			if(isUsuario) response.redirect("/PerfilUsuario.html"); else response.redirect("/PerfilEmpresa.html");
		}
		
		return "OK";
	}

	public Object add(Request request, Response response, boolean isUsuario) {
		int id_cadastro = cadastroDAO.getMaxId() + 1;
		String login = request.queryParams("login");
		String senha = MD5.toMD5(request.queryParams("senha"));
		String tipo = (isUsuario) ? "profissional" : "empresa";
		String nome = request.queryParams("nome");
		nome += (isUsuario) ? " "+request.queryParams("sobrenome"): "";
		
		cadastro c = new cadastro(id_cadastro, login, senha, tipo, nome);

		cadastroDAO.add(c);
		
		response.status(201); // 201 Created
		response.redirect("/Login.html");
		return "OK";
	}

	public Object get(Request request, Response response) {
		int id_cadastro = Integer.parseInt(request.params(":id_cadastro"));
		
		cadastro c = (cadastro) cadastroDAO.get(id_cadastro);
		
		if (c != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<cadastro>\n" + 
            		"\t<id_cadastro>" + c.getId_Cadastro() + "</id_cadastro>\n" +
            		"\t<login>" + c.getLogin() + "</login>\n" +
            		"\t<senha>" + c.getSenha() + "</senha>\n" +
            		"\t<tipo>" + c.getTipo() + "</tipo>\n" +
            		"</cadastro>\n";
        } else {
            response.status(404); // 404 Not found
            return "Cadastro " + id_cadastro + " não encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id_cadastro = Integer.parseInt(request.params(":id_cadastro"));
        
		cadastro c = (cadastro) cadastroDAO.get(id_cadastro);

        if (c != null) {
        	c.setLogin(request.queryParams("login"));
        	c.setSenha(request.queryParams("senha"));
        	//c.setTipo(request.queryParams("tipo"));

        	cadastroDAO.update(c);
        	
            return id_cadastro;
        } else {
            response.status(404); // 404 Not found
            return "Cadastro" + id_cadastro + " não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        int id_cadastro = Integer.parseInt(request.params(":id_cadastro"));

        cadastro c = (cadastro) cadastroDAO.get(id_cadastro);

        if (c != null) {

            cadastroDAO.remove(c.getId_Cadastro());

            response.status(200); // success
        	return id_cadastro;
        } else {
            response.status(404); // 404 Not found
            return "Cadastro" + id_cadastro + " não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<x type=\"array\">");
		for (cadastro c : cadastroDAO.getAll()) {
			returnValue.append("\n<cadastro>\n" + 
				            		"\t<id_cadastro>" + c.getId_Cadastro() + "</id_cadastro>\n" +
				            		"\t<login>" + c.getLogin() + "</login>\n" +
				            		"\t<senha>" + c.getSenha() + "</senha>\n" +
				            		"\t<tipo>" + c.getTipo() + "</tipo>\n" +
		            			"</cadastro>\n");
		}
		returnValue.append("</cadastro>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
	
}
