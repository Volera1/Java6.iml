package com.company.logic;

import com.company.logic.Object;

import java.util.Scanner;

public class Detail extends Object {
    public static String[] DTypesList = new String[]{"Болт", "Винт", "Корпус"};
    public String DType;
    public String Name = "Detail";

    public Detail (String NewType){
        //typeProverka(NewType);
        IDcount+=1;
        ID=IDcount;
        Name+=ID;
        DType=NewType;
    }
    public Detail() {
        IDcount +=1;
        this.ID=IDcount;
        System.out.println("Введите название: ");
        Name=scanner.nextLine();
        System.out.println("Введите тип детали: ");
        String Type = typeProverka(scanner.nextLine());
        DType=Type;
    }
    public Detail(String NewName, String NewType){
        IDcount+=1;
        this.ID=IDcount;
        Name=NewName;
        DType=NewType;
    }
    public Detail(int ID,String NewName, String NewType){
        this.ID=ID;
        Name=NewName;
        DType=NewType;
    }

    public static String typeProverka(String NewType) {
        Scanner scanner=new Scanner(System.in);
        boolean b = false;//Разрешение на запись
        for (String Str : DTypesList) {
            if (NewType.equals(Str)) {
                b = true;
            }
        }
        if (b == false) {
            System.out.println("Тип не найден\n" +
                    "Введите другой тип из списка типов:" +
                    GetDTypeList() + "\n");
            return typeProverka(scanner.nextLine());
        }
        return NewType;
    }

    public static String GetDTypeList() {
        return String.join(", ", DTypesList);
    }

    @Override
    public String Get(){
        String get = "Деталь  "+ID+"  "+Name+"  "+DType;
        return get;
    }

    public void setName(String aValue) {
        this.Name=aValue;
    }
}
