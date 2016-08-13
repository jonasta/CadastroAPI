package api.cadastro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import api.cadastro.dao.ClienteDAO;
import api.util.ToolUtils;

@Path("/service")
public class ClienteService {
	
	
	private static final ToolUtils tools = new ToolUtils();
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response pesquisa() {

		try {
			ClienteDAO dao = new ClienteDAO();
			List<Cliente> clientes = dao.pesquisar(new PesquisaDTO());
			return Response.ok() // 200
					.entity(clientes)
					.build();
		} catch (Exception e) {
			return Response.ok() // 200
					.entity("Erro ao solicitar clientes!")
					.build();
		}

	}
	
	@GET
	@Path("/tester")
	@Produces({ MediaType.APPLICATION_JSON })
	public String tester() {

		return "mensagem de teste bem sucedido!";

	}
	
	
	@GET
	@Path("/pesquisaPorId")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response pesquisa(@QueryParam("id") int id) {

		try {
			ClienteDAO dao = new ClienteDAO();
			Cliente cliente = dao.pesquisaPorID(id);

			return Response.ok() // 200
					.entity(cliente)
					.build();
		} catch (Exception e) {
			return Response.ok() // 200
					.entity("Erro ao solicitar cliente!")
					.build();
		}
		
	}
	
	@POST
	@Path("/pesquisa")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response pesquisa(PesquisaDTO dto) {

		try {
			ClienteDAO dao = new ClienteDAO();
			List<Cliente> clientes = dao.pesquisar(dto);
			return Response.ok() // 200
					.entity(clientes)
					.build();
		} catch (Exception e) {
			return Response.ok() // 200
					.entity("Erro ao solicitar clientes!")
					.build();
		}

	}

	
	@POST
	@Path("/pesquisa/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response pesquisaPorId(@PathParam("id") int id) {

		try {
			ClienteDAO dao = new ClienteDAO();
			Cliente cliente = dao.pesquisaPorID(id);

			return Response.ok() // 200
					.entity(cliente)
					.build();
		} catch (Exception e) {
			return Response.ok() // 200
					.entity("Erro ao excluir cliente!")
					.build();
		}

	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response post(Cliente cliente) {

		ClienteCadastroDTO retorno = new ClienteCadastroDTO();
		
		try {
			
			String validacoes = validarCliente(cliente); 
			
			if(!tools.isNull(validacoes))
				retorno.setMessage(validacoes);
			else{
				
				ClienteDAO dao = new ClienteDAO();
				dao.inserir(cliente);
				
				retorno.setSuccess(true);
				retorno.setMessage("Cliente salvo com sucesso!");
				retorno.setCliente(cliente);
			}
			
		} catch (Exception e) {
			retorno.setMessage("Ocorreu um erro ao salvar o Cliente!");
			retorno.setSuccess(false);
		}

		return Response.ok().entity(retorno).build();
	}

	@PUT
	@Produces({ MediaType.APPLICATION_JSON })
	public Response put(Cliente cliente) {
		
		ClienteCadastroDTO retorno = new ClienteCadastroDTO();
		
		try {
			
			String validacoes = validarCliente(cliente); 
			
			if(!tools.isNull(validacoes))
				retorno.setMessage(validacoes);
			else{
				
				ClienteDAO dao = new ClienteDAO();
				dao.editar(cliente);
				
				retorno.setSuccess(true);
				retorno.setMessage("Cliente salvo com sucesso!");
				retorno.setCliente(cliente);
			}
			
		} catch (Exception e) {
			retorno.setMessage("Ocorreu um erro ao salvar o Cliente!");
			retorno.setSuccess(false);
		}

		return Response.ok().entity(retorno).build();
	}

	
	
	@DELETE
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response delete(@PathParam("id") int id) {

		try {
			ClienteDAO dao = new ClienteDAO();
			dao.deletar(id);

			return Response.ok() // 200
					.entity("OK")
					.build();
		} catch (Exception e) {
			return Response.ok() // 200
					.entity("Erro ao excluir cliente!")
					.build();
		}

	}
	
	private String validarCliente(Cliente cliente) throws Exception{
		
		List<String> validacoes = new ArrayList<String>();
		
		if(tools.isNull(cliente.getNome()))
			validacoes.add(" { Nome } ");
		
		if(tools.isNull(cliente.getEmail()))
			validacoes.add(" { Email } ");
		
		if(tools.isNull(cliente.getCpf()))
			validacoes.add(" { Cpf } ");
		
		if(tools.isNull(cliente.getTelefone()))
			validacoes.add(" { Telefone } ");
		
		if(cliente.getNascimento() == null)
			validacoes.add(" { Nascimento } ");
		
		if(validacoes.size() > 0){
			String msg = "Informe os campos obrigatórios:";
			for(String str : validacoes)
				msg += str;
			return msg;
		} 
		else if(!tools.validaCpf(cliente.getCpf())) 
			return "O Cpf informado não é válido!";
		else if(!tools.validaEmail(cliente.getEmail()))
			return "O Email informado não é válido!";
		else if(cliente.getNascimento().after(new Date()))
			return "A Data de Nascimento deve ser inferior a hoje!";
		else{
			
			ClienteDAO dao = new ClienteDAO();
			Cliente clienteExistente = dao.pesquisaPorCpf(cliente.getCpf(), cliente.getId());
			if(clienteExistente != null)
				return "O Cpf informado já está cadastrado!";
		}
		
		return null;
	}

}
