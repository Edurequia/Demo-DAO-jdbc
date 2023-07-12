package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
// ESSA CLASSE IMPLEMENTA A INTERFACE SELLERDAO E A UTILIDADE DELA É A CONEXAO DOS METODOS
// E DA CLASSE COM O BANCO DE DADOS
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		
		
	}

	@Override
	public void update(Seller obj) {
		
		
	}

	@Override
	public void deleteById(Integer id) {
		Statement st = null;
		try {
			st = conn.createStatement();
			
			st.executeUpdate("DELETE FROM seller WHERE DepartmentId = ? ");
			
			((PreparedStatement) st).setInt(1, id);
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
	
	// ESSE METODO PROCURA O ID QUE O USUARIO DIGITAR DO VENDEDOR LA DO BANCO DE DADOS 
	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.name as DepName " // Seleciona todas colunas dos vendedores e as colunas dos nomes dos departamentos
					+ "FROM seller INNER JOIN department "     // Faz a junção das duas tabelas
					+ "ON seller.DepartmentId = department.id " // Compara a coluna departmentID com o id do departamento
					+ "WHERE seller.id = ?"); // Filtra o resultado para o id que o usuário digitar
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	// FUNÇÃO PARA INSTANCIAR O VENDEDOR E REUTILIZAR QUANDO PRECISA
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);
		return obj;
	}

	// FUNÇÃO PARA INSTANCIAR O DEPARTAMENTO E REUTILIZAR QUANDO PRECISA
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		
		return null;
	}

}
