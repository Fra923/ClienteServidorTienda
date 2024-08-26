package FvController;

import FvModel.ClienteRegistrado;
import FvModel.Inventario;
import FvView.ClienteVista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClienteControlador {
    private ClienteRegistrado cliente;
    private Inventario inventario;
    private ClienteVista vista;

    public ClienteControlador(ClienteRegistrado cliente, Inventario inventario, ClienteVista vista) {
        this.cliente = cliente;
        this.inventario = inventario;
        this.vista = vista;

        this.vista.getVerCarritoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cliente.visualizarCarrito();
            }
        });

        this.vista.getFinalizarCompraButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cliente.finalizarCompra(inventario);
            }
        });
    }

    public void iniciar() {
        vista.mostrar();
        // Cargar productos en la vista
    }
}

