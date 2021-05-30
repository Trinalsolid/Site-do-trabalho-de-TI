package Service;

import java.io.IOException;

import Dao.DAOcandidatura;
import Model.candidatura;
import spark.Request;
import spark.Response;


public class candidaturaService {

	private DAOcandidatura candidaturaDAO;

	public candidaturaService() {
		try {
			candidaturaDAO = new DAOcandidatura();
			candidaturaDAO.conectar();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public Object add(Request request, Response response) {
		int id_candidatura = candidaturaDAO.getMaxId() + 1;
		int id_vaga = Integer.parseInt(request.queryParams("id_vaga"));
		int id_profissional = Integer.parseInt(request.queryParams("id_profissional"));

		candidatura c = new candidatura(id_candidatura, id_vaga, id_profissional);

		candidaturaDAO.add(c);

		response.status(201); // 201 Created
		return id_candidatura;
	}

	public Object get(Request request, Response response) {
		int id_candidatura = Integer.parseInt(request.params(":id_candidatura"));
		
		candidatura c = (candidatura) candidaturaDAO.get(id_candidatura);
		
		if (c != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<candidatura>\n" + 
            		"\t<id_candidatura>" + c.getId_Candidatura() + "</id_candidatura>\n" +
            		"\t<id_vaga>" + c.getId_Vaga() + "</id_vaga>\n" +
            		"\t<id_profissional>" + c.getId_Profissional() + "</id_profissional>\n" +
            		"</candidatura>\n";
        } else {
            response.status(404); // 404 Not found
            return "candidatura " + id_candidatura + " não encontrado.";
        }
		
	}

	public Object update(Request request, Response response) {
		int id_candidatura = Integer.parseInt(request.params(":id_candidatura"));
        
		candidatura c = (candidatura) candidaturaDAO.get(id_candidatura);

        if (c != null) {
        	c.setId_Vaga(Integer.parseInt(request.queryParams("id_vaga")));
        	c.setId_Profissional(Integer.parseInt(request.queryParams("id_profissional")));

        	candidaturaDAO.update(c);
        	
            return id_candidatura;
        } else {
            response.status(404); // 404 Not found
            return "candidatura" + id_candidatura + " não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        int id_candidatura = Integer.parseInt(request.params(":id_candidatura"));

        candidatura c = (candidatura) candidaturaDAO.get(id_candidatura);

        if (c != null) {

            candidaturaDAO.remove(c.getId_Candidatura());

            response.status(200); // success
        	return id_candidatura;
        } else {
            response.status(404); // 404 Not found
            return "candidatura" + id_candidatura + " não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<x type=\"array\">");
		for (candidatura c : candidaturaDAO.getAll()) {
			returnValue.append("\n<candidatura>\n" + 
				            		"\t<id_candidatura>" + c.getId_Candidatura() + "</id_candidatura>\n" +
				            		"\t<id_vaga>" + c.getId_Vaga() + "</id_vaga>\n" +
				            		"\t<id_profissional>" + c.getId_Profissional() + "</id_profissional>\n" +
		            			"</candidatura>\n");
		}
		returnValue.append("</candidatura>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}
