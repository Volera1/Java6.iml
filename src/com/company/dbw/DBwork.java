package com.company.dbw;

import com.company.logic.Detail;
import com.company.logic.Device;
import com.company.logic.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DBwork {
    public static final String PathToDB = "Factory.db";
    public static final String URL = "jdbc:sqlite:" + PathToDB;
    public static Connection connection;

    public static void initDB() {
        try {
            connection = DriverManager.getConnection(URL);
            if (connection != null) {
                DatabaseMetaData metaData = connection.getMetaData();
                System.out.println(metaData.getDriverName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            connection = null;
        }
    }

    public static void closeDB() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTableProduct() {
        try {
            Statement statement = connection.createStatement();//Create Table if not exists
            statement.execute("Create Table if not exists 'DetailTypes' ('id' integer primary key Autoincrement, 'name' text);");
            statement.execute("Create Table if not exists 'Details' ('ID' integer primary key Autoincrement, 'name' text, 'numtype' integer,FOREIGN key (numtype) references DetailTypes (id));");
            statement.execute("Create Table if not exists 'DeviceTypes' ('devicenumtype' integer primary key Autoincrement, 'name' text);");
            statement.execute("Create Table if not exists 'Products' ('productnumber' integer primary key Autoincrement, 'name' text, 'needs' text, 'devicenumtype' integer, FOREIGN key (devicenumtype) references DeviceTypes (devicenumtype));");
            System.out.println("созданы таблички");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void addDeviceType(String title) {
        try {
            PreparedStatement statement = connection.prepareStatement("Insert into DeviceTypes('name')" + "VALUES(?);");
            statement.setObject(1, title);
            statement.execute();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addDetailType(String title) {
        try {
            PreparedStatement statement = connection.prepareStatement("Insert into DetailTypes('name')" + "VALUES(?);");
            statement.setObject(1, title);
            statement.execute();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addDetail(String name, int idType) {
        try {
            PreparedStatement statement = connection.prepareStatement("Insert into Details ('name','numtype')" + "VALUES(?,?);");
            statement.setObject(1, name);
            statement.setObject(2, idType);
            statement.execute();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addDevice(String Name, int idDevType, String needs) {
        try {
            PreparedStatement statement = connection.prepareStatement("Insert into Products('name','devicenumtype','needs')" + "VALUES(?,?,?);");
            statement.setObject(1, Name);
            statement.setObject(2, idDevType);
            statement.setObject(3, needs);
            statement.execute();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addProduct(String title, String needs) {
        try {
            PreparedStatement statement = connection.prepareStatement("Insert into Products('name','devicenumtype','needs')" + "VALUES(?,null,?);");
            statement.setObject(1, title);
            statement.setObject(2, needs);
            statement.execute();
            System.out.println("Not addProductExeption");
            statement.close();
        } catch (SQLException e) {
            System.out.println("addProductExeption");
            e.printStackTrace();
        }
    }

    public static void getDetailType() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * FROM DetailTypes");
        while (result.next()) {
            System.out.println(result.getString(2));
        }
        statement.close();
    }

    public static ArrayList<Detail> getDetail() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select Details.ID, Details.name, DetailTypes.name FROM Details join DetailTypes on Details.numtype=DetailTypes.id");
        ArrayList<Detail> newDetails = new ArrayList<>();
        while (result.next()) {
            newDetails.add(new Detail(result.getInt(1), result.getString(2), result.getString(3)));
        }
        statement.close();
        return newDetails;
    }

    public static void getDeviceType() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * FROM DeviceTypes");
        while (result.next()) {
            System.out.println(result.getString(2));
        }
        statement.close();
    }

    public static ArrayList<Product> getProduct() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select Products.productnumber ,Products.name, Products.needs, DeviceTypes.name FROM Products left join DeviceTypes on DeviceTypes.idDP=Products.devicenumtype");
        ArrayList<Product> DBProducts = new ArrayList<>();
        while (result.next()) {
            Product resPart;
            if (result.getString(4) == null) { //DeviceType
                resPart = new Product(result.getInt(1), result.getString(2));
            } else {
                resPart = new Device(result.getInt(1), result.getString(2), result.getString(4));
            }
            resPart.SetNeedestDetail(DBwork.StringToHashMap(result.getString(3)));
            DBProducts.add(resPart);
        }
        statement.close();
        return DBProducts;
    }

    public static void deleteAllTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from Products");
        statement.execute();

        statement.close();
    }

    public static String[] getDetailTypes() throws SQLException {
        Statement statement1 = connection.createStatement();
        Statement statement2 = connection.createStatement();
        ResultSet listsize = statement1.executeQuery("select count(*) from DetailTypes");
        if (listsize.getInt(1) < 1) {
            return new String[]{"DetailType is null"};
        }
        int size = listsize.getInt(1);
        ResultSet result = statement2.executeQuery("select name FROM DetailTypes");
        String[] list = new String[size];
        int k = 0;
        while (result.next()) {
            list[k] = result.getString(1);
            k += 1;
        }
        statement1.close();
        statement2.close();
        return list;
    }

    public static String[] getDeviceTypes() throws SQLException {
        Statement statement1 = connection.createStatement();
        Statement statement2 = connection.createStatement();
        ResultSet result = statement1.executeQuery("select name FROM DeviceTypes");
        ResultSet listsize = statement2.executeQuery("select count(*) from DeviceTypes");
        if (listsize.getInt(1) < 1) {
            return new String[]{"DetailType is null"};
        }
        String[] list = new String[listsize.getInt(1)];
        int k = 0;
        while (result.next()) {
            list[k] = result.getString(1);
            k += 1;
        }
        statement1.close();
        statement2.close();
        return list;
    }

    public static int getThisDetailType(String name) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * FROM DetailTypes");
        while (result.next()) {
            if (name.compareTo(result.getString(2)) == 0) {
                return result.getInt(1);
            }
        }
        statement.close();
        return 0;
    }

    public static int getThisDeviceType(String name) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * FROM DeviceTypes");
        while (result.next()) {
            if (name.compareTo(result.getString(2)) == 0) {
                return result.getInt(1);
            }
        }
        statement.close();
        return 0;
    }

    public static int getDeviceID(String name) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * FROM DeviceTypes");
        while (result.next()) {
            if (name.compareTo(result.getString(2)) == 0) {
                return result.getInt(1);
            }
        }
        statement.close();
        return 0;
    }

    public static HashMap<String,Integer> StringToHashMap(String map) {
        HashMap<String,Integer> newMap=new HashMap<String,Integer>();
        String[] keyCount=map.split(",");
        for(String pair :keyCount){
            pair=pair.trim();
            String[] entry = pair.split(" ");
            newMap.put(entry[0].trim(), Integer.parseInt(entry[1].trim()));
        }
            return newMap;
    }
    public static void deleteProduct(int deletedID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("Delete from Products where productnumber=?");
        statement.setInt(1,deletedID);
        statement.execute();
        statement.close();
    }

    public static void deleteDetail(int deletedID) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("Delete from Details where ID=?");
        statement.setInt(1,deletedID);
        statement.execute();
        statement.close();
    }
    public static void updateProductName(int ind,String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update Products set name=? where productnumber=?");
        statement.setString(1,name);
        statement.setInt(2,ind);
        statement.execute();
        statement.close();
    }
    public static void updateDetailName(int ind,String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update Details set name=? where ID=?");
        statement.setString(1,name);
        statement.setInt(2,ind);
        statement.execute();
        statement.close();
    }
}