package FvController;

import FvModel.Administrador;
import FvModel.Cliente;
import FvModel.ClienteRegistrado;
import FvModel.Inventario;
import FvBaseDatos.BaseDatos;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class GestionClientes {
    private Map<String, ClienteRegistrado> clientesRegistrados;
    private Map<String, Administrador> administradores;
    private BaseDatos baseDatos;

    public GestionClientes() {
        clientesRegistrados = new HashMap<>();
        administradores = new HashMap<>();
        baseDatos = new BaseDatos();
    }

    public void registrarCliente(String nombre, String apellidos, String cedula, String direccion, String email, String dineroEnCuenta, String metodoPagoPreferido, String contrasena) {
        ClienteRegistrado cliente = new ClienteRegistrado(nombre, apellidos, cedula, direccion, email, dineroEnCuenta, metodoPagoPreferido, contrasena);
        clientesRegistrados.put(cedula, cliente);
        System.out.println("Cliente registrado con éxito.");
    }
    
    public void actualizarCliente(String nombre, String apellidos, String cedula, String direccion, String email, String dineroEnCuenta, String metodoPagoPreferido, String contrasena) {
        ClienteRegistrado cliente = new ClienteRegistrado();
        cliente.actualizarClienteEnDB(nombre, apellidos, cedula, direccion, email, dineroEnCuenta, metodoPagoPreferido, contrasena);
        JOptionPane.showMessageDialog(null, "Cliente actualizado con éxito.", "Info", 1);
        System.out.println("Cliente registrado con éxito.");
    }
    
    public ClienteRegistrado consultarCliente(String cedula){
        ClienteRegistrado cliente = new ClienteRegistrado();
        cliente.consultarClienteEnDBCedula(cedula);
        return cliente;
    }

    public void registrarAdministrador(String nombre, String cedula, String contrasena) {
        Administrador administrador = new Administrador(nombre, cedula, contrasena);
        administradores.put(cedula, administrador);
        System.out.println("Administrador registrado con éxito.");
    }

    public ClienteRegistrado iniciarSesionCliente(String cedula) {
        return clientesRegistrados.get(cedula);
    }

    public Administrador iniciarSesionAdministrador(String cedula, String contrasena) {
        String contrasenaAdmin = "1";
        String cedulaAdmin     = "1";
//        Administrador administrador = administradores.get(cedula);
        if (contrasenaAdmin.equals(contrasena) && cedulaAdmin.equals(cedula)) {
            System.out.println("Inicio de sesión exitoso.");
            return new Administrador("Admin", cedula, contrasena);
        } else {
            JOptionPane.showMessageDialog(null, "Cédula o contraseña incorrecta.", "Error", 0);
            System.out.println("Cédula o contraseña incorrecta.");
            return null;
        }
    }
    
    public ClienteRegistrado iniciarSesionCliente(String cedula, String contrasena) {
        ClienteRegistrado cliente = new ClienteRegistrado();
        cliente.consultarClienteEnDB(cedula, contrasena);
        if (!cliente.getCedula().equals("") && !cliente.getContrasena().equals("")) {
            System.out.println("Inicio de sesión exitoso.");
            clientesRegistrados.put(cliente.getCedula(), cliente);
            return cliente;
        } else {
            JOptionPane.showMessageDialog(null, "Cédula o contraseña incorrecta.", "Error", 0);
            System.out.println("Cédula o contraseña incorrecta.");
            return null;
        }
    }
    
    public void agregarACarrito(String id, String nombre){
        ClienteRegistrado cliente = clientesRegistrados.values().iterator().next();
        cliente.agregarProductoAlCarrito(Integer.valueOf(id), nombre, 1);
    }

    public void visualizarProductos(Cliente cliente, Inventario inventario) {
        cliente.visualizarProductos(inventario);
    }
}