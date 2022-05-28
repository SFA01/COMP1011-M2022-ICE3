package ca.georgiancollege.comp1011m2022ice3;

import java.sql.*;

/* Singleton */
public class DBManager
{
    /********************* SINGLETON SECTION *****************************/
    // Step 1. private static instance member variable
    private static DBManager m_instance = null;
    // Step 2. make the default constructor private
    private DBManager() {}
    // Step 3. create a public static entry point / instance method
    public static DBManager Instance()
    {
        // Step 4. Check if the private instance member variable is null
        if(m_instance == null)
        {
            // Step 5. Instantiate a new DBManager instance
            m_instance = new DBManager();
        }
        return m_instance;
    }
    /*********************************************************************/

    // private instance member variables
    private String m_user = "root";
    private String m_password = "";
    private String m_connectURL = "jdbc:mysql://localhost:3306/comp1011";

    /**
     * This method inserts a Vector2D object to the Database
     *
     * @param vector2D
     * @return
     * @throws SQLException
     */
    public int insertVector2D(Vector2D vector2D) throws SQLException {
        int vectorID = -1; // if this method returns -1 - it means that something went wrong

        // initializing the result set object
        ResultSet resultSet = null;

        // create a query string
        String sql = "INSERT INTO vectors(X, Y) VALUES(?, ?);";

        try
                (   /* head of the try / catch block */
                        Connection connection = DriverManager.getConnection(m_connectURL, m_user, m_password);
                        PreparedStatement statement = connection.prepareStatement(sql, new String[] {"vectorID"});
                )
        {
            // configure prepared statement
            statement.setFloat(1, vector2D.getX());
            statement.setFloat(2, vector2D.getY());

            // run the command on the Database
            statement.executeUpdate();

            // get the VectorID
            resultSet = statement.getGeneratedKeys();
            while(resultSet.next())
            {
                // get the VectorID from the Database
                vectorID = resultSet.getInt(1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(resultSet != null)
            {
                resultSet.close();
            }
        }

        return vectorID;
    }
}