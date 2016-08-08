package api.cadastro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.postgresql.util.PGobject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import api.cadastro.Cliente;
import api.cadastro.PesquisaDTO;
import api.util.DBConnection;

public class ClienteDAO {
	private static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("dd/MM/yyyy")
			.create();

	private boolean isNull(String str) {
		return str == null || str.equals("");
	}

	private boolean isNull(DateTime data) {
		return data == null;
	}

	public List<Cliente> pesquisar(PesquisaDTO pesquisa) throws SQLException {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "";
		List<Cliente> result = new ArrayList<Cliente>();

		try {

			con = DBConnection.getConnection();
			sql = "Select * From Cliente";
			sql += " Where 1=1";

			if (!isNull(pesquisa.getNome()))
				sql += " And upper(data ->> 'nome') like upper(?)";

			if (!isNull(pesquisa.getEmail()))
				sql += " And upper(data ->> 'email') like upper(?)";

			if (!isNull(pesquisa.getCpf()))
				sql += " And data ->> 'cpf' like ?";

			if (!isNull(pesquisa.getDataInicio()))
				sql += " And to_date(data->>'nascimento', 'dd-MM-yyyy') >= ?";

			if (!isNull(pesquisa.getDataFim()))
				sql += " And to_date(data->>'nascimento', 'dd-MM-yyyy') <= ?";

			stmt = con.prepareStatement(sql);

			int i = 1;
			if (!isNull(pesquisa.getNome()))
				stmt.setString(i++, pesquisa.getNome() + "%");

			if (!isNull(pesquisa.getEmail()))
				stmt.setString(i++, pesquisa.getEmail() + "%");

			if (!isNull(pesquisa.getCpf()))
				stmt.setString(i++, pesquisa.getCpf() + "%");

			if (!isNull(pesquisa.getDataInicio())) {
				LocalDate ld = pesquisa.getDataInicio().toLocalDate();
				stmt.setDate(i++, new java.sql.Date(ld.toDateTimeAtStartOfDay().getMillis()));
			}

			if (!isNull(pesquisa.getDataFim())) {
				LocalDate ld = pesquisa.getDataFim().toLocalDate();
				stmt.setDate(i++, new java.sql.Date(ld.toDateTimeAtStartOfDay().getMillis()));
			}

			rs = stmt.executeQuery();
			while (rs.next()) {
				Cliente cliente = gson.fromJson(rs.getString("data"), Cliente.class);
				cliente.setId(rs.getInt("id"));
				result.add(cliente);
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			throw ex;

		} finally {

			if (con != null) {
				con.close();
				con = null;
			}
		}

		return result;
	}

	public void inserir(Cliente cliente) throws Exception {

		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "";

		try {

			con = DBConnection.getConnection();
			sql = "Insert Into Cliente(data) Values (?)";

			PGobject jsonObject = new PGobject();
			jsonObject.setType("jsonb");
			jsonObject.setValue(gson.toJson(cliente));

			stmt = con.prepareStatement(sql);
			stmt.setObject(1, jsonObject);
			stmt.execute();

		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			if (con != null) {
				con.close();
				con = null;
			}
		}
	}

	public void editar(Cliente cliente) throws Exception {

		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "";

		try {

			con = DBConnection.getConnection();
			sql = "Update Cliente Set data = ? Where id = ?";
			stmt = con.prepareStatement(sql);

			PGobject jsonObject = new PGobject();
			jsonObject.setType("jsonb");
			jsonObject.setValue(gson.toJson(cliente));
			stmt.setObject(1, jsonObject);
			stmt.setInt(2, cliente.getId());

			stmt.execute();

		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			if (con != null) {
				con.close();
				con = null;
			}
		}
	}

	public void deletar(int id) throws Exception {

		Connection con = null;
		PreparedStatement stmt = null;
		String sql = "";

		try {

			con = DBConnection.getConnection();
			sql = "Delete From Cliente Where id = ?";
			stmt = con.prepareStatement(sql);

			stmt.setInt(1, id);
			stmt.execute();

		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			if (con != null) {
				con.close();
				con = null;
			}
		}
	}

	public Cliente pesquisaPorID(int id) throws Exception {

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "";
		Cliente result = new Cliente();

		try {

			con = DBConnection.getConnection();
			sql = "Select * From Cliente";
			sql += " Where id = ?";
			stmt = con.prepareStatement(sql);

			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {

				Cliente cliente = gson.fromJson(rs.getString("data"), Cliente.class);
				cliente.setId(rs.getInt("id"));
				result = cliente;
			}

		} catch (SQLException e) {
			throw new Exception(e);
		} finally {
			if (con != null) {
				con.close();
				con = null;
			}
		}
		return result;
	}
}