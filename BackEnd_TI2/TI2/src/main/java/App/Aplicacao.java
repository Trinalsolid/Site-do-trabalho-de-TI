package App;

import static spark.Spark.*;

import Service.cadastroService;
import Service.candidaturaService;
import Service.empresaService;
import Service.profissionalService;
import Service.vagaService;

public class Aplicacao {
	
	private static cadastroService cadService = new cadastroService();
	private static candidaturaService canService = new candidaturaService();
	private static empresaService empService = new empresaService();
	private static profissionalService proService = new profissionalService();
	private static vagaService vagaService = new vagaService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        	
        post("/cadastroUsuario", (request, response) -> cadService.add(request, response ,true));
        post("/cadastroEmpresa", (request, response) -> cadService.add(request, response ,false));
        post("/cadastro/LoginUsuario", (request, response) -> cadService.login(request, response ,true));
        post("/cadastro/LoginEmpresa", (request, response) -> cadService.login(request, response ,false));
        
        get("/cadastro/:id", (request, response) -> cadService.get(request, response));
        get("/cadastro/update/:id", (request, response) -> cadService.update(request, response));
        get("/cadastro/delete/:id", (request, response) -> cadService.remove(request, response));
        get("/cadastro", (request, response) -> cadService.getAll(request, response));
        
        post("/candidatura", (request, response) -> canService.add(request, response));
        get("/candidatura/:id", (request, response) -> canService.get(request, response));
        get("/candidatura/update/:id", (request, response) -> canService.update(request, response));
        get("/candidatura/delete/:id", (request, response) -> canService.remove(request, response));
        get("/candidatura", (request, response) -> canService.getAll(request, response));
        
        post("/empresa", (request, response) -> empService.add(request, response));
        get("/empresa/:id", (request, response) -> empService.get(request, response));
        get("/empresa/update/:id", (request, response) -> empService.update(request, response));
        get("/empresa/delete/:id", (request, response) -> empService.remove(request, response));
        get("/empresa", (request, response) -> empService.getAll(request, response));
        
        post("/profissional", (request, response) -> proService.add(request, response));
        get("/profissional/:id", (request, response) -> proService.get(request, response));
        get("/profissional/update/:id", (request, response) -> proService.update(request, response));
        get("/profissional/delete/:id", (request, response) -> proService.remove(request, response));
        get("/profissional", (request, response) -> proService.getAll(request, response));
        
        post("/vaga", (request, response) -> vagaService.add(request, response));
        get("/vaga/:id", (request, response) -> vagaService.get(request, response));
        get("/vaga/update/:id", (request, response) -> vagaService.update(request, response));
        get("/vaga/delete/:id", (request, response) -> vagaService.remove(request, response));
        get("/vaga", (request, response) -> vagaService.getAll(request, response));
               
    }
}