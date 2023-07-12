package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

// ESSA É UMA INTERFACE QUE APENAS CRIA OS METÓDOS NECESSARIOS PARA A CLASSE DEPARTMENT, 
// FAZENDO INJEÇÃO DE DEPENDENCIA, CADA CLASSE TEM SUA RESPONSABILIDADE	
	
	void insert(Department obj);
	void update(Department obj);
	void deleteById(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
}
