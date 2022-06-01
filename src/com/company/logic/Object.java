package com.company.logic;

import java.util.Scanner;

public abstract class Object {
    public int ID;
    protected static int IDcount=0;
    Scanner scanner = new Scanner(System.in);
    public int GetID () {
        return ID;
    }
    public static int GetIDcount(){return IDcount;}
    public void SetID(int newID) {ID=newID;}
    public abstract String Get();

}
