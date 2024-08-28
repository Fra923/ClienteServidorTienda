package FvModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Producto> productos;

    public Inventario() {
        this.productos = new ArrayList<>();
        cargarInventarioDesdeDB();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
        producto.crearProducto(); // Guardar el producto en la base de datos
    }

    public void editarProducto(String nombre, String categoria, int cantidad, double precio, String imagen) {
        for (Producto p : productos) {
            if (p.getNombre().equals(nombre)) {
                p.setCategoria(categoria);
                p.setCantidad(cantidad);
                p.setPrecio(precio);
                p.setImagen(imagen);
                p.actualizarProducto(); // Actualizar el producto en la base de datos
                break;
            }
        }
    }

    public String mostrarInventario() {
        StringBuilder inv = new StringBuilder();
        for (Producto p : productos) {
            inv.append("Nombre: ").append(p.getNombre())
                    .append(", Categoría: ").append(p.getCategoria())
                    .append(", Cantidad: ").append(p.getCantidad())
                    .append(", Precio: ").append(p.getPrecio())
                    .append(", Imagen: ").append(p.getImagen()).append("\n");
        }
        return inv.toString();
    }

    public void realizarCompra(String nombre, int cantidad) {
        for (Producto p : productos) {
            if (p.getNombre().equals(nombre)) {
                p.disminuirCantidad(cantidad);
                p.actualizarProducto(); // Actualizar la cantidad en la base de datos
                break;
            }
        }
    }

    public double calcularIngresos() {
        double ingresos = 0;
        for (Producto p : productos) {
            ingresos += p.getPrecio() * (p.getCantidad());
        }
        return ingresos;
    }

    public Producto buscarProducto(String nombre) {
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    // Método para cargar el inventario desde la base de datos
    private void cargarInventarioDesdeDB() {
        String sql = "SELECT * FROM Productos";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fvgames", "root", "SVfr2890210!");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int idProducto = rs.getInt("idProductos");
                String nombre = rs.getString("Producto");
                String categoria = rs.getString("Categoria");
                int cantidad = rs.getInt("Cantidad");
                double precio = rs.getDouble("Precio");

                Producto producto = new Producto(idProducto, nombre, categoria, precio, cantidad);
                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
