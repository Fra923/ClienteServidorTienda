package FvController;

import FvModel.Administrador;
import FvModel.Cliente;
import FvModel.ClienteRegistrado;
import FvModel.Inventario;
import FvBaseDatos.BaseDatos;
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
    
    public ClienteRegistrado consultarCliente(String cedula){
        ClienteRegistrado cliente = new ClienteRegistrado();
        cliente.consultarClienteEnDB(cedula);
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

    public void visualizarProductos(Cliente cliente, Inventario inventario) {
        cliente.visualizarProductos(inventario);
    }
}