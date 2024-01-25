import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * AD Azterketa - Ejercicio 3
 * @author ANE
 * @version 2024/01/25
 */
public class OfficialLanguagesReport {
	
	private static final String CONNECTION_URL = "jdbc:mysql://localhost/world";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	public static void main(String[] args) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
			Scanner sc = new Scanner(System.in);
			//Solicitamos el nombre del continente
			System.out.println("Introduce el nombre del continente para ver sus idiomas oficiales: ");
			String continente = sc.nextLine();
			//Obtenemos el nombre de la BD
			printPaises(connection, continente);
			
		} catch (SQLException e) {
			 System.err.println("Error al conectarse a la base de datos: " + e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			}catch (SQLException e) {
				System.err.println("Error al desconectarse a la base de datos: " + e.getMessage());
			}
		}

	}
	
	/**
	 * Método que pinta por pantalla la información de todos los eventos mostrarndo el nombre
	 * del evento, el número de asistentes, la ubicación y la dirección
	 * @param connection conexión a la BD
	 * @throws SQLException errores
	 */
	private static void printPaises(Connection connection, String continente) throws SQLException {
		 String query = "SELECT c.Name, cl.Language, cl.Percentage "
		 		+ "FROM country c, countrylanguage cl "
		 		+ "WHERE c.Code = cl.CountryCode and c.Continent = ? and cl.IsOfficial = ? "
		 		+ "ORDER BY c.Name, cl.Language";
		 Statement stmt = null;
		 PreparedStatement pstmt = null;
		 ResultSet rsIdiomas = null;
		 try {
			 pstmt = connection.prepareStatement(query);
			 pstmt.setString(1, continente);
			 pstmt.setString(2, "T");
			 rsIdiomas = pstmt.executeQuery();
			 
			 System.out.println("");
			 System.out.printf("%-20s","País");          
             System.out.printf("%-25s","Idioma Oficial");         
             System.out.printf("%-35s%n","% Hablantes");
             System.out.println("-------------------------------------------------------------");
			 while (rsIdiomas.next()) {
				 System.out.printf("%-20s",rsIdiomas.getString(1));	            
	             System.out.printf("%-25s",rsIdiomas.getString(2));	             
	             System.out.printf("%-35s%n",rsIdiomas.getDouble(3));	            
	         }
		 } catch(SQLException e) {
			 System.err.println("Error al obtener datos de los idiomas: " + e.getMessage());
	     } finally {
			if (rsIdiomas != null) {
				rsIdiomas.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
	 }

}
