package com.company.logic;

import java.util.Scanner;

public class Device extends Product {
    public static String[] typesList=new String[]{"Микрофон","Сенсор","Диод"};
    public String type;/*
    public Device(){
        System.out.println("Введите название: ");
        Name=scanner.nextLine();
        System.out.println("Введите тип устройства: ");
        String Type = typeProverka(scanner.nextLine());
        type=Type;
        NeedestDetailWrite();
    }*/
    public Device(String NewType){
        Name="Device"+ID;
        type=NewType;
    }

    public Device(int ID, String name, String NewType) {
        this.ID=ID;
        this.Name=name;
        type=NewType;
    }
    /*
    @Override
    public boolean Work(List<Detail> AllDetail) {
        int CountOf =0;
        for (Detail All : AllDetail) {
            for (Detail Need : NeedToWorkDetail) {
                if (All.DType.equals(Need.DType)) {
                    CountOf+=1;
                }
            }
        }
        if (CountOf>=NeedToWorkDetail.size()){
            System.out.println("Деталей достаточно.");
            return true;}
        System.out.println("Создать девайс невозможно, нет необходимых деталей");
        return false;
    }*/

    public static String GettypeList(){
        return String.join(", ",typesList);
    }

    public static String typeProverka(String NewType) {
        Scanner scanner=new Scanner(System.in);
        boolean b = false;//Разрешение на запись
        for (String Str : typesList) {
            if (NewType.equals(Str)) {
                b = true;
            }
        }
        if (b == false) {
            System.out.println("Тип не найден\n" +
                    "Введите другой тип из списка типов:" +
                    GettypeList() + "\n");
            NewType = typeProverka(scanner.nextLine());
        }
        return NewType;
    }

    @Override
    public String Get() {
        return "Устройство " + ID + "  " + Name + " " + type + " " + NeedListOut();
    }
}
