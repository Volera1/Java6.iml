package com.company.GUI;
import com.company.dbw.DBwork;
import com.company.logic.Factory;

import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;

public class DetailTableModel extends AbstractTableModel{
    private Factory data;
    public DetailTableModel(Factory factory){
        this.data = factory;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:{return "Название";}
            case 1:{return "Тип";}
            case 2:{return "ID";}
        }
        return "";
    }

    @Override
    public int getRowCount() {
        if(data.AllDetail.isEmpty()){
            return 5;
        }
        return data.AllDetail.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public java.lang.Object getValueAt(int rowIndex, int columnIndex) {
        if (data.AllDetail.isEmpty()){
            return "";
        }
        switch (columnIndex){
            case 0:{
                return data.AllDetail.get(rowIndex).Name;
            }
            case 1:{
                return data.AllDetail.get(rowIndex).DType;
            }
            case 2:{
                return data.AllDetail.get(rowIndex).ID;
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
    public void delete(int ind) throws SQLException {
        this.data.Delite(ind);
        this.data.DBinput();
        fireTableDataChanged();
    }
    @Override
    public Class<?> getColumnClass(int columnIndex){
        return String.class;
    }
    public void setValueAt(java.lang.Object aValue, int rowIndex, int columnIndex){
        switch (columnIndex){
            case 0:
                try {
                    DBwork.updateDetailName(data.AllDetail.get(rowIndex).ID,(String)aValue);
                    data.DBinput();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
}
