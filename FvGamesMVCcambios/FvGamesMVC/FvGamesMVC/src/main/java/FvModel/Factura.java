package FvModel;

public class Factura {
    
    private CarritoDeCompras carrito;

    public Factura(CarritoDeCompras carrito) {
        this.carrito = carrito;
    }

    public void mostrarFactura() {
        System.out.println("----- Factura -----");
        carrito.mostrarCarrito();
        System.out.println("Subtotal: " + carrito.calcularTotal());
        System.out.println("Impuesto de venta (13%): " + (carrito.calcularTotal() * 0.13));
        System.out.println("Total: " + carrito.calcularTotalConImpuesto());
        System.out.println("-------------------");
    }
}
