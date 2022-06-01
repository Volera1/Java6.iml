package com.company.logic;

import com.company.dbw.DBwork;
import org.sqlite.core.DB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

public class Factory {
    public ArrayList<Product> AllProducts = new ArrayList<>();
    public ArrayList<Detail> AllDetail = new ArrayList<>();

    public void DeliteDetail(Detail D) {
        AllDetail.remove(D);
    }
    public void Delite(int ind) throws SQLException {
        DBwork.deleteDetail(AllDetail.get(ind).ID);
        this.DBinput();
    }

    public void Vivod() {
        if (AllProducts.isEmpty()){
            System.out.println("Продуктов и устройств нет\n");
        }
        else {
            System.out.println("Вид   \t  ID\t Название\tТип \t Список необходимых деталей");
            for (Product P : AllProducts) {
                P.Get();
            }}
    }

    public void VivodDetail() {
        if (AllDetail.isEmpty()){
            System.out.println("Деталей нет\n");}
        else {
            System.out.println("Вид\t  ID\tНазвание\tТип");
            for (Detail P : AllDetail) {
                P.Get();
            }}
    }

    public String searchByName(String name) {
        Boolean find = false;
        String search = "";
        for (Product P : AllProducts) {
            if (P.Name.contains(name)) {
                search+=P.Get() + '\n';
                find = true;
            }
        }
        if (find == false)
            search="Не найден такой продукт\n";
        return search;
    }

    public String searchByNameDetail(String name) {
        Boolean find = false;
        String search = "";
        for (Detail D : AllDetail) {
            if (D.Name.contains(name)) {
                search+=D.Get()+"\n";
                find = true;
            }
        }
        if (find == false)
            search="Не найдена такая деталь\n";
        return search;
    }

    public void searchToDelite(String name) {
        Boolean find = false;

        try {
            for (Product P : AllProducts) {
                if (P.Name.contains(name)) {
                    find = true;AllProducts.remove(P);
                }
            }
        } catch (ConcurrentModificationException ex) {
        }
        if (find == false)
            System.out.println("Не найден такой продукт");
    }

    public void remove(int ind) {
        try {
            DBwork.deleteProduct(AllProducts.get(ind).ID);
            this.DBinput();
        } catch (SQLException e) {
            System.out.println("Exep on facctory");
            e.printStackTrace();
        }
    }

    public void addDetail(String type){
        try {
            int typeint = DBwork.getThisDetailType(type);
            if (typeint>0){
            Detail D = new Detail(type);
            DBwork.addDetail(D.Name, typeint);
            this.DBinput();
            }
            else {
                System.out.println("Type Exeption");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addDevice(String item,HashMap<String, Integer> newDeviceDetails) {
        try {
            int typeint = DBwork.getThisDeviceType(item);
            if (typeint > 0) {
                Device newDevice = new Device(item);
                newDevice.SetNeedestDetail(newDeviceDetails);
                DBwork.addDevice(newDevice.Name,typeint,newDevice.NeedListOut());
                this.AllProducts.add(newDevice);
            } else {
                System.out.println("Type Exeption");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void DBinput() throws SQLException {
        AllDetail=DBwork.getDetail();
        AllProducts=DBwork.getProduct();
    }
}
