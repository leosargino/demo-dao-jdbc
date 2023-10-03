package model.dao;

import model.entities.Departamento;
import model.entities.Vendedor;

import java.util.List;

public interface VendedorDao {
    void insert(Vendedor vendedor);
    void update(Vendedor vendedor);
    void deleteById(Integer id);
    Vendedor findyById(Integer id);
    List<Vendedor> findAll();
    List<Vendedor> findyByDepartamento(Departamento departamento);
}
