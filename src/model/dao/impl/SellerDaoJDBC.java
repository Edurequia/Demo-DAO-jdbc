package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "VALUES (?,?,?,?,?) ", 
					Statement.RETURN_GENERATED_KEYS);
					
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			 // st.setDate(3, java.sql.Date(obj.getBirthDate()));
			st.setString(4, obj.getName());
			st.setString(5, obj.getName());
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Seller obj) {
		
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM seller WHERE DepartmentId = ? ");
			
			st.setInt(1, id);
			
			int rowsAffected = st.executeUpdate();
			System.out.println("Done! Rows affected: " + rowsAffected);
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
	
	// ESSE METODO PROCURA O ID DO VENDEDOR QUE O USUARIO DIGITAR
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
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name ");
		rs = st.executeQuery();
		
		List<Seller> list = new ArrayList<>();
		Map<Integer, Department> map = new HashMap<>();
		
		while (rs.next()) {
			Department dep = map.get(rs.getInt("DepartmentId"));
			
			if(dep == null) {
				dep = instantiateDepartment(rs);
				map.put(rs.getInt("DepartmentId"), dep);
			}
			
			Seller obj = instantiateSeller(rs, dep);
			list.add(obj);
		}
		
		return list;	
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	// EXISTE APENAS 1 DEPARTMENTO POR ISSO ELE É ASSINALADO COMO LIST
	// ESSE METODO PROCURA O DEPARTAMENTO QUE O USUARIO DIGITAR 
	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name ");
			
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			
			List <Seller> list = new ArrayList<>();
			
			while (rs.next()) {			
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep); 
				list.add(obj);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
