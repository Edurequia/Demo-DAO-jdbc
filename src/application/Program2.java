package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("==== Teste 1: department findById =====");
		Department dep = departmentDao.findById(1);
		System.out.println(dep);
		
		System.out.println("==== Teste 2: department findAll =====");
		List <Department> list = departmentDao.findAll();
		for(Department department : list) {
			System.out.println(department);
		}
		
		System.out.println("==== Teste 3: department insert =====");
		Department newDep = new Department(null, "Tools");
		departmentDao.insert(newDep);
		System.out.println("Inserted! New id = " + newDep.getId());
		
		
		System.out.println("==== Teste 4: department update =====");
		newDep = departmentDao.findById(1);
		newDep.setName("Fashion");
		departmentDao.update(newDep);
		System.out.println("Updated! ");
		
		
		System.out.println("==== Teste 5: department delete =====");
		System.out.print("Enter id for delete: ");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Deleted! ");
		
		
		sc.close();
	}

}
