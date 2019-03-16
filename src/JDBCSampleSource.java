
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
        if (!USER.isEmpty()){ 
            System.out.print("Database password: ");
            PASS = in.nextLine(); //PASSWORD ASKED ONLY WHERE THERES A USER
            DB_URL = DB_URL  + DBNAME + ";user="+ USER + ";password=" + PASS;
            System.out.println(DB_URL);
        }
        else DB_URL = DB_URL + DBNAME; //NO USER OR PASS USED WHEN NO USER AND PASS IS GIVEN
        
        
        //Constructing the database URL connection string
        
          //initialize the statement that we're using
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            
            
            while(!INPUT.equals("0")){
            displayOptions();
            INPUT = in.nextLine();
            
            switch(INPUT){
                case "1":
                    listAllGroups();
                    break;
                case "2":
                    listGroup();
                    break;
                case "3":
                    listAllPublishers();
                    break;
                case "4":
                    listPublisher();
                    break;
                case "5":
                    listAllBooks();
                    break;
                case "6":
                    listBook();
                    break;
                case "7":
                    insertBook();
                    break;
                case "8":
                    insertPublisher();
                    break;
                case "9":
                    removeBook();
                    break;
                case "0":
                    break;
                 default: 
                     System.out.println("Please input a valid number");
            } 
        }
            if (conn != null) {
                conn.close(); //IF YOU ONLY CHOOSE "0" TO QUIT
              }
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            System.out.println("Try again!");
            main(null);
            
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
        System.out.println("\nGoodbye!");
    }
    //END MAIN
    
    
    
        public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
  
    
    public static void listAllPublishers(){
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
            System.out.println(se.getMessage());
            //se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            System.out.println(e.getMessage());
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
    }
    
    public static void displayOptions(){
        System.out.println("\nWhat would you like to do?");
        System.out.println("1 : List all writing groups");
        System.out.println("2 : List data for a specific group");
        System.out.println("3 : List all publishers");
        System.out.println("4 : List data for a specific publisher");
        System.out.println("5 : List all book titles");
        System.out.println("6 : List data for a particular book");
        System.out.println("7 : Insert a new book");
        System.out.println("8 : Insert a new publisher");
        System.out.println("9 : Remove a book");
        System.out.println("0 : Quit\n");
    }
        
        public static void listAllGroups()
        {
            
        }
        
        public static void listGroup()
        {
             try{
            Scanner in = new Scanner(System.in);
            conn = DriverManager.getConnection(DB_URL); //REQUIRED FOR EXECUTION OF STATEMENTS
            //PreparedStatement pstmt = null;
            
            System.out.println("Enter group name");
            String groupName = in.nextLine();
            System.out.println("Enter head writer");
            String headWriter = in.nextLine();
            System.out.println("Enter year the group was formed");
            String year = in.nextLine();
             while(!year.matches("[0-9]+")){
                System.out.println("Please enter a valid year");
                year = in.nextLine();
            }
            System.out.println("Enter subject");
            String subject = in.nextLine();
            
            PreparedStatement stmt=conn.prepareStatement("insert into writing_groups values(?,?,?,?)");  
            stmt.setString(1,groupName);//1 specifies the first parameter in the query  
            stmt.setString(2,headWriter);
            stmt.setString(3,year);
            stmt.setString(4,subject);
            
            System.out.println("inserting writing group...");
            
            int i=stmt.executeUpdate();  
            System.out.println(i+" records inserted");  
            
            //pstmt.close();  
            stmt.close();
        }catch (SQLException se) {
            //Handle errors for JDBC
             System.out.println(se.getMessage());
        } catch (Exception e) {
            //Handle errors for Class.forName
             System.out.println(e.getMessage());
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
    
        }
        
        public static void listPublisher()
        {
            
        }
        
        public static void listAllBooks()
        {
            
        }
        public static void listBook()
        {
            
        }
        public static void insertBook()
        {
            
        }
        public static void insertPublisher()
        {
            
        }
        public static void removeBook()
        {
            
        }
    
}
    
    
