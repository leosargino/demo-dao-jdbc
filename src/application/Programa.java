package application;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

import java.util.Date;
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
        System.out.println("=== Teste 3: vendedor FindAll");
        list = vendedorDao.findAll();
        for(Vendedor vendedor1 : list){
            System.out.println(vendedor1);
        }
        System.out.println();
        System.out.println("=== Teste 3: vendedor Insert");
        Vendedor novoVendedor = new Vendedor(null,"Greg", "greg@gmail.com", new Date(), 4000, departamento);
        vendedorDao.insert(novoVendedor);
        System.out.println("Inserido! novo id = " + novoVendedor.getId());
   }
}

