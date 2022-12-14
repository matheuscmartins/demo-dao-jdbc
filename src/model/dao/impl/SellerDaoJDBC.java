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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                Department objDepartment = instantiateDepartment(resultSet);
                Seller objSeller = instantiateSeller(resultSet,objDepartment);
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

    private Seller instantiateSeller(ResultSet resultSet, Department objDepartment) throws SQLException {
        Seller objSeller = new Seller();
        objSeller.setId(resultSet.getInt("Id"));
        objSeller.setName(resultSet.getString("Name"));
        objSeller.setEmail(resultSet.getString("Email"));
        objSeller.getBaseSalary(resultSet.getDouble("BaseSalary"));
        objSeller.setBirthDate(resultSet.getDate("BirthDate"));
        objSeller.setDepartment(objDepartment);
        return objSeller;
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
       Department objDepartment=  new Department();
        objDepartment.setId(resultSet.getInt("DepartmentId"));
        objDepartment.setName(resultSet.getString("DepName"));
        return objDepartment;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name");

            resultSet = preparedStatement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (resultSet.next()) {            //enquanto o ResultSet tiver linha

                Department objDepartment = map.get(resultSet.getInt("DepartmentId"));
                //obtem os Department que estão no map sejam eles existentes ou null

                if (objDepartment == null) {        //se o o Department for null

                    objDepartment = instantiateDepartment(resultSet);   //função para obter um Department do banco

                    map.put(resultSet.getInt("DepartmentId"), objDepartment);
                    //adiciona Department que não existe no map para ser reutilizado

                }

                Seller objSeller = instantiateSeller(resultSet, objDepartment);
                list.add(objSeller);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }
    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name");

            preparedStatement.setInt(1, department.getId());

            resultSet = preparedStatement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (resultSet.next()) {            //enquanto o ResultSet tiver linha

                Department objDepartment = map.get(resultSet.getInt("DepartmentId"));
                //obtem os Department que estão no map sejam eles existentes ou null

                if (objDepartment == null) {        //se o o Department for null

                    objDepartment = instantiateDepartment(resultSet);   //função para obter um Department do banco

                    map.put(resultSet.getInt("DepartmentId"), objDepartment);
                    //adiciona Department que não existe no map para ser reutilizado

                }

                Seller objSeller = instantiateSeller(resultSet, objDepartment);
                list.add(objSeller);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

}
