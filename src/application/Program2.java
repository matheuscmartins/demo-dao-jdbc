package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println();
        System.out.println("\n---------------Teste 1-----------------------Department findById");
        Department department = departmentDao.findById(3);
        System.out.println(department);


        System.out.println("\n---------------Teste 2-----------------------Department findLikeName");
        System.out.print("Enter the Name or character to test: ");
        List<Department> list = departmentDao.findLikeName(scanner.nextLine());
        for (Department obj : list) {
            System.out.println(obj);
        }
        System.out.println("Total: " + list.size());

        System.out.println("\n---------------Teste 3-----------------------Department findAll");
        list = departmentDao.findAll();
        for (Department obj : list) {
            System.out.println(obj);
        }
        System.out.println("Total: " + list.size());


        System.out.println("\n---------------Teste 4-----------------------Department insert");
        Department newDepartment = new Department(null, "Music");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! New id = " + newDepartment.getId());


        System.out.println("\n---------------Teste 5-----------------------Department update");
        department = departmentDao.findById(1);
        department.setName("Food");
        departmentDao.update(department);
        System.out.println("Updated completed");

        System.out.println("\n---------------Teste 6-----------------------Department delete");
        System.out.print("Enter id number for delete test: ");
        department.setId(scanner.nextInt());
        departmentDao.deleteById(department);
        System.out.println("Delete completed");

        scanner.close();
    }
}