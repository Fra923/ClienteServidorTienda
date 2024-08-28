package FvModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ElementoCarrito {

    private Producto producto;
    private int cantidad;

    public ElementoCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getTotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return "Producto: " + producto.getNombre() + ", Cantidad: " + cantidad + ", Precio total: " + getTotal();
    }

    // Método para guardar el elemento del carrito en la base de datos
    public void guardarElementoCarrito(int idCliente) {
        String sql = "INSERT INTO CarritoCompras (idCliente, idProducto, Estado) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCliente);
            pstmt.setInt(2, producto.getIdProducto());
            pstmt.setString(3, "Pendiente");
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
