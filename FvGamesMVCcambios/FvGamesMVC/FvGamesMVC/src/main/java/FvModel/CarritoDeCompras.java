package FvModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarritoDeCompras {
    private List<ElementoCarrito> elementos;
    private int idCliente;

    public CarritoDeCompras(int idCliente) {
        this.elementos = new ArrayList<>();
        this.idCliente = idCliente;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        ElementoCarrito elemento = new ElementoCarrito(producto, cantidad);
        elementos.add(elemento);
        elemento.guardarElementoCarrito(idCliente); // Guardar el elemento en la base de datos
    }

    public double calcularTotal() {
        double total = 0;
        for (ElementoCarrito e : elementos) {
            total += e.getTotal();
        }
        return total;
    }

    public double calcularTotalConImpuesto() {
        double total = calcularTotal();
        double impuesto = total * 0.13;
        return total + impuesto;
    }

    public void mostrarCarrito() {
        for (ElementoCarrito e : elementos) {
            System.out.println(e);
        }
    }

    public void generarFactura() {
        Factura factura = new Factura(this);
        factura.mostrarFactura();
        actualizarEstadoCarrito("Facturado"); // Actualizar el estado del carrito en la base de datos
    }

    public List<ElementoCarrito> getElementos() {
        return elementos;
    }

    // Método para actualizar el estado de los elementos del carrito en la base de datos
    private void actualizarEstadoCarrito(String nuevoEstado) {
        String sql = "UPDATE CarritoCompras SET Estado = ? WHERE idCliente = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, idCliente);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
