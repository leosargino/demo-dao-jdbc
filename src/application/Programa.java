package application;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

import java.util.List;

public class Programa {
    public static void main(String[] args) {

        VendedorDao vendedorDao = DaoFactory.createVendedorDao();

        System.out.println("=== Teste 1: vendedor FindById");
        Vendedor vendedor = vendedorDao.findyById(3);
        System.out.println(vendedor);

        System.out.println("=== Teste 2: vendedor FindByDepartamento");
        Departamento departamento = new Departamento(2,null);
        List<Vendedor> list = vendedorDao.findyByDepartamento(departamento);
        for(Vendedor vendedor1 : list){
            System.out.println(vendedor1);
        }
   }
}

