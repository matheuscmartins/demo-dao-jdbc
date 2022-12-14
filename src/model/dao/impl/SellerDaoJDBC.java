package model.dao.impl;

import Db.DB;
import Db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;
    public SellerDaoJDBC(Connection conn){
        this.connection= conn;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Seller seller) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    +"FROM seller INNER JOIN department "
                    +"ON seller.DepartmentId = department.Id "
                    +"WHERE seller.Id = ?"
            );
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()){

                Department objDepartment = new Department();
                objDepartment.setId(resultSet.getInt("DepartmentId"));
                objDepartment.setName(resultSet.getString("DepName"));
                Seller objSeller = new Seller();
                objSeller.setId(resultSet.getInt("Id"));
                objSeller.setName(resultSet.getString("Name"));
                objSeller.setEmail(resultSet.getString("Email"));
                objSeller.getBaseSalary(resultSet.getDouble("BaseSalary"));
                objSeller.setBirthDate(resultSet.getDate("BirthDate"));
                objSeller.setDepartment(objDepartment);
                return objSeller;
            }
            return null;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }

    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
