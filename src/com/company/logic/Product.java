package com.company.logic;

import com.company.logic.Detail;
import com.company.logic.Object;

import java.util.HashMap;
import java.util.Scanner;

public class Product  extends Object {
    HashMap<String, Integer> NeedToWorkDetail;
    //protected ArrayList<com.company.logic.Detail> NeedToWorkDetail = new ArrayList<>();
    public String Name = "Product";
    public Product (){
        IDcount +=1;
        this.ID=IDcount;
        Name+=ID;
    }
    public Product (String NewName){
        IDcount +=1;
        this.ID=IDcount;
        Name=NewName;
        NeedestDetailWrite();
    }
    public Product (int[] Needest){
        IDcount +=1;
        this.ID=IDcount;
        Name+=ID;
        SetNeedestDetail(Needest);
    }
    public Product (int ID,String name){
        this.ID=ID;
        Name+=ID;
       // SetNeedestDetail(Needest);
    }

   /* public boolean Work(List<com.company.logic.Detail> AllDetail) {
        int CountOf = 0;
        for (com.company.logic.Detail All : AllDetail) {
            for (com.company.logic.Detail Need : NeedToWorkDetail) {
                if (All.DType.equals(Need.DType)) {
                    CountOf += 1;
                }
            }
        }
        if (CountOf >= NeedToWorkDetail.size()) {
            System.out.println("Деталей достаточно.");
            return true;
        }
        System.out.println("Создать продукт невозможно, нет необходимых деталей");
        return false;
    }
*/
    public void setName(String s){
        this.Name=s;
    }

    public String NeedListOut() {
        String out="";
        for (String il:NeedToWorkDetail.keySet()){
            if (NeedToWorkDetail.get(il)>0){
                out+=il+" "+NeedToWorkDetail.get(il);
                out+=", ";
            }

        }
        if (out.length()>2){out=out.substring(0,out.length()-2);}
        return out;
    }
    public static int IntProverka(String Num) {
        Scanner scanner = new Scanner(System.in);
        int Count=0;
        try {
            Count= Integer.parseInt(Num);
            if (Count<0){
                throw new NumberFormatException();
            }
        } catch (NumberFormatException E) {
            System.out.println("Некорректо введено количество. Введите снова");
            Num=scanner.nextLine();
            Count= IntProverka(Num);
        }
        return Count;
    }
    public void NeedestDetailWrite(){
        for (int il = 0; il< Detail.DTypesList.length; il++) {
            NeedToWorkDetail.put(Detail.DTypesList[il],0);
        }
    }

    @Override
    public String Get(){
        return "Продукт  "+ID+"  "+Name+"  "+NeedListOut();
    }

    public void SetNeedestDetail(int[] NeedCounts){
        int Count=0;
        for (String il:NeedToWorkDetail.keySet()){
            NeedToWorkDetail.put(il,NeedCounts[Count]);
            Count+=1;
        }
    }
    public void SetNeedestDetail(HashMap<String,Integer> Map){
        NeedToWorkDetail=(HashMap<String,Integer>)Map.clone();
    }

}
