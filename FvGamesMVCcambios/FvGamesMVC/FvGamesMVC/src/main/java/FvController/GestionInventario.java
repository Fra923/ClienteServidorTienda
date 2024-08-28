
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
    
    public List<Producto> consultarTodosProductos(){        
        return Producto.leerTodosProductos();
    }
    
}
