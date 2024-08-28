package FvModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Producto {

    private int idProducto;
    private String nombre;
    private String categoria;
    private int cantidad;
    private double precio;
    private String imagen;  // Ruta de la imagen descriptiva

    // Constructor sin id para nuevos productos
    public Producto(String nombre, String categoria, double precio, int cantidad) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Producto() {
    } 

    // Constructor con id para productos existentes
    public Producto(int idProducto, String nombre, String categoria, double precio, int cantidad) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Getters y Setters
    public int getIdProducto() {
        return idProducto;
    }
    
    public String getNombre() { 
        return nombre; 
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() { 
        return categoria; 
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidad() { 
        return cantidad;
    }

    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad;
    }

    public double getPrecio() { 
        return precio; 
    }

    public void setPrecio(double precio) { 
        this.precio = precio;
    }

    public String getImagen() { 
        return imagen; 
    }

    public void setImagen(String imagen) { 
        this.imagen = imagen;
    }

    public void disminuirCantidad(int cantidad) {
        if (this.cantidad >= cantidad) {
            this.cantidad -= cantidad;
        } else {
            System.out.println("Cantidad insuficiente en inventario.");
        }
    }

    // Métodos CRUD

    // Crear un nuevo producto en la base de datos
    public void crearProducto() {
        String sql = "INSERT INTO Productos (Producto, Categoria, Cantidad, Precio, Activo) VALUES (?, ?, ?, ?, 'Si')";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.nombre);
            pstmt.setString(2, this.categoria);
            pstmt.setInt(3, this.cantidad);
            pstmt.setDouble(4, this.precio);
            pstmt.executeUpdate();
            System.out.println("Producto creado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Leer un producto de la base de datos
    public static Producto leerProducto(int idProducto) {
        String sql = "SELECT * FROM Productos WHERE idProductos = ?";
        Producto producto = null;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProducto);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("Producto");
                String categoria = rs.getString("Categoria");
                int cantidad = rs.getInt("Cantidad");
                double precio = rs.getDouble("Precio");
                producto = new Producto(idProducto, nombre, categoria, precio, cantidad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }
    
    // Leer todos los productos de la base de datos
    public static List<Producto> leerTodosProductos() {
        String sql = "SELECT * FROM Productos";
        List<Producto> lista = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idProductos");
                String nombre = rs.getString("Producto");
                String categoria = rs.getString("Categoria");
                int cantidad = rs.getInt("Cantidad");
                double precio = rs.getDouble("Precio");
                lista.add(new Producto(id, nombre, categoria, precio, cantidad));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Actualizar un producto en la base de datos
    public void actualizarProducto() {
        String sql = "UPDATE Productos SET Producto = ?, Categoria = ?, Cantidad = ?, Precio = ? WHERE idProductos = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.nombre);
            pstmt.setString(2, this.categoria);
            pstmt.setInt(3, this.cantidad);
            pstmt.setDouble(4, this.precio);
            pstmt.setInt(5, this.idProducto);
            pstmt.executeUpdate();
            System.out.println("Producto actualizado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar un producto de la base de datos
    public void eliminarProducto() {
        String sql = "DELETE FROM Productos WHERE idProductos = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.idProducto);
            pstmt.executeUpdate();
            System.out.println("Producto eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Conectar a la base de datos
    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
