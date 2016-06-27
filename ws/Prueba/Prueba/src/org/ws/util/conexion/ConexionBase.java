
package org.ws.util.conexion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.sql.DataSource;
import javax.naming.InitialContext;

import oracle.jdbc.OracleDriver;

public class ConexionBase {

    public Connection conexion;
    private DataSource ds = null;
    private String jdbcLookUp = "";
    private String conexion_usuario = "";
    private String conexion_password = "";
    private String conexion_URL = "";
    private String conexion_Driver = "";

    public ConexionBase() {
    }
    //__________________________________________________________________________
    //**************************************************************************

    public boolean Conectar() throws Exception{
        int tipoConexion = 1; //1 --> JDBC
                              //2 --> DataSource4
      
            if (tipoConexion == 2) {//cambiar 2
                this.initContext();
                if (conexion == null) {
                    System.out.println("Conexión a DB: OK");
                    conexion = ds.getConnection();
                    conexion.clearWarnings();
                    return true;
                }
            } else {
                //Class<OracleDriver> o = (Class<OracleDriver>) Class.forName("oracle.jdbc.OracleDriver");
            	Class.forName ("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            	String db_url = "jdbc:sqlserver://localhost;database=redSocial";
            	conexion = DriverManager.getConnection (db_url, "sa", "Promeinfo2008");
                //conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.37.34:1521:axisd", "IAS_DMZ_AUTOSERV", "IAS_DMZ_AUTOSERV");
                //conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.37.34:1521:axisd", "portakav", "portakav");
                //conexion = DriverManager.getConnection("jdbc:oracle:thin:@192.168.37.147:1521:AXISCLI", "portakav", "portakav");

                conexion.clearWarnings();
                return true;
            }
        return false;
    }

    //__________________________________________________________________________
    //**************************************************************************
    private void initContext() throws Exception {
        if (ds == null) {
            Context envContext = new InitialContext();
            ds = (DataSource) envContext.lookup("jdbc/axis");
            envContext.close();
        }
        System.out.println("Finalizado init Contexto");
    }

    //__________________________________________________________________________
    //**************************************************************************
    public void conectarPLSQL_SAS() throws SQLException {
        
            //Línea de código para realizar la conecçxión a la Base de datos.
            conexion = DriverManager.getConnection(conexion_URL, conexion_usuario, conexion_password);
    }

    //__________________________________________________________________________
    //**************************************************************************
    public ConexionBase(String usuario, String password, String url) throws SQLException {
        setUsuario_Conexion(usuario);
        setPassword_Conexion(password);
        setURL_Conexion(url);
    }

    //__________________________________________________________________________
    //**************************************************************************
    public ResultSet ejecutarServicio_SAS(String procedure) throws SQLException {
        CallableStatement c_servicio = conexion.prepareCall(procedure);
        return c_servicio.executeQuery();
    }

    public void ejecutarInsertUpdate(String procedure) throws SQLException {
        PreparedStatement c_servicio = conexion.prepareCall(procedure);
        c_servicio.executeUpdate();
    }
    
    //__________________________________________________________________________
    //**************************************************************************
    public boolean actualizarDatos_SAS() throws SQLException {
        return true;
    }

    //__________________________________________________________________________
    //**************************************************************************
    public boolean cerrarConexion() throws SQLException {
        if (conexion != null) {
            conexion.close();
            conexion = null;
            return true;
        }
        return false;
    }

    //__________________________________________________________________________
    //**************************************************************************
    private void setUsuario_Conexion(String user_conex) {
        this.conexion_usuario = user_conex;
    }

    private void setPassword_Conexion(String pass_conex) {
        this.conexion_password = pass_conex;
    }

    private void setURL_Conexion(String url_conex) {
        this.conexion_URL = url_conex;
    }

    private void setDriver_Conexion(String driver_conex) {
        this.conexion_Driver = driver_conex;
    }

    //__________________________________________________________________________
    //**************************************************************************
    private String getUsuario_Conexion() {
        return this.conexion_usuario;
    }

    private String getPassword_Conexion() {
        return this.conexion_password;
    }

    private String getURL_Conexion() {
        return this.conexion_URL;
    }

    private String getDriver_Conexion() {
        return this.conexion_Driver;
    }
}
