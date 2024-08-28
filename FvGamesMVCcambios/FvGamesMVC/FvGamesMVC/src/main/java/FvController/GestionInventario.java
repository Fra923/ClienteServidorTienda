
package FvController;

import FvModel.CarritoDeCompras;
import FvModel.Inventario;
import FvModel.Paquete;
import FvModel.Producto;
import java.util.ArrayList;
import java.util.List;


public class GestionInventario {
    
    private Inventario inventario;
    private List<Paquete> paquetes;
    private CarritoDeCompras carrito;
    
    public GestionInventario() {
        this.inventario = new Inventario();
        this.paquetes = new ArrayList<>();
        
    }
    
    public void crearPaquete(String nombre, List<Producto> productos, double descuento) {
        Paquete paquete = new Paquete(nombre, productos, descuento);
        paquetes.add(paquete);
    }

    public void mostrarPaquetes() {
        for (Paquete p : paquetes) {
            System.out.println(p);
        }
    }
    
    public void actualizarProducto(int id, String nombre, String categoria, double precio, int cantidad){
        Producto producto = new Producto(id, nombre, categoria, precio, cantidad);
        producto.actualizarProducto();
    }
    
    public List<Producto> consultarTodosProductos(){        
        return Producto.leerTodosProductos();
    }
    
}
