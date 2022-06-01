package com.company.logic;

import com.company.logic.Detail;
import com.company.logic.Factory;
import com.company.logic.Product;

import java.util.Scanner;

public class MenuClass {
    public static void Menu(Factory F, String c) {

            System.out.println("1. Ввести новую деталь\n" +
                    "2. Ввести новый продукт\n" +
                    "3. Ввести новое устройство\n" +
                    "4. Удалить продукт\n" +
                    "5. Работа фабрики (Создать продукты с использованием деталей)\n" +
                    "6. Поиск по названию продукта\n" +
                    "7. Вывести список всех продуктов\n" +
                    "8. Вывести список всех деталей\n" +
                    "9. Выход из программы");
            Scanner scanner = new Scanner(System.in);
            switch (c) {
               /* case "1": {
                    Detail D = new Detail();
                    F.addDetail(D);
                    break;
                }*/
                case "2": {
                    F.AllProducts.add(new Product());
                    break;
                }
                //case "3": {
                  //  F.AllProducts.add(new Device());
                    //break;
                //}
                /*case "4": {
                    System.out.println("Введите название для удаления");
                    F.searchToDelite(scanner.nextLine());
                    break;
                    }*/
                /*
                case "5": {
                    MenuWork(F);
                    break;
                }*/
                /*case "6": {
                    System.out.println("Введите название или его часть для поиска");
                    F.searchByName(scanner.nextLine());
                    break;
                }
                case "7": {
                    F.Vivod();
                    break;
                }
                case "8": {
                    F.VivodDetail();
                    break;
                }
                case "9": {
                    return;
                }
                default: {
                    System.out.println("Атата, нет такого пункта меню, введите новый");
                    break;
                }*/
            }

    }

   /* public static void MenuWork(Factory F) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Работа по созданию продукта\n" +
                    "2.Работа по созданию устройства\n" +
                    "3. Прекращение работы (ничего не создать)");
            switch (scanner.nextLine()) {
                case "1": {
                    System.out.println("Введите название: ");
                    Product P = new Product(scanner.nextLine());

                    if (P.Work(F.AllDetail)) {
                        F.AllProducts.add(P);
                        for (Detail Need : P.NeedToWorkDetail) {
                            for (Detail All : F.AllDetail) {
                                if (Need.DType.contains(All.DType)) {
                                    F.DeliteDetail(All);

                                }
                            }
                        }
                    }
                    break;
                }
                case "2": {
                    Device Dev = new Device();
                    if (Dev.Work(F.AllDetail)) {
                        F.AllProducts.add(Dev);
                        for (Detail Need : Dev.NeedToWorkDetail) {
                            for (Detail All : F.AllDetail) {
                                if (Need.DType.contains(All.DType)) {
                                    F.DeliteDetail(All);
                                }
                            }
                        }
                    }
                }
                break;
                case "3":{
                    return;
                }
                default: {
                    System.out.println("Нет такого пункта меню, введите новый");
                    break;
                }

            }
        }
    }*/
}
