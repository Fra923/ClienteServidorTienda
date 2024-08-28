
package FvView;

import FvController.GestionClientes;
import FvController.GestionInventario;
import FvModel.Producto;
import static java.nio.file.Files.delete;
import javax.swing.table.DefaultTableModel;

public class AdminViewGestionInventario extends javax.swing.JFrame {

    /**
     * Creates new form LoginView
     */
    
    GestionClientes gestionClientes;
    GestionInventario gestionInventario;
    
    public AdminViewGestionInventario(GestionClientes clientes, GestionInventario inventario ) {
        this.gestionClientes = clientes;
        this.gestionInventario = inventario;
        
        DefaultTableModel modeloTabla = new DefaultTableModel ( new Object []{ "Id","Nombre" , "Categoria", "Precio", "Cantidad"}, 0);
        for (Producto producto : gestionInventario.consultarTodosProductos()){
            modeloTabla.addRow(new Object[]{producto.getIdProducto(), producto.getNombre(), producto.getCategoria(), producto.getPrecio(), producto.getCantidad()});
        }
        initComponents();
        jTable1.setModel(modeloTabla);            
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setText("Gestion de Inventario");

        jButton4.setText("Regresar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Guardar cambios");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(jButton4)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(51, 51, 51)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jButton1)
                .addGap(64, 64, 64)
                .addComponent(jButton4)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        new AdminMenuView(gestionClientes, gestionInventario).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int rowcount = jTable1.getModel().getRowCount();
        int colcount = jTable1.getModel().getColumnCount();
        for(int i = 0; i<rowcount; i++){
            
            String id       = "";
            String nombre   = "";
            String cat      = "";
            String precio   = "";
            String cant     = "";
            
            for (int j = 0; j<colcount; j++){
                switch (j){
                    case 0:
                        id = String.valueOf(jTable1.getModel().getValueAt(i, j));
                    break;
                    case 1:
                        nombre = String.valueOf(jTable1.getModel().getValueAt(i, j));
                    break;
                    case 2:
                        cat = String.valueOf(jTable1.getModel().getValueAt(i, j));
                    break;
                    case 3:
                        precio = String.valueOf(jTable1.getModel().getValueAt(i, j));
                    break;
                    case 4:
                        cant = String.valueOf(jTable1.getModel().getValueAt(i, j));
                    break;
                }
                System.out.println(id);
                System.out.println(nombre);
                System.out.println(cat);
                System.out.println(precio);
                System.out.println(cant);                
            }
            gestionInventario.actualizarProducto(Integer.valueOf(id), nombre, cat, Double.valueOf(precio), Integer.valueOf(cant));
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
