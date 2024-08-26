package com.mycompany.main;

import FvModel.ClienteRegistrado;
import FvController.GestionClientes;
import FvModel.Inventario;
import FvModel.Producto;
import java.util.Scanner;

public class FvGamesMVC {

    public static void main(String[] args) {
        GestionClientes gestionClientes = new GestionClientes();
        
        
    }
}








/*


       GestionClientes gestionClientes = new GestionClientes();
        Inventario inventario = new Inventario();
        Scanner scanner = new Scanner(System.in);

        // Agregar productos al inventario
        inventario.agregarProducto(new Producto("PlayStation 5", "Consolas", 499.99, 10));
        inventario.agregarProducto(new Producto("Xbox Series X", "Consolas", 499.99, 8));
        inventario.agregarProducto(new Producto("Nintendo Switch", "Consolas", 299.99, 15));
        inventario.agregarProducto(new Producto("The Last of Us Part II", "Videojuegos", 59.99, 20));
        inventario.agregarProducto(new Producto("Cyberpunk 2077", "Videojuegos", 59.99, 5));

        // Registro de un cliente y un administrador
        gestionClientes.registrarCliente("Juan", "Perez", "1234567890", "Calle Falsa 123", "juan.perez@example.com",1000.0, "Tarjeta de crédito");
        gestionClientes.registrarAdministrador("Admin", "0987654321", "admin123");

        // Iniciar sesión como cliente registrado
        ClienteRegistrado cliente = gestionClientes.iniciarSesionCliente("1234567890");
        if (cliente != null) {
            // Visualizar productos
            cliente.visualizarProductos(inventario);

            // Agregar productos al carrito
            cliente.agregarProductoAlCarrito(inventario, "PlayStation 5", 1);
            cliente.agregarProductoAlCarrito(inventario, "Cyberpunk 2077", 1);

            // Visualizar carrito
            cliente.visualizarCarrito();

            // Finalizar compra
            cliente.finalizarCompra(inventario);
            
        }
        
        if (cliente != null){
            cliente.visualizarProductos(inventario);
        }

*/