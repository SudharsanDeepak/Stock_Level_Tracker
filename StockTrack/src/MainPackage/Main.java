package MainPackage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static final String DB_URL="jdbc:mysql://localhost:3306/stocktracker";
    private static final String DB_USER="root";
    private static final String DB_PASSWORD="root";

    private Connection connect()throws SQLException{
        return DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
    }

    private void addProductInventory(int product_id,int stock_level){
        String insertInventorySQL="INSERT INTO Inventory (product_id,stock_level) VALUES (?,?)";
        try(Connection conn=connect();PreparedStatement stmt=conn.prepareStatement(insertInventorySQL)){
            stmt.setInt(1,product_id);
            stmt.setInt(2,stock_level);
            stmt.executeUpdate();
            System.out.println("Inventory added successfully for product ID:"+product_id);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void updateStockLevel(int productId,int quantity){
        String updateInventorySQL="UPDATE Inventory SET stock_level=stock_level+?,last_updated=? WHERE product_id=?";
        try(Connection conn=connect();PreparedStatement stmt=conn.prepareStatement(updateInventorySQL)){
            stmt.setInt(1,quantity);
            stmt.setTimestamp(2,Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3,productId);
            int rowsAffected=stmt.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Stock updated for product ID:"+productId);
                checkAndGenerateAlert(conn,productId);
            }else{
                System.out.println("Product ID not found in inventory.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void checkAndGenerateAlert(Connection conn,int productId)throws SQLException{
        String checkStockSQL="SELECT stock_level FROM Inventory WHERE product_id=?";
        try(PreparedStatement stmt=conn.prepareStatement(checkStockSQL)){
            stmt.setInt(1,productId);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                int stockLevel=rs.getInt("stock_level");
                if(stockLevel<10){
                    String alertMessage="Low stock alert for product ID "+productId;
                    generateAlert(conn,productId,alertMessage);
                }
            }
        }
    }

    private void generateAlert(Connection conn,int productId,String message)throws SQLException{
        String insertAlertSQL="INSERT INTO InventoryAlert (product_id,alert_message,alert_time) VALUES (?,?,?)";
        try(PreparedStatement stmt=conn.prepareStatement(insertAlertSQL)){
            stmt.setInt(1,productId);
            stmt.setString(2,message);
            stmt.setTimestamp(3,Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            System.out.println("ALERT:"+message);
        }
    }

    private void displayAllAlerts(){
        String selectAlertsSQL="SELECT * FROM InventoryAlert";
        try(Connection conn=connect();Statement stmt=conn.createStatement();ResultSet rs=stmt.executeQuery(selectAlertsSQL)){
            while(rs.next()){
                System.out.println("Alert ID:"+rs.getInt("alert_id")+", Product ID:"+rs.getInt("product_id")+
                                   ", Message:"+rs.getString("alert_message")+", Time:"+rs.getTimestamp("alert_time"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void displayInventory(){
        String selectInventorySQL="SELECT * FROM Inventory";
        try(Connection conn=connect();Statement stmt=conn.createStatement();ResultSet rs=stmt.executeQuery(selectInventorySQL)){
            while(rs.next()){
                System.out.println("Inventory ID:"+rs.getInt("inventory_id")+", Product ID:"+rs.getInt("product_id")+
                                   ", Stock Level:"+rs.getInt("stock_level")+", Last Updated:"+rs.getTimestamp("last_updated"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Main app=new Main();
        Scanner scanner=new Scanner(System.in);
        while(true){
            System.out.println("Select operation:\n1.Add Inventory\n2.Update Stock\n3.Display Inventory\n4.Show Alerts\n0.Exit");
            int choice=scanner.nextInt();
            if(choice==1){
                System.out.print("Enter Product ID and Stock Level to Add:");
                int productId=scanner.nextInt();
                int stockLevel=scanner.nextInt();
                app.addProductInventory(productId,stockLevel);
            }else if(choice==2){
                System.out.print("Enter Product ID and quantity to add:");
                int productId=scanner.nextInt();
                int quantity=scanner.nextInt();
                app.updateStockLevel(productId,quantity);
            }else if(choice==3){
                app.displayInventory();
            }else if(choice==4){
                app.displayAllAlerts();
            }else if(choice==0){
                System.out.println("Exiting application.");
                break;
            }else{
                System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
}