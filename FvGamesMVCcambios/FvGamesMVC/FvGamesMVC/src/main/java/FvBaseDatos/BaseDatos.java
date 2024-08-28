package FvBaseDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author eder
 */
public class BaseDatos {
    
    //Datos de gestión de BD
    Connection conexion = null;
    PreparedStatement consulta = null;
    ResultSet resultado = null;
    
    //Parámetros de inicialización de BD
    String url = "jdbc:mysql://localhost:3306/FvGames";
    String username = "root";
    String password = "SVfr2890210!";
    
    //Construir los métodos para la conexión a la BD

    public void setConsulta(String sql) {
        try
        {
            this.consulta = conexion.prepareStatement(sql);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public PreparedStatement getConsulta() {
        return consulta;
    }

    public ResultSet getResultado() {
        try
        {
            return consulta.executeQuery();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void setConexion() {
        try
        {
            this.conexion = DriverManager.getConnection(url, username, password);
            System.out.println("Conexion exitosa");
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void cerrarConexion()
    {
        if(resultado != null)
        {
            try
            {
                resultado.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        if(consulta != null)
        {
            try
            {
                consulta.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        if(conexion != null)
        {
            try
            {
                conexion.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
    }
}
