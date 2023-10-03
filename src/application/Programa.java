package application;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Vendedor;

public class Programa {
    public static void main(String[] args) {

        VendedorDao vendedorDao = DaoFactory.createVendedorDao();

        System.out.println("=== Teste 1: vendedor FindById");
        Vendedor vendedor = vendedorDao.findyById(3);

        System.out.println(vendedor);
    }
}

