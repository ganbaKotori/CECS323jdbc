import java.sql.*;
import java.util.*;

/**
 *
 * @author kermi
 */
public class JDBCApp {
    
     // Database credentials
    static String USER;
    static String PASSWORD;
    static String DBNAME;
    
    static String displayFormat;
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
    // + "testdb;user=";
    static Connection conn;
    static String INPUT = "";
    static Scanner in;
    static PreparedStatement pstmt;
    static Statement stmt = null;

    
    public static String dispNull(String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0) {
            return "N/A";
        } else {
            return input;
        }
    }
    
     public static void promptEnterKey() {
        System.out.println("Press \"ENTER\" to continue...");
        Scanner in = new Scanner(System.in);
        in.nextLine();
    }
    
    //1
    public static void listAllGroups()
    {
        try{
            pstmt = conn.prepareStatement("SELECT * FROM WritingGroup");
            
            ResultSet rs = pstmt.executeQuery();
            
            System.out.println("\nGroup Name");
            System.out.println("----------");
            
            while(rs.next()){
                //Retrieve by column name
                String name = rs.getString("groupName");

                //Display values
                System.out.println(name);
            }               
        } catch(SQLException e)
        {
            e.printStackTrace();
        }       
    }
    
    //2
        public static void listSpecificGroup() {
        try {
            System.out.print("\nInput a group name: ");
            String groupName = in.nextLine();
            pstmt = conn.prepareStatement
            ("SELECT GROUPNAME, BOOKTITLE, PUBLISHERNAME, YEARPUBLISHED, NUMBERPAGES, "
           + "PUBLISHERADDRESS, PUBLISHERPHONE, PUBLISHEREMAIL, HEADWRITER, YEARFORMED, SUBJECT "
           + "FROM WritingGroup INNER JOIN BOOKS using (GROUPNAME) INNER JOIN PUBLISHERS using (PUBLISHERNAME) WHERE groupName = ?");
            pstmt.setString(1, groupName);
            ResultSet rs = pstmt.executeQuery();

            
            displayFormat = "%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s%-30s\n";
            System.out.printf(displayFormat, "Group Name","Book Title", "Publisher Name", "Year Published", "Pages",
                    "Publisher Address","Publisher Phone","Publisher Email","Head Writer", "Year Formed", "Subject");
            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("groupName");
                String title = rs.getString("bookTitle");
                String publisher = rs.getString("publisherName");
                String yearPub = rs.getString("yearPublished");
                String pages = rs.getString("numberPages");
                String addrPub = rs.getString("publisherAddress");
                String phonePub = rs.getString("publisherPhone");
                String emailPub = rs.getString("publisherEmail");
                String head = rs.getString("headWriter");
                String year = rs.getString("yearFormed");
                String subject = rs.getString("subject");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(name),dispNull(title), dispNull(publisher),dispNull(yearPub),
                        dispNull(pages),dispNull(addrPub),dispNull(phonePub),dispNull(emailPub),
                        dispNull(head), dispNull(year), dispNull(subject));
            }

            rs.close();
            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
    // 3
    public static void listAllPublishers() {
        try {
            pstmt = conn.prepareStatement(
                "SELECT publisherName FROM Publishers"
            );
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\nPublisher Name");
            System.out.println("--------------");
            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("publisherName");

                //Display values
                System.out.println(name);
            }

            rs.close();
            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     // 4
    public static void listSpecificPublisher() {
        try {
            System.out.print("\nInput a publisher name: ");
            String publisherName = in.nextLine();
            pstmt = conn.prepareStatement(
                "SELECT PUBLISHERNAME, PUBLISHERADDRESS, PUBLISHERPHONE,"
                + "PUBLISHEREMAIL, BOOKTITLE, YEARPUBLISHED, NUMBERPAGES,"
                + "GROUPNAME, HEADWRITER, YEARFORMED, SUBJECT FROM Publishers "
                + "INNER JOIN BOOKS using (PUBLISHERNAME)"
                + "INNER JOIN WRITINGGROUP using (GROUPNAME) WHERE publisherName = ?");
            pstmt.setString(1, publisherName);
            ResultSet rs = pstmt.executeQuery();

            displayFormat = "%-30s%-35s%-30s%-30s%-35s%-25s%-25s%-35s%-30s%-25s%-35s\n";
            System.out.printf(displayFormat, "Publisher", "Publisher Address", 
                    "Publisher Phone", "Publisher Email", "Book Title", "Year Published", 
                    "Pages", "Group Name", "HeadWriter", "Year Formed", "Subject");

            boolean inside = false;
            while (rs.next()) {
                inside = true;
                //Retrieve by column name
                String name = rs.getString("publisherName");
                String address = rs.getString("publisherAddress");
                String phone = rs.getString("publisherPhone");
                String email = rs.getString("publisherEmail");
                String title = rs.getString("bookTitle");
                String yearPub = rs.getString("yearPublished");
                String pages = rs.getString("numberPages");
                String groupName = rs.getString("groupName");
                String headwriter = rs.getString("headWriter");
                String yearFormed = rs.getString("yearFormed");
                String subject = rs.getString("subject");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(name),dispNull(address), dispNull(phone),dispNull(email),
                        dispNull(title),dispNull(yearPub),dispNull(pages),dispNull(groupName),
                        dispNull(headwriter), dispNull(yearFormed), dispNull(subject));
            }

            rs.close();
            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5
    public static void listAllBooks() {
        try {
            pstmt = conn.prepareStatement(
                "SELECT bookTitle FROM Books"
            );
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\nBook Title");
            System.out.println("----------");
            while (rs.next()) {
                //Retrieve by column name
                String title = rs.getString("bookTitle");

                //Display values
                System.out.println(title);
            }

            rs.close();
            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 6
    public static void listBook() {
        try {
            System.out.println("Input a book title: ");
            String bookTitle = in.nextLine();
            System.out.println("Input a group name: ");
            String writingGroup = in.nextLine();
            pstmt = conn.prepareStatement(
                    "SELECT * FROM Books NATURAL JOIN WritingGroup WHERE bookTitle = ?"
            );
            pstmt.setString(1, bookTitle);
            ResultSet rs = pstmt.executeQuery();

            displayFormat = "%-30s%-30s%-30s%-30s%-30s\n";
            System.out.printf(displayFormat, "Title", "Year Published", "Number of Pages", "Group Name", "Publisher Name");
            while (rs.next()) {
                //Retrieve by column name
                String title = rs.getString("bookTitle");
                String year = rs.getString("yearPublished");
                String pages = rs.getString("numberPages");
                String group = rs.getString("groupName");
                String publisher = rs.getString("publisherName");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(title), dispNull(year), dispNull(pages), dispNull(group), dispNull(publisher));
            }

            rs.close();
            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 7
    public static void addBook() {
        try {
            System.out.println("Input book title: ");
            String title = in.nextLine();
            while(title.isEmpty()){
                System.out.println("Title is empty. Try again");
                System.out.println("Input book title: ");
                 title = in.nextLine();
            }
            System.out.println("Input year published: ");
            String yearTemp = in.nextLine();
            while(yearTemp.isEmpty() || !yearTemp.matches("[0-9]+")){
                System.out.println("Invalid Year. Try again");
                System.out.println("Input a valid year: ");
                 yearTemp = in.nextLine();
            }
            
            
            int year = Integer.parseInt(yearTemp);
          
            System.out.println("Input number of pages: ");
            String pagesTemp = in.nextLine();
            while(pagesTemp.isEmpty() || !pagesTemp.matches("[0-9]+")){
                System.out.println("Invalid Pages input. Try again");
                System.out.println("Input number of pages: ");
                 pagesTemp = in.nextLine();
                
            }
            
            int pages = Integer.parseInt(pagesTemp);
            
            System.out.println("Input group name: ");
            String group = in.nextLine();
            while(group.isEmpty()){
                System.out.println("Group is empty. Try again");
                System.out.println("Input group name: ");
                 group = in.nextLine();
                
            }
            pstmt = conn.prepareStatement("SELECT bookTitle, groupName FROM Books WHERE bookTitle = ? AND groupName = ?");
            pstmt.setString(1, title);
            pstmt.setString(2, group);
            ResultSet rs3 = pstmt.executeQuery();
            if(rs3.next()){
                System.out.println("This book and group already exist!\nReturning to main menu");
                return;
            }
                
            pstmt = conn.prepareStatement (
                    "SELECT groupName FROM WritingGroup WHERE groupName = ?"
            );
            pstmt.setString(1,group);
            ResultSet rs2 = pstmt.executeQuery();
            if(!rs2.next()){
                System.out.println("Group does not exist\nGoing back to main menu\n");
                return;
            }
                
            
            System.out.println("Input publisher name: ");
            String publisher = in.nextLine();
            while(publisher.isEmpty()){
                System.out.println("Publisher name is empty. Try again");
                System.out.println("Input publisher name: ");
                 publisher = in.nextLine();
                
            }
            pstmt = conn.prepareStatement (
                    "SELECT publisherName FROM Publishers WHERE publisherName = ?"
            );
            pstmt.setString(1,publisher);
            rs2 = pstmt.executeQuery();
            if(!rs2.next()){
                System.out.println("Publisher does not exist\nGoing back to main menu");
                return;
            }

            pstmt = conn.prepareStatement(
                    "INSERT INTO Books (bookTitle, yearPublished, numberPages, groupName, publisherName) VALUES (?, ?, ?, ?, ?)"
            );
            pstmt.setString(1, title);
            pstmt.setInt(2, year);
            pstmt.setInt(3, pages);
            pstmt.setString(4, group);
            pstmt.setString(5, publisher);
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement(
                    "SELECT * FROM Books NATURAL JOIN WritingGroup WHERE bookTitle = ?"
            );
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            displayFormat = "%-30s%-30s%-30s%-30s%-30s\n";
            System.out.printf(displayFormat, "Title", "Year Published", "Number of Pages", "Group Name", "Publisher Name");
            while (rs.next()) {
                //Retrieve by column name
                String btitle = rs.getString("bookTitle");
                String byear = rs.getString("yearPublished");
                String bpages = rs.getString("numberPages");
                String bgroup = rs.getString("groupName");
                String bpublisher = rs.getString("publisherName");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(btitle), dispNull(byear), dispNull(bpages), dispNull(bgroup), dispNull(bpublisher));
            }
            rs.close();

            System.out.println("Book has been added!\n");

            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //8
    public static void insertPublisher()
        {
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
            System.out.println("Enter publisher that's to be replaced");
            String previousPublisher = in.nextLine();
            
            PreparedStatement pstmt2 = conn.prepareStatement (
                    "SELECT publisherName FROM Publishers WHERE publisherName = ?"
            );
            pstmt2.setString(1,previousPublisher);
            ResultSet rs2 = pstmt2.executeQuery();
            if(!rs2.next()){
                System.out.println("Old publisher does not exist\nInserting new publisher only\n");
                pstmt=conn.prepareStatement("insert into publishers values(?,?,?,?)");  
            pstmt.setString(1,name);//1 specifies the first parameter in the query  
            pstmt.setString(2,address);
            pstmt.setString(3,phone);
            pstmt.setString(4,email);
             System.out.println("inserting publisher...");
            
            pstmt.executeUpdate();  
            
             pstmt = conn.prepareStatement("SELECT * FROM Publishers WHERE publisherName = ?");
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            
            displayFormat = "%-30s%-35s%-30s%-30s\n";
            System.out.printf(displayFormat, "Publisher Name", "Publisher Address", 
                    "Publisher Phone", "Publisher Email");
            
            boolean inside = false;
            while (rs.next()) {
                inside = true;
                //Retrieve by column name
                name = rs.getString("publisherName");
                address = rs.getString("publisherAddress");
                phone = rs.getString("publisherPhone");
                email = rs.getString("publisherEmail");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(name),dispNull(address), dispNull(phone),dispNull(email));
            }
            rs.close();
            
            System.out.println("New publisher has been added!\n");  
            pstmt.close(); 
                return;
            }
            
            pstmt=conn.prepareStatement("insert into publishers values(?,?,?,?)");  
            pstmt.setString(1,name);//1 specifies the first parameter in the query  
            pstmt.setString(2,address);
            pstmt.setString(3,phone);
            pstmt.setString(4,email);
            
            System.out.println("inserting publisher!\n");
            
            pstmt.executeUpdate();  
            
            pstmt = conn.prepareStatement("UPDATE Books SET publisherName = ? WHERE publisherName = ?");
            pstmt.setString(1, name);
            pstmt.setString(2, previousPublisher);
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("DELETE FROM Publishers WHERE publisherName = ?");
            
            pstmt.setString(1, previousPublisher);
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("SELECT * FROM Publishers WHERE publisherName = ?");
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            
            displayFormat = "%-30s%-35s%-30s%-30s\n";
            System.out.printf(displayFormat, "Publisher Name", "Publisher Address", 
                    "Publisher Phone", "Publisher Email");
            
            boolean inside = false;
            while (rs.next()) {
                inside = true;
                //Retrieve by column name
                //Retrieve by column name
                name = rs.getString("publisherName");
                address = rs.getString("publisherAddress");
                phone = rs.getString("publisherPhone");
                email = rs.getString("publisherEmail");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(name),dispNull(address), dispNull(phone),dispNull(email));
            }
            rs.close();
            
            System.out.println("New publisher has been added!\n" + previousPublisher
            + " has been replaced and removed\n");

            
            
            
            pstmt.close();  
            //stmt.close();
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
            
        }//end try
        }
    
    // 9
    public static void removeBook() {
        try {
            System.out.println("Input book title to delete: ");
            String title = in.nextLine();
            System.out.println("Input corresponding writing group: ");
            String group = in.nextLine();
            while(title.isEmpty()){
                System.out.println("Title is empty. Try again");
                System.out.println("Input book title: ");
                 title = in.nextLine();
            }
            while(group.isEmpty()){
                System.out.println("Group is empty. Try again");
                System.out.println("Input writing group: ");
                 group = in.nextLine();
            }
            
            pstmt = conn.prepareStatement("SELECT bookTitle, groupName FROM Books WHERE bookTitle = ? AND groupName = ?");
            pstmt.setString(1, title);
            pstmt.setString(2, group);
            ResultSet rs3 = pstmt.executeQuery();
            if(!rs3.next()){
                System.out.println("This book and group don't exist!\nReturning to main menu\n");
                return;
            }
            
            pstmt = conn.prepareStatement(
                    "DELETE FROM Books WHERE bookTitle = ? AND groupName = ?"
            );
            pstmt.setString(1, title);
            pstmt.setString(2, group);
            pstmt.executeUpdate();

            System.out.println("Book successfully removed!\n");
            

            promptEnterKey();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        System.out.println("ENTER (q) to exit\n");
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {       
        in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        // Constructing the database URL connection string
        System.out.print("Database username: ");
        USER = in.nextLine();
         if (!USER.isEmpty()){ 
            System.out.print("Database password: ");
            PASSWORD = in.nextLine(); //PASSWORD ASKED ONLY WHERE THERES A USER
            DB_URL = DB_URL  + DBNAME + ";user="+ USER + ";password=" + PASSWORD;
        }
        else DB_URL = DB_URL + DBNAME; //NO USER OR PASS USED WHEN NO USER AND PASS IS GIVEN
        
        
        //DB_URL = DB_URL + DBNAME;
        conn = null; // initialize the connection
        pstmt = null; // initialize the statement that we're using
        
        try{
             // STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            
            while(!INPUT.equals("q"))
            {   
                displayOptions();
                System.out.print("Enter number choice: ");             
                INPUT = in.nextLine();

                switch(INPUT){
                    case "1": listAllGroups();
                        break;
                    case "2": listSpecificGroup();
                        break;
                    case "3": listAllPublishers();
                        break;
                    case "4": listSpecificPublisher();
                        break;
                    case "5": listAllBooks();
                        break;
                    case "6": listBook();
                        break;
                    case "7": addBook();
                        break;
                    case "8": insertPublisher();
                        break;
                    case "9": removeBook();
                        break;
                } 
                
            
            }
        }catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }// end finally try
        }// end try
        System.out.println("Goodbye!");      
    }   
}
