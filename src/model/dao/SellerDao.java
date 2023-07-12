package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {
	
// ESSA É UMA INTERFACE QUE APENAS CRIA OS METÓDOS NECESSARIOS PARA A CLASSE SELLER, 
// FAZENDO INJEÇÃO DE DEPENDENCIA, CADA CLASSE TEM SUA RESPONSABILIDADE
	
	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findById(Integer id);
	List<Seller> findAll();
}
