package Service;

import java.io.IOException;

import Dao.DAOvaga;
import Model.vaga;
import spark.Request;
import spark.Response;

public class vagaService {

	private DAOvaga vagaDAO;

	public vagaService() {
		try {
			vagaDAO = new DAOvaga();
			vagaDAO.conectar();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public Object add(Request request, Response response) {
		int id_vaga = vagaDAO.getMaxId() + 1;
		String descricao = request.queryParams("descricao");
		String tipo = request.queryParams("tipo");
		String requisitos = request.queryParams("requisitos");
		int id_empresa = Integer.parseInt(request.queryParams("id_empresa"));

		vaga v = new vaga(id_vaga, descricao, requisitos, tipo,id_empresa);

		vagaDAO.add(v);

		response.status(201); // 201 Created
		return id_vaga;
	}

	public Object get(Request request, Response response) {
		int id_vaga = Integer.parseInt(request.params(":id_vaga"));
		
		vaga v = (vaga) vagaDAO.get(id_vaga);
		
		if (v != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<vaga>\n" + 
            		"\t<id_vaga>" + v.getId_Vaga() + "</id_vaga>\n" +
            		"\t<descricao>" + v.getDescricao() + "</descricao>\n" +
            		"\t<requisitos>" + v.getRequisitos() + "</requisitos>\n" +
            		"\t<tipo>" + v.getTipo() + "</tipo>\n" +
            		"\t<id_empresa>" + v.getId_Empresa() + "</id_empresa>\n" +
            		"</vaga>\n";
        } else {
            response.status(404); // 404 Not found
            return "Vaga " + id_vaga + " não encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id_vaga = Integer.parseInt(request.params(":id_vaga"));
        
		vaga v = (vaga) vagaDAO.get(id_vaga);

        if (v != null) {
        	v.setDescricao(request.queryParams("descricao"));
        	v.setRequisitos(request.queryParams("requisitos"));
        	v.setTipo(request.queryParams("tipo"));
        	v.setId_Empresa(Integer.parseInt(request.queryParams("tipo")));

        	vagaDAO.update(v);
        	
            return id_vaga;
        } else {
            response.status(404); // 404 Not found
            return "Vaga" + id_vaga + " não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        int id_vaga = Integer.parseInt(request.params(":id_vaga"));

        vaga v = (vaga) vagaDAO.get(id_vaga);

        if (v != null) {

            vagaDAO.remove(v.getId_Vaga());

            response.status(200); // success
        	return id_vaga;
        } else {
            response.status(404); // 404 Not found
            return "Vaga" + id_vaga + " não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<x type=\"array\">");
		for (vaga v : vagaDAO.getAll()) {
			returnValue.append("<vaga>\n" + 
			            		"\t<id_vaga>" + v.getId_Vaga() + "</id_vaga>\n" +
			            		"\t<descricao>" + v.getDescricao() + "</descricao>\n" +
			            		"\t<requisitos>" + v.getRequisitos() + "</requisitos>\n" +
			            		"\t<tipo>" + v.getTipo() + "</tipo>\n" +
			            		"\t<id_empresa>" + v.getId_Empresa() + "</id_empresa>\n" +
			            		"</vaga>\n");
		}
		returnValue.append("</vaga>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}
