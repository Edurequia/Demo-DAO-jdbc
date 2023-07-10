package application;

import java.util.Date;

import model.dao.SellerDao;
import model.dao.daoFactory;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department obj = new Department(1, "books");
		System.out.println(obj);
		
		Seller seller = new Seller(2, "Bob", "bob@gmail.com", new Date(), 3000.0, obj);
		System.out.println(seller);
		
		SellerDao sellerDao = daoFactory.createSellerDao();
	}

}
