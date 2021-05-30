package Service;

import java.io.IOException;

import Dao.DAOprofissional;
import Model.profissional;
import spark.Request;
import spark.Response;

public class profissionalService {
	
	private DAOprofissional profissionalDAO;

	public profissionalService() {
		try {
			profissionalDAO = new DAOprofissional();
			profissionalDAO.conectar();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public Object add(Request request, Response response) {
		int id_profissional = profissionalDAO.getMaxId() + 1;
		String cpf = request.queryParams("cpf");
		String nome = request.queryParams("nome");
		String telefone = request.queryParams("telefone");
		String email = request.queryParams("email");
		String competencias = request.queryParams("competencias");
		int id_cadastro = Integer.parseInt(request.queryParams("id_cadastro"));
		
		profissional p = new profissional(id_profissional, nome, cpf, telefone, email, competencias, id_cadastro);
		
		profissionalDAO.add(p);

		response.status(201); // 201 Created
		return id_profissional;
	}

	public Object get(Request request, Response response) {
		int id_profissional = Integer.parseInt(request.params(":id_profissional"));

		profissional p = (profissional) profissionalDAO.get(id_profissional);

		if (p != null) {
			response.header("Content-Type", "application/xml");
			response.header("Content-Encoding", "UTF-8");

			return "<profissional>\n" + "\t<id_profissional>" + p.getId_Profissional() + "</id_profissional>\n"
					+ "\t<nome>" + p.getNome() + "</nome>\n" 
					+ "\t<cpf>" + p.getCpf() + "</cpf>\n" 
					+ "\t<telefone>" + p.getTelefone() + "</telefone>\n" 
					+ "\t<email>" + p.getEmail() + "</email>\n" 
					+ "\t<competencias>" + p.getCompetencias() + "</competencias>\n" 
					+ "\t<id_cadastro>" + p.getId_Cadastro() + "</id_cadastro>\n"
				+ "</profissional>\n";
		} else {
			response.status(404); // 404 Not found
			return "Profissional " + id_profissional + " não encontrada.";
		}

	}

	public Object update(Request request, Response response) {
		int id_profissional = Integer.parseInt(request.params(":id_profissional"));

		profissional p = (profissional) profissionalDAO.get(id_profissional);

		if (p != null) {
			p.setCpf(request.queryParams("cpf"));
			p.setNome(request.queryParams("nome"));
			p.setTelefone(request.queryParams("telefone"));
			p.setEmail(request.queryParams("email"));
			p.setCompetencias(request.queryParams("competencias"));
			p.setId_Cadastro(Integer.parseInt(request.queryParams("id_cadastro")));
			
			profissionalDAO.update(p);

			return id_profissional;
		} else {
			response.status(404); // 404 Not found
			return "Profissional" + id_profissional + " não encontrado.";
		}

	}

	public Object remove(Request request, Response response) {
		int id_profissional = Integer.parseInt(request.params(":id_profissional"));

		profissional p = (profissional) profissionalDAO.get(id_profissional);

		if (p != null) {

			profissionalDAO.remove(p.getId_Profissional());

			response.status(200); // success
			return id_profissional;
		} else {
			response.status(404); // 404 Not found
			return "Profissional" + id_profissional + " não encontrado.";
		}
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<x type=\"array\">");
		for (profissional e : profissionalDAO.getAll()) {
			returnValue.append("<profissional>\n" + "\t<id_profissional>" + e.getId_Profissional() + "</id_profissional>\n"
								+ "\t<nome>" + e.getNome() + "</nome>\n" 
								+ "\t<cpf>" + e.getCpf() + "</cpf>\n" 
								+ "\t<telefone>" + e.getTelefone() + "</telefone>\n" 
								+ "\t<email>" + e.getEmail() + "</email>\n" 
								+ "\t<competencias>" + e.getCompetencias() + "</competencias>\n" 
								+ "\t<id_cadastro>" + e.getId_Cadastro() + "</id_cadastro>\n"
							+ "</profissional>\n");
					}
		returnValue.append("</profissional>");
		response.header("Content-Type", "application/xml");
		response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}
