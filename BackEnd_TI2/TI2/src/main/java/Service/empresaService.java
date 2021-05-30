package Service;

import java.io.IOException;

import Dao.DAOempresa;
import Model.empresa;
import spark.Request;
import spark.Response;

public class empresaService {
	
	private DAOempresa empresaDAO;

	public empresaService() {
		try {
			empresaDAO = new DAOempresa();
			empresaDAO.conectar();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public Object add(Request request, Response response) {
		int id_empresa = empresaDAO.getMaxId() + 1;
		String nome = request.queryParams("nome");
		String cnpj = request.queryParams("cnpj");
		String telefone = request.queryParams("telefone");
		String email = request.queryParams("email");
		String site = request.queryParams("site");
		int id_cadastro = Integer.parseInt(request.queryParams("id_cadastro"));
		
		empresa e = new empresa(id_empresa, nome, cnpj, telefone, email, site, id_cadastro);
		
		empresaDAO.add(e);

		response.status(201); // 201 Created
		return id_empresa;
	}

	public Object get(Request request, Response response) {
		int id_empresa = Integer.parseInt(request.params(":id_empresa"));

		empresa e = (empresa) empresaDAO.get(id_empresa);

		if (e != null) {
			response.header("Content-Type", "application/xml");
			response.header("Content-Encoding", "UTF-8");

			return "<empresa>\n" + "\t<id_empresa>" + e.getId_Empresa() + "</id_empresa>\n"
					+ "\t<nome>" + e.getNome() + "</nome>\n" 
					+ "\t<cnpj>" + e.getCnpj() + "</cnpj>\n" 
					+ "\t<telefone>" + e.getTelefone() + "</telefone>\n" 
					+ "\t<email>" + e.getEmail() + "</email>\n" 
					+ "\t<site>" + e.getSite() + "</site>\n" 
					+ "\t<id_cadastro>" + e.getId_Cadastro() + "</id_cadastro>\n"
				+ "</empresa>\n";
		} else {
			response.status(404); // 404 Not found
			return "Empresa " + id_empresa + " não encontrada.";
		}

	}

	public Object update(Request request, Response response) {
		int id_empresa = Integer.parseInt(request.params(":id_empresa"));

		empresa e = (empresa) empresaDAO.get(id_empresa);

		if (e != null) {
			e.setNome(request.queryParams("nome"));
			e.setCnpj(request.queryParams("cnpj"));
			e.setTelefone(request.queryParams("telefone"));
			e.setEmail(request.queryParams("email"));
			e.setSite(request.queryParams("site"));
			e.setId_Cadastro(Integer.parseInt(request.queryParams("id_cadastro")));
			
			empresaDAO.update(e);

			return id_empresa;
		} else {
			response.status(404); // 404 Not found
			return "Empresa" + id_empresa + " não encontrado.";
		}

	}

	public Object remove(Request request, Response response) {
		int id_empresa = Integer.parseInt(request.params(":id_empresa"));

		empresa e = (empresa) empresaDAO.get(id_empresa);

		if (e != null) {

			empresaDAO.remove(e.getId_Empresa());

			response.status(200); // success
			return id_empresa;
		} else {
			response.status(404); // 404 Not found
			return "Empresa" + id_empresa + " não encontrado.";
		}
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<x type=\"array\">");
		for (empresa e : empresaDAO.getAll()) {
			returnValue.append("<empresa>\n" + "\t<id_empresa>" + e.getId_Empresa() + "</id_empresa>\n"
								+ "\t<nome>" + e.getNome() + "</nome>\n" 
								+ "\t<cnpj>" + e.getCnpj() + "</cnpj>\n" 
								+ "\t<telefone>" + e.getTelefone() + "</telefone>\n" 
								+ "\t<email>" + e.getEmail() + "</email>\n" 
								+ "\t<site>" + e.getSite() + "</site>\n" 
								+ "\t<id_cadastro>" + e.getId_Cadastro() + "</id_cadastro>\n"
							+ "</empresa>\n");
					}
		returnValue.append("</empresa>");
		response.header("Content-Type", "application/xml");
		response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}
