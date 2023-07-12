package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

// ESSA � UMA INTERFACE QUE APENAS CRIA OS MET�DOS NECESSARIOS PARA A CLASSE DEPARTMENT, 
// FAZENDO INJE��O DE DEPENDENCIA, CADA CLASSE TEM SUA RESPONSABILIDADE	
	
	void insert(Department obj);
	void update(Department obj);
	void deleteById(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
}
