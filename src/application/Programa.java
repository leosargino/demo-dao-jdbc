package application;

import model.entities.Departamento;
import model.entities.Vendedor;

import java.util.Date;

public class Programa {
    public static void main(String[] args) {
        Departamento departamento = new Departamento(1,"Books");

        Vendedor vendedor = new Vendedor(1,"Bob","bob@gmail.com",new Date(),3000,departamento);

        System.out.println(vendedor);
    }
}

