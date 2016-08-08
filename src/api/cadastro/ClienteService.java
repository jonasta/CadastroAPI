package api.cadastro;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import api.cadastro.dao.ClienteDAO;

@Path("/service")
public class ClienteService {
	
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

		try {
			ClienteDAO dao = new ClienteDAO();
			dao.inserir(cliente);

			return Response.ok() // 200
					.entity(cliente)
					.build();
		} catch (Exception e) {
			return Response.ok() // 200
					.entity("Erro ao inserir cliente!")
					.build();
		}

	}

	@PUT
	@Produces({ MediaType.APPLICATION_JSON })
	public Response put(Cliente cliente) {

		try {
			ClienteDAO dao = new ClienteDAO();
			dao.editar(cliente);

			return Response.ok() // 200
					.entity(cliente)
					.build();
		} catch (Exception e) {
			return Response.ok() // 200
					.entity("Erro ao alterar cliente!")
					.build();
		}

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

}
