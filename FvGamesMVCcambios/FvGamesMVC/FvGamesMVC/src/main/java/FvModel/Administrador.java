package FvModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Administrador {
    private final String nombre;
    private final String cedula;
    private final String contrasena;

    public Administrador(String nombre, String cedula, String contrasena) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getContrasena() {
        return contrasena;
    }

    // Método para autenticar al administrador en la base de datos
    public boolean autenticarAdministrador() {
        String sql = "SELECT * FROM Administradores WHERE Cedula = ? AND Contrasena = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FvGames", "root", "SVfr2890210!");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cedula);
            pstmt.setString(2, contrasena);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();  // Devuelve true si se encontró una coincidencia

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar la contraseña del administrador en la base de datos
    public void actualizarContrasena(String nuevaContrasena) {
        String sql = "UPDATE Administradores SET Contrasena = ? WHERE Cedula = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "SVfr2890210!");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nuevaContrasena);
            pstmt.setString(2, cedula);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
