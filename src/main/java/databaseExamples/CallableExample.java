package databaseExamples;

import java.math.BigDecimal;
/*
The Learn Programming Academy
Java SE 11 Developer 1Z0-819 OCP Course - Part 2
Section 15: Database Applications with JDBC
Topic:  CallableStatement
*/
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class CallableExample {
    public static void main(String[] args) throws SQLException {

        // Wrap connection in a try-with-resources, so connection
        // automatically closed when completes or gets exception
        try (Connection connection =
                     ConnectionExamples.getConnection(
                             ConnectionExamples.derbyFileConnectionString);
        ) {
            // This method will create derby table PERSONS and populate
            // with data.
            setUpData(connection);

            // Retrieve and Print List of Person
            CRUDExamples.getPersonList(connection)
                    .forEach(System.out::println);

            // Execute java method which simply executes an update query
//            javaUpdatePassThrough("BOB", 101);
            
            // Execute DDL to create the stored procedure in DERBY
//            createProcForDerby(connection);
            
            // Execute DDL to create average stored procedure in DERBY
            createFunctionForDerby(connection);

            // Execute the stored procedure to get average age.
            int averageAge = executeAvgStoredProcedure(connection);
            System.out.println("The average age of population = " +
                    averageAge);

            // Execute DDL to create the stored procedure in DERBY
            createProcForDerby(connection);

            // Execute the stored procedure with different values
            executeStoredProcedure(connection, "CHANGED NAME TO BOB", 101);
            executeStoredProcedure(connection, "CHANGED NAME TO CAROL", 102);
            

            // Retrieve and Print List of Person
            CRUDExamples.getPersonList(connection)
                    .forEach(System.out::println);

        }
    }

    // Pass thru to other methods, to create a Table, PERSONS in the
    // Derby database, and add Data to it.
    public static void setUpData(Connection connection)
            throws SQLException {

        try {
            ConnectionExamples.dropTable(connection);
        } catch (SQLException se) {
            System.out.println(se);
        }

        // Example of DDL -
        ConnectionExamples.createTable(connection);

        // Example of DML - create data
        CRUDExamples.addData(connection);
    }

    // This is the java method which the Derby stored procedure will execute.
    public static void javaUpdatePassThrough(String name, int personId)
            throws SQLException {

        // Java code to do the actual update
        String query = "update person set name = ? where person_id = ?";

        try (Connection connection =
                     ConnectionExamples.getConnection(
                             ConnectionExamples.derbyFileConnectionString);
             PreparedStatement pstmt = connection.prepareStatement(query)
        ) {
            pstmt.setString(1, name);
            pstmt.setInt(2, personId);
            int result = pstmt.executeUpdate();
            if (result == 1) System.out.println("Row was updated");

        }
    }
    
 // Method creates a stored procedure in Derby based on java code
    public static void createProcForDerby(Connection connection)
            throws SQLException {

        // Derby let's you create a stored procedure based on java code,
        // in this case, the method CallableExample.testProc is defined in 
        // current class.
        String createProcedure =
                "create procedure" +
                        " UPDATE_PERSON(" +
                        "IN name VARCHAR(255)," +
                        "IN pid INTEGER) " +
                        "PARAMETER STYLE JAVA READS SQL" +
                        " DATA LANGUAGE JAVA EXTERNAL NAME " +
                        "'CallableExample.javaUpdatePassThrough'";

        try (Statement stmt = connection.createStatement()) {
            try {
                // First drop Procedure
                stmt.execute("drop procedure UPDATE_PERSON");
            } catch (Exception e) {
                // Ignore error, might occur if procedure does not exist
            }
            // Create the stored procedure in Derby
            stmt.executeUpdate(createProcedure);

        }
    }
    
    // Executes the Derby stored procedure, keeps connection open
    public static void executeStoredProcedure(
            Connection connection, String name, int personId)
            throws SQLException {

        try (
                // CallableStatement similar to PreparedStatement except
                // calls a named stored procedure and passes it values
                CallableStatement cs =
                        connection.prepareCall("{ call UPDATE_PERSON(?, ?)}")) {
            cs.setString(1, name);
            cs.setInt(2, personId);
            cs.execute();
        }
    }
    
    // This is the java method which the Derby stored procedure will execute.
    public static void javaGetAverageMethod(BigDecimal[] n)
            throws SQLException {

        int average = -1;
        // Java code to do the actual update
        String query = "select AVG(age) from PERSON";

        try (Connection connection =
                     ConnectionExamples.getConnection(
                             ConnectionExamples.derbyFileConnectionString);
             PreparedStatement pstmt = connection.prepareStatement(query)
        ) {
            ResultSet rs  = pstmt.executeQuery();

            if (rs.next()) {
                n[0] = rs.getBigDecimal(1);
            }
        }
    }
    
    // Method creates a stored procedure in Derby based on java code
    public static void createFunctionForDerby(Connection connection)
            throws SQLException {

        // Derby let's you create a stored procedure based on java code,
        // this case, the method CallableExample.testProc defined in this
        // class - real world stored procedure would exist in your database
        String createProcedure =
                "create procedure" +
                        " GET_AVERAGE(" +
                        " OUT DECIMAL) " +
                        "PARAMETER STYLE JAVA READS SQL" +
                        " DATA LANGUAGE JAVA EXTERNAL NAME " +
                        "'CallableExample.javaGetAverageMethod'";

        try (Statement stmt = connection.createStatement()) {
            try {
                // First drop Procedure
                stmt.execute("drop procedure GET_AVERAGE");
            } catch (Exception e) {
                // Ignore error, might occur if procedure does not exist
            }
            // Create the stored procedure in Derby
            stmt.executeUpdate(createProcedure);

        }
    }
    
    // Executes the Derby stored procedure, keeps connection open
    public static int executeAvgStoredProcedure(Connection connection)
            throws SQLException {

        int average = -1;
        try (
                // CallableStatement similar to PreparedStatement except
                // calls a named stored procedure and passes it values
                CallableStatement cs =
                        connection.prepareCall("{ call GET_AVERAGE(?)}")) {
            cs.registerOutParameter(1, Types.DECIMAL);
            cs.execute();
            BigDecimal bigDecimal = cs.getBigDecimal(1);

            average = bigDecimal.intValue();
        }
        return average;
    }
}
