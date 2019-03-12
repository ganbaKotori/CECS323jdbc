
import java.sql.*;
import java.util.Scanner;


public class JDBCSampleSource {
    //  Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    static String INPUT;
    static Connection conn = null; //initialize the connection
    static Statement stmt = null;
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are
    //strings, but that won't always be the case.
    static final String displayFormat="%-5s%-15s%-15s%-15s\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
//            + "testdb;user=";
/**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    public static void displayOptions(){
        System.out.println("What would you like to do?");
        System.out.println("1 : List all writing groups");
        System.out.println("2 : List data for a specific group");
        System.out.println("3 : List all publishers");
        System.out.println("4 : List data for a specific publisher");
        System.out.println("5 : List all book titles");
        System.out.println("6 : List data for a particular book");
        System.out.println("7 : Insert a new book");
        System.out.println("8 : Remove a book");
    }
    
    public static void listAllBooks(){
        //STEP 4: Execute a query
        try{
            System.out.println("Listing books...");
            stmt = conn.createStatement();
            conn = DriverManager.getConnection(DB_URL); //REQUIRED FOR EXECUTION OF STATEMENTS
            String sql;
            sql = "SELECT pub_id, pub_name, year_est, city, state, country FROM publishers";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            System.out.printf(displayFormat, "Pub ID", "Pub Name", "Year", "City","State","Country");
            while (rs.next()) {
                //Retrieve by column name
                String id = rs.getString("pub_id");
                String name = rs.getString("pub_name");
                String year = rs.getString("year_est");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String country = rs.getString("country");

                //Display values
                System.out.printf(displayFormat+"%n",
                        id, name, year, city,state,
                        country); // Doesn't display all columns for some reason
       
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
        }catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                    return;
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                    return;
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }//END LISTPUBLISHERS
    
    public static void listPublishers(){
        //STEP 4: Execute a query
        try{
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            conn = DriverManager.getConnection(DB_URL); //REQUIRED FOR EXECUTION OF STATEMENTS
            String sql;
            sql = "SELECT pub_id, pub_name, year_est, city, state, country FROM publishers";
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            System.out.printf(displayFormat, "Pub ID", "Pub Name", "Year", "City","State","Country");
            while (rs.next()) {
                //Retrieve by column name
                String id = rs.getString("pub_id");
                String name = rs.getString("pub_name");
                String year = rs.getString("year_est");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String country = rs.getString("country");

                //Display values
                System.out.printf(displayFormat+"%n",
                        id, name, year, city,state,
                        country); // Doesn't display all columns for some reason
       
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
        }catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                    return;
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                    return;
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }//END LISTPUBLISHERS
    
     public static void insertPublisher(){
        try{
            Scanner in = new Scanner(System.in);
            conn = DriverManager.getConnection(DB_URL); //REQUIRED FOR EXECUTION OF STATEMENTS

            System.out.println("Enter publisher name");
            String name = in.nextLine();
            System.out.println("Enter publisher address");
            String address = in.nextLine();
            System.out.println("Enter publisher phone number");
            String phone = in.nextLine();
            System.out.println("Enter publisher email");
            String email = in.nextLine();
            
            PreparedStatement stmt=conn.prepareStatement("insert into publishers values(?,?,?,?)");  
            stmt.setString(1,name);//1 specifies the first parameter in the query  
            stmt.setString(2,address);
            stmt.setString(3,phone);
            stmt.setString(4,email);
            
            System.out.println("inserting publisher...");
            
            int i=stmt.executeUpdate();  
            System.out.println(i+" records inserted");  
            
            //pstmt.close();  
            stmt.close();
        }catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                    return;
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                    return;
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    } //END INSERT PUBLISHER

    public static void main(String[] args) {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        INPUT = "";
        
        
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        System.out.print("Database user name: ");
        USER = in.nextLine();
        System.out.print("Database password: ");
        PASS = in.nextLine();
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
          //initialize the statement that we're using
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            
            
            while(!INPUT.equals("q")){
                
               
                
            displayOptions();
            INPUT = in.nextLine();
            
            switch(INPUT){
                case "3":
                    listPublishers();
                    break;
                case "6":
                    insertPublisher();
            } 
           
//            if (INPUT.equals("3")){
//                System.out.println("Listing all publishers!");
//                listPublishers();
//            }
                
            
        }

            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}
