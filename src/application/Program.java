package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println();
        System.out.println("---------------Teste 1-----------------------seller findById");
        Seller seller = sellerDao.findById(3);

        System.out.println(seller);

        System.out.println("\n---------------Teste 2-----------------------seller findByDepartment");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj : list) {
            System.out.println(obj);
        }
        System.out.println("Total: " + list.size());

        System.out.println("\n---------------Teste 3-----------------------Seller findAll");
         list = sellerDao.findAll();
        for (Seller obj : list) {
            System.out.println(obj);
        }
        System.out.println("Total: " + list.size());

        System.out.println("\n---------------Teste 4-----------------------Seller insert");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(),4000.00,department);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());

        System.out.println("\n---------------Teste 5-----------------------Seller update");
        seller = sellerDao.findById(1);
        seller.setName("Marta Waine");
        seller.setEmail("marta@gmail.com");
        sellerDao.update(seller);
        System.out.println("Updated completed");

        System.out.println("\n---------------Teste 6-----------------------Seller delete");
        System.out.print("Enter id number for delete test: ");
        seller.setId(scanner.nextInt());
        sellerDao.deleteById(seller);
        System.out.println("Delete completed");

        scanner.close();
    }
}