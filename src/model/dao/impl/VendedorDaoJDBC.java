package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendedorDaoJDBC implements VendedorDao {

    private Connection connection;

    public VendedorDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Vendedor vendedor) {

        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement("INSERT INTO vendedor (Nome, Email, DataNascimento, BaseSalarial, DepartamentoId) VALUE (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,vendedor.getNome());
            preparedStatement.setString(2,vendedor.getEmail());
            preparedStatement.setDate(3,new java.sql.Date(vendedor.getDataNascimento().getTime()));
            preparedStatement.setDouble(4,vendedor.getBaseSalarial());
            preparedStatement.setInt(5,vendedor.getDepartamento().getId());

            int linhasAlteradas = preparedStatement.executeUpdate();

            if(linhasAlteradas > 0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    int id = resultSet.getInt(1);
                    vendedor.setId(id);
                }
                DB.closeResultSet(resultSet);
            }else{
                throw new DbException("Erro: Nenhuma linha foi alterada!");
            }
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(Vendedor vendedor) {

    }

    @Override
    public void deleteById(Vendedor vendedor) {

    }

    @Override
    public Vendedor findyById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement("SELECT v.*, d.Nome AS NomeDep\n" +
                    "FROM vendedor v\n" +
                    "INNER JOIN departamento d ON v.DepartamentoId = d.Id\n" +
                    "WHERE v.Id = ?;");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Departamento departamento = instantiateDepartamento(rs);
                Vendedor vendedor = instantiateVendedor(rs, departamento);
                return vendedor;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Vendedor> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement("SELECT v.*, d.Nome AS NomeDep\n" +
                    "FROM vendedor v\n" +
                    "INNER JOIN departamento d ON v.DepartamentoId = d.Id\n" +
                    "ORDER BY v.Nome;");

            rs = st.executeQuery();
            List<Vendedor> list = new ArrayList<>();
            Map<Integer, Departamento> map = new HashMap<>();

            while (rs.next()) {
                Departamento departamento1 = map.get(rs.getInt("DepartamentoId"));
                if (departamento1 == null) {
                    departamento1 = instantiateDepartamento(rs);
                    map.put(rs.getInt("DepartamentoId"), departamento1);
                }
                Vendedor vendedor = instantiateVendedor(rs, departamento1);
                list.add(vendedor);
            }
            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Vendedor> findyByDepartamento(Departamento departamento) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement("SELECT v.*, d.Nome AS NomeDep\n" +
                    "FROM vendedor v\n" +
                    "INNER JOIN departamento d ON v.DepartamentoId = d.Id\n" +
                    "WHERE v.DepartamentoId = ?\n" +
                    "ORDER BY v.Nome;");
            st.setInt(1, departamento.getId());
            rs = st.executeQuery();
            List<Vendedor> list = new ArrayList<>();
            Map<Integer, Departamento> map = new HashMap<>();

            while (rs.next()) {
                Departamento departamento1 = map.get(rs.getInt("DepartamentoId"));
                if (departamento1 == null) {
                    departamento1 = instantiateDepartamento(rs);
                    map.put(rs.getInt("DepartamentoId"), departamento1);
                }
                Vendedor vendedor = instantiateVendedor(rs, departamento1);
                list.add(vendedor);
            }
            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Vendedor instantiateVendedor(ResultSet rs, Departamento departamento) throws SQLException {
        Vendedor vendedor = new Vendedor();
        vendedor.setId(rs.getInt("Id"));
        vendedor.setNome(rs.getString("Nome"));
        vendedor.setEmail(rs.getString("Email"));
        vendedor.setBaseSalarial(rs.getDouble("BaseSalarial"));
        vendedor.setDataNascimento(rs.getDate("DataNascimento"));
        vendedor.setDepartamento(departamento);
        return vendedor;
    }

    private Departamento instantiateDepartamento(ResultSet rs) throws SQLException {
        Departamento departamento = new Departamento();
        departamento.setId(rs.getInt("DepartamentoId"));
        departamento.setNome(rs.getString("NomeDep"));
        return departamento;
    }
}
