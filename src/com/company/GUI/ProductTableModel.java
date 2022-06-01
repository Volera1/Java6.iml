package com.company.GUI;

import com.company.dbw.DBwork;
import com.company.logic.Device;
import com.company.logic.Factory;

import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;

public class ProductTableModel extends AbstractTableModel {
    private Factory data;
    public ProductTableModel(Factory factory){
        this.data = factory;
    }
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:{return "Название";}
            case 1:{return "Тип";}
            case 2:{return "Использованные детали";}
            case 3:{return "ID";}
        }
        return "";
    }

    @Override
    public int getRowCount() {
        if(data.AllProducts.isEmpty()){
            return 5;
        }
        return data.AllProducts.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public java.lang.Object getValueAt(int rowIndex, int columnIndex) {
        if (data.AllProducts.isEmpty()){
            return "";
        }
        switch (columnIndex){
            case 0:{
                return data.AllProducts.get(rowIndex).Name;
            }
            case 1:{
                if (data.AllProducts.get(rowIndex) instanceof Device){
                    return ((Device) data.AllProducts.get(rowIndex)).type;
                }
                else {
                    return "Продукт";
                }
            }
            case 2:{
                return data.AllProducts.get(rowIndex).NeedListOut();
            }

            case 3:{
                return data.AllProducts.get(rowIndex).ID;
            }

        }

        return "O";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:return true;
        }
        return false;
    }
    public void delete(int ind){
        this.data.remove(ind);
        fireTableDataChanged();
    }
    @Override
    public Class<?> getColumnClass(int columnIndex){
        return String.class;
    }
@Override
public void setValueAt(java.lang.Object aValue, int rowIndex, int columnIndex){
        switch (columnIndex){
            case 0:
                try {
                    DBwork.updateProductName(data.AllProducts.get(rowIndex).ID,(String)aValue);
                    data.DBinput();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
}
