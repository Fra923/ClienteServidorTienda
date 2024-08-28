package FvModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClienteRegistrado extends Cliente {
    private String direccion;
    private String email;
    private String dineroEnCuenta;
    private String metodoPagoPreferido;
    private String contrasena;
    private CarritoDeCompras carrito;
    private int idCliente;

    public ClienteRegistrado(String nombre, String apellidos, String cedula, String direccion, String email, String dineroEnCuenta, String metodoPagoPreferido, String contrasena) {
        super(nombre, apellidos, cedula);
        this.direccion = direccion;
        this.email = email;
        this.dineroEnCuenta = dineroEnCuenta;
        this.metodoPagoPreferido = metodoPagoPreferido;
        this.contrasena = contrasena;
        this.idCliente = guardarClienteEnDB();
        this.carrito = new CarritoDeCompras(idCliente);
    }   

    public ClienteRegistrado() {
    }   

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDineroEnCuenta() {
        return dineroEnCuenta;
    }

    public void setDineroEnCuenta(String dineroEnCuenta) {
        this.dineroEnCuenta = dineroEnCuenta;
    }

    public String getMetodoPagoPreferido() {
        return metodoPagoPreferido;
    }

    public void setMetodoPagoPreferido(String metodoPagoPreferido) {
        this.metodoPagoPreferido = metodoPagoPreferido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }  

    @Override
    public void visualizarProductos(Inventario inventario) {
        inventario.mostrarInventario();
    }

    public void agregarProductoAlCarrito(int idProducto, String nombreProducto, int cantidad) {
        Producto producto = Producto.leerProducto(idProducto);
        if (producto != null) {
            if (producto.getCantidad() >= cantidad) {
                carrito.agregarProducto(producto, cantidad);
                System.out.println("Producto agregado al carrito.");
            } else {
                System.out.println("Cantidad no disponible. Disponible: " + producto.getCantidad());
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    public void visualizarCarrito() {
        carrito.mostrarCarrito();
    }

    public void finalizarCompra(Inventario inventario) {
        double total = carrito.calcularTotalConImpuesto();
        if (total <= Double.parseDouble(dineroEnCuenta)) {
            dineroEnCuenta = ""+(Double.parseDouble(dineroEnCuenta) - total);
            List<ElementoCarrito> elementos = carrito.getElementos();
            for (ElementoCarrito elemento : elementos) {
                Producto producto = elemento.getProducto();
                int cantidadComprada = elemento.getCantidad();
                producto.disminuirCantidad(cantidadComprada);
            }
            System.out.println("Compra realizada con éxito.");
            carrito.generarFactura();
            carrito = new CarritoDeCompras(idCliente); // Reiniciar el carrito después de la compra
        } else {
            System.out.println("Fondos insuficientes. Recargue su cuenta.");
        }
    }

    public void recargarCuenta(double monto) {
        dineroEnCuenta = "" + (Double.parseDouble(dineroEnCuenta) + monto);
        System.out.println("Cuenta recargada con éxito.");
    }

    private int guardarClienteEnDB() {
        String sql = "INSERT INTO Cliente (Nombre, Apellidos, Cedula, Direccion, Email, Dinero, MetodoPago, Foto, NumeroTarjeta, NumeroCuentaBanco, Contrasena, TipoCliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int id = -1;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, getNombre());
            pstmt.setString(2, getApellidos());
            pstmt.setString(3, getCedula());
            pstmt.setString(4, direccion);
            pstmt.setString(5, email);
            pstmt.setString(6, dineroEnCuenta);
            pstmt.setString(7, metodoPagoPreferido);
            pstmt.setString(8, ""); // Foto (dejar vacío o poner valor por defecto si no se tiene)
            pstmt.setString(9, ""); // NumeroTarjeta (dejar vacío o poner valor por defecto si no se tiene)
            pstmt.setString(10, ""); // NumeroCuentaBanco (dejar vacío o poner valor por defecto si no se tiene)
            pstmt.setString(11, contrasena); // Contrasena (dejar vacío o poner valor por defecto si no se tiene)
            pstmt.setString(12, "Cliente"); // TipoCliente por defecto como "Cliente"
            pstmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
    
    public void consultarClienteEnDBCedula(String cedula) {
        String Nombre       =   "";
        String Apellidos    =   "";
        String Cedula       =   "";
        String Direccion    =   "";
        String Email        =   "";
        String Dinero       =   "";
        String MetodoPago   =   "";
        String Contrasena   =   "";
        
        String sql = "SELECT * FROM cliente WHERE Cedula=?";        
        
       // System.out.println("Estoy en consultar cliente");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cedula);
            System.out.println("Ejecutar query con cedula "+cedula);

            // Obtener el ID generado
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Nombre      =   rs.getString("Nombre");
                    Cedula      =   rs.getString("Cedula");
                    Apellidos   =   rs.getString("Apellidos");
                    Direccion   =   rs.getString("Direccion");
                    Email       =   rs.getString("Email");
                    Dinero      =   rs.getString("Dinero");
                    MetodoPago  =   rs.getString("MetodoPago");
                    Contrasena  =   rs.getString("Contrasena");                    
                }
            }

        } catch (SQLException e) {
            System.out.println("CATCH");
            e.printStackTrace();
        }
        
        System.out.println("SETS");
        System.out.println("Metodo de pago:" + MetodoPago);
        this.setNombre(Nombre);
        this.setCedula(Cedula);
        this.setApellidos(Apellidos);
        this.setDireccion(Direccion);
        this.setEmail(Email);
        this.setDineroEnCuenta(Dinero);
        this.setMetodoPagoPreferido(MetodoPago);
        this.setContrasena(Contrasena);
    }
    
    public void consultarClienteEnDB(String cedula, String contrasena) {
        String id           =   "";
        String Nombre       =   "";
        String Apellidos    =   "";
        String Cedula       =   "";
        String Direccion    =   "";
        String Email        =   "";
        String Dinero       =   "";
        String MetodoPago   =   "";
        String Contrasena   =   "";
        
        String sql = "SELECT * FROM cliente WHERE Cedula=? AND Contrasena=?";        
        
        //System.out.println("Estoy en consultar cliente");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cedula);
            pstmt.setString(2, contrasena);
            System.out.println("Ejecutar query con cedula "+cedula);

            // Obtener el ID generado
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    id          =   rs.getString("idCliente");
                    Nombre      =   rs.getString("Nombre");
                    Cedula      =   rs.getString("Cedula");
                    Apellidos   =   rs.getString("Apellidos");
                    Direccion   =   rs.getString("Direccion");
                    Email       =   rs.getString("Email");
                    Dinero      =   rs.getString("Dinero");
                    MetodoPago  =   rs.getString("MetodoPago");
                    Contrasena  =   rs.getString("Contrasena");                    
                }
            }

        } catch (SQLException e) {
            System.out.println("CATCH");
            e.printStackTrace();
        }
        
        //System.out.println("SETS");
        System.out.println("Metodo de pago:" + MetodoPago);
        this.idCliente = Integer.parseInt(id);
        this.setNombre(Nombre);
        this.setCedula(Cedula);
        this.setApellidos(Apellidos);
        this.setDireccion(Direccion);
        this.setEmail(Email);
        this.setDineroEnCuenta(Dinero);
        this.setMetodoPagoPreferido(MetodoPago);
        this.setContrasena(Contrasena);
        this.carrito = new CarritoDeCompras(idCliente);
    }
    
    public int actualizarClienteEnDB(String nombre, String apellidos, String cedula, String direccion, String email, String dineroEnCuenta, String metodoPagoPreferido, String contrasena) {
        String sql = "UPDATE Cliente SET Nombre=?, Apellidos=?, Cedula=?, Direccion=?, Email=?, Dinero=?, MetodoPago=?, Contrasena=? WHERE Cedula=?";
        int id = -1;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, apellidos);
            pstmt.setString(3, cedula);
            pstmt.setString(4, direccion);
            pstmt.setString(5, email);
            pstmt.setString(6, dineroEnCuenta);
            pstmt.setString(7, metodoPagoPreferido);
            pstmt.setString(8, contrasena); // Contrasena (dejar vacío o poner valor por defecto si no se tiene)
            pstmt.setString(9, cedula);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}
