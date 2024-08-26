package FvView;

import javax.swing.*;
import java.awt.*;

public class ClienteVista {
    private JFrame frame;
    private JTextArea productoArea;
    private JButton verCarritoButton;
    private JButton finalizarCompraButton;

    public ClienteVista() {
        frame = new JFrame("Vista Cliente");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        productoArea = new JTextArea();
        frame.add(new JScrollPane(productoArea), BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        verCarritoButton = new JButton("Ver Carrito");
        finalizarCompraButton = new JButton("Finalizar Compra");
        panel.add(verCarritoButton);
        panel.add(finalizarCompraButton);
        frame.add(panel, BorderLayout.SOUTH);
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    public JTextArea getProductoArea() {
        return productoArea;
    }

    public JButton getVerCarritoButton() {
        return verCarritoButton;
    }

    public JButton getFinalizarCompraButton() {
        return finalizarCompraButton;
    }
}
