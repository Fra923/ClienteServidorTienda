package FvModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Paquete {
    private int idPaquete;
    private String nombre;
    private String productos;
    private double descuento;

    // Constructor para crear un paquete nuevo
    public Paquete(String nombre, String productos, double descuento) {
        String[] splitted = productos.split(",");
        if (splitted.length< 2 || splitted.length > 5) {
            throw new IllegalArgumentException("El paquete debe contener entre 2 y 5 productos.");
        }
        this.nombre = nombre;
        this.productos = productos;
        this.descuento = descuento;
    }

    // Constructor para cargar un paquete existente
    public Paquete(int idPaquete, String nombre, String productos, double descuento) {
        this.idPaquete = idPaquete;
        this.nombre = nombre;
        this.productos = productos;
        this.descuento = descuento;
    }

    // Getters y Setters
    public int getIdPaquete() {
        return idPaquete;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProductos() {
        return productos;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double calcularPrecioTotal() {
        double total = 0;
//        for (Producto p : productos) {
//            total += p.getPrecio();
//        }
        return total * (1 - descuento / 100);
    }

    @Override
    public String toString() {
        return "Paquete{" +
                "idPaquete=" + idPaquete +
                ", nombre='" + nombre + '\'' +
                ", productos=" + productos +
                ", descuento=" + descuento +
                ", precioTotal=" + calcularPrecioTotal() +
                '}';
    }

    // Métodos CRUD

    // Crear un nuevo paquete en la base de datos
    public void crearPaquete() {
        String sqlPaquete = "INSERT INTO Paquetes (NombrePaquete, Descuento) VALUES (?, ?)";
        String sqlProductoPaquete = "INSERT INTO ProductoPaquete (idPaquete, idProducto) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmtPaquete = conn.prepareStatement(sqlPaquete, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmtProductoPaquete = conn.prepareStatement(sqlProductoPaquete)) {

            // Insertar el paquete
            pstmtPaquete.setString(1, this.nombre);
            pstmtPaquete.setDouble(2, this.descuento);
            pstmtPaquete.executeUpdate();

            // Obtener el ID generado para el paquete
            ResultSet generatedKeys = pstmtPaquete.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.idPaquete = generatedKeys.getInt(1);
            }

            // Insertar los productos asociados al paquete
//            for (Producto producto : productos) {
//                pstmtProductoPaquete.setInt(1, this.idPaquete);
//                pstmtProductoPaquete.setInt(2, producto.getIdProducto());
//                pstmtProductoPaquete.executeUpdate();
//            }

            System.out.println("Paquete creado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Leer un paquete de la base de datos
    public static Paquete leerPaquete(int idPaquete) {
        String sqlPaquete = "SELECT * FROM Paquetes WHERE idPaquete = ?";
        String sqlProductos = "SELECT p.* FROM Productos p INNER JOIN ProductoPaquete pp ON p.idProductos = pp.idProducto WHERE pp.idPaquete = ?";
        Paquete paquete = null;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmtPaquete = conn.prepareStatement(sqlPaquete);
             PreparedStatement pstmtProductos = conn.prepareStatement(sqlProductos)) {

            // Obtener los detalles del paquete
            pstmtPaquete.setInt(1, idPaquete);
            ResultSet rsPaquete = pstmtPaquete.executeQuery();

            if (rsPaquete.next()) {
                String nombre = rsPaquete.getString("NombrePaquete");
                double descuento = rsPaquete.getDouble("Descuento");

                // Obtener los productos asociados al paquete
                pstmtProductos.setInt(1, idPaquete);
                ResultSet rsProductos = pstmtProductos.executeQuery();
                List<Producto> productos = new ArrayList<>();
                String productosString = "";

                while (rsProductos.next()) {
                    int idProducto = rsProductos.getInt("idProductos");
                    String nombreProducto = rsProductos.getString("Producto");
                    String categoria = rsProductos.getString("Categoria");
                    int cantidad = rsProductos.getInt("Cantidad");
                    double precio = rsProductos.getDouble("Precio");
                    String imagen = rsProductos.getString("imagen");
                    
                    productosString = productosString + idProducto + ",";
                    Producto producto = new Producto(idProducto, nombreProducto, categoria, precio, cantidad);
                    productos.add(producto);
                }
                productosString = productosString.substring(0, productosString.length() - 1);

                paquete = new Paquete(idPaquete, nombre, productosString, descuento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paquete;
    }

    // Actualizar un paquete en la base de datos
    public void actualizarPaquete() {
        String sqlPaquete = "UPDATE Paquetes SET NombrePaquete = ?, Descuento = ? WHERE idPaquete = ?";
        String sqlEliminarProductos = "DELETE FROM ProductoPaquete WHERE idPaquete = ?";
        String sqlProductoPaquete = "INSERT INTO ProductoPaquete (idPaquete, idProducto) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmtPaquete = conn.prepareStatement(sqlPaquete);
             PreparedStatement pstmtEliminarProductos = conn.prepareStatement(sqlEliminarProductos);
             PreparedStatement pstmtProductoPaquete = conn.prepareStatement(sqlProductoPaquete)) {

            // Actualizar el paquete
            pstmtPaquete.setString(1, this.nombre);
            pstmtPaquete.setDouble(2, this.descuento);
            pstmtPaquete.setInt(3, this.idPaquete);
            pstmtPaquete.executeUpdate();

            // Eliminar los productos antiguos asociados al paquete
            pstmtEliminarProductos.setInt(1, this.idPaquete);
            pstmtEliminarProductos.executeUpdate();

            // Insertar los nuevos productos asociados al paquete
//            for (Producto producto : productos) {
//                pstmtProductoPaquete.setInt(1, this.idPaquete);
//                pstmtProductoPaquete.setInt(2, producto.getIdProducto());
//                pstmtProductoPaquete.executeUpdate();
//            }

            System.out.println("Paquete actualizado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
            // Leer un paquete de la base de datos
    public static List<Paquete> leerTodosPaquete() {
        String sqlPaquete = "SELECT * FROM Paquetes";
        List<Paquete> paquetes = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmtPaquete = conn.prepareStatement(sqlPaquete);) {

            // Obtener los detalles del paquete
            ResultSet rsPaquete = pstmtPaquete.executeQuery();

            if (rsPaquete.next()) {
                int id = rsPaquete.getInt("idPaquete");
                String nombre = rsPaquete.getString("NombrePaquete");
                double descuento = rsPaquete.getDouble("Descuento");
                String productos = rsPaquete.getString("Productos");
                
//                paquetes.add(new Paquete(id, nombre, productos, descuento))
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

//        return paquete;
return null;
    }

    // Eliminar un paquete de la base de datos
    public void eliminarPaquete() {
        String sqlEliminarProductos = "DELETE FROM ProductoPaquete WHERE idPaquete = ?";
        String sqlPaquete = "DELETE FROM Paquetes WHERE idPaquete = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmtEliminarProductos = conn.prepareStatement(sqlEliminarProductos);
             PreparedStatement pstmtPaquete = conn.prepareStatement(sqlPaquete)) {

            // Eliminar los productos asociados al paquete
            pstmtEliminarProductos.setInt(1, this.idPaquete);
            pstmtEliminarProductos.executeUpdate();

            // Eliminar el paquete
            pstmtPaquete.setInt(1, this.idPaquete);
            pstmtPaquete.executeUpdate();

            System.out.println("Paquete eliminado exitosamente.");
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
