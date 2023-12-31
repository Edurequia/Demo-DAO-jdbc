package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	
// ESSA � UMA INTERFACE QUE APENAS CRIA OS MET�DOS NECESSARIOS PARA A CLASSE SELLER, 
// FAZENDO INJE��O DE DEPENDENCIA, CADA CLASSE TEM SUA RESPONSABILIDADE
	
	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findById(Integer id);
	List<Seller> findAll();
	List <Seller> findByDepartment(Department department); // EXISTE APENAS 1 DEPARTMENTO POR ISSO ELE � ASSINALADO COMO LIST
}
