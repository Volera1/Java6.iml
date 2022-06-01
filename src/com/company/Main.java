package com.company;

import com.company.GUI.DetailTableModel;
import com.company.GUI.ProductTableModel;
import com.company.dbw.DBwork;
import com.company.logic.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    Scanner scanner=new Scanner(System.in);
    public static void main(String[] args) throws SQLException {
        Factory F=new Factory();
        MyFrame Fram = new MyFrame(F);
    }

}
class MyFrame extends JFrame{
    public JTextField NameF= new JTextField(15);
    public JTextField TypeF= new JTextField(15);
    public JTextField NeedDetailF= new JTextField(15);
    public DetailTableModel detailTableModel;
    public JTable detailTable =new JTable();
    public ProductTableModel productTableModel;
    public JTable productTable =new JTable();
    public SpinnerPanel deviceSpinnerPanel;
    MyComboBoxDialog DetailboxDialog;
    MyComboBoxDialog DeviceboxDialog;

    public MyFrame(Factory F) throws SQLException {
        super("Fabric");
        this.setMinimumSize(new Dimension(700,300));
        this.setSize(700,250);
        //инициализируем БД
        DBwork.initDB();
        DBwork.createTableProduct();
        //Элементы создаем
        MyBut B1 = new MyBut("Ввести новую деталь");
        MyBut B2 = new MyBut("Ввести новый продукт");
        MyBut B3 = new MyBut("Ввести новое устройство");
        MyBut SearchBut = new MyBut("Найти по названию");
        MyBut DeleteProductBut = new MyBut("Удалить продукт");
        MyBut DeleteDetailBut=new MyBut("Удалить деталь");
        //Таблицы и модели таблиц
        F.DBinput();
        detailTableModel = new DetailTableModel(F);
        detailTable.setModel(detailTableModel);
        productTableModel=new ProductTableModel(F);
        productTable.setModel(productTableModel);
        //Диалоговые окна
        String[] DeviceTypes = new String[]{"Exeption"};
        String[] DetailTypes = new String[]{"Exeption"};
        try {DeviceTypes= DBwork.getDeviceTypes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DeviceboxDialog = new MyComboBoxDialog(this, DeviceTypes ,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ((MyComboBoxDialog)e.getSource()).setVisible(false);
                if (DeviceboxDialog!=null){
                    String item= (String) DeviceboxDialog.box.getSelectedItem();
                    HashMap<String, Integer> newDeviceDetails = MyFrame.this.deviceSpinnerPanel.getDetailsMap();
                    F.addDevice(item,newDeviceDetails);
                    productTableModel.fireTableDataChanged();
                }
                MyFrame.this.setVisible(true);
            }
        });
        try {DetailTypes= DBwork.getDetailTypes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DetailboxDialog = new MyComboBoxDialog(this,DetailTypes , new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(DetailboxDialog!=null) {
                    String item = (String) DetailboxDialog.box.getSelectedItem();
                    F.addDetail(item);
                    detailTableModel.fireTableDataChanged();
                }
                MyFrame.this.setVisible(true);
            }
        });

        deviceSpinnerPanel = new SpinnerPanel();
        DetailboxDialog.setMinimumSize(new Dimension(200,200));
        DeviceboxDialog.add(deviceSpinnerPanel,BorderLayout.WEST);
        //Диалоговое окно продукта
        JDialog ProductDialog = new JDialog();
        SpinnerPanel spinPaneToProduct= new SpinnerPanel();
        ProductDialog.add(spinPaneToProduct);
        MyBut OkProductDialog = new MyBut("Ok");
        ProductDialog.add(OkProductDialog,BorderLayout.SOUTH);
        OkProductDialog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product newP = new Product();
                newP.SetNeedestDetail(spinPaneToProduct.getDetailsMap());
                F.AllProducts.add(newP);
                System.out.println("Product ok");
                try {
                    DBwork.addProduct(newP.Name, newP.NeedListOut());
                    F.DBinput();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                productTableModel.fireTableDataChanged();
                ProductDialog.setVisible(false);
                MyFrame.this.setVisible(true);
            }
        });
        ProductDialog.setVisible(false);
        ProductDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MyFrame.this.setVisible(true);
            }
        });
        ProductDialog.setLocationRelativeTo(null);
        ProductDialog.setMinimumSize(new Dimension(200,200));

        //Добавляем таблицы в скролл
        JScrollPane Scroll = new JScrollPane(detailTable);
        JScrollPane ProductScroll = new JScrollPane(productTable);


        //Действия на кнопки
        B1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFrame.this.setVisible(false);
                DetailboxDialog.setVisible(true);
            }
        });
        B2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFrame.this.setVisible(false);
                ProductDialog.setVisible(true);
            }
        });
        B3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFrame.this.setVisible(false);
                DeviceboxDialog.setVisible(true);
                //HashMap<String,Integer> newDeviceDetails = new HashMap<>(deviceSpinnerPanel.getDetailsMap());
                //F.AllProducts.get(F.AllProducts.size()-1).SetNeedestDetail(newDeviceDetails);
                productTableModel.fireTableDataChanged();
            }

        });
        DeleteDetailBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    detailTableModel.delete(detailTable.getSelectedRow());
                }
                catch (Exception ex){
                    JDialog DeleteExeption = new JDialog(MyFrame.this, "Ошибка удаления",true);
                    JLabel lab = new JLabel("Выделите строку в таблице деталей",JLabel.CENTER);
                    DeleteExeption.setMinimumSize(new Dimension(300,100));
                    DeleteExeption.add(lab,BorderLayout.CENTER);
                    DeleteExeption.setLocationRelativeTo(null);
                    DeleteExeption.setVisible(true);
                }
            }
        });
        DeleteProductBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    productTableModel.delete(productTable.getSelectedRow());
                }
                catch (Exception ex){
                    JDialog DeleteExeption = new JDialog(MyFrame.this, "Ошибка удаления",true);
                    JLabel lab = new JLabel("Выделите строку в таблице продуктов",JLabel.CENTER);
                    DeleteExeption.setMinimumSize(new Dimension(300,100));
                    DeleteExeption.add(lab,BorderLayout.CENTER);
                    DeleteExeption.setLocationRelativeTo(null);
                    DeleteExeption.setVisible(true);
                    DeleteExeption.setLocationRelativeTo(null);
                }
            }
        });
        SearchBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialogSearch = new JDialog();
                String text = "";
                text+=F.searchByName(NameF.getText());
                text+=F.searchByNameDetail(NameF.getText());

                dialogSearch.setLocationRelativeTo(null);
                dialogSearch.setMinimumSize(new Dimension(400,300));
                dialogSearch.setVisible(true);
                dialogSearch.add(new JTextArea(text));
            }

        });


        //Работа с планировщиками
        final JPanel VvodPanel = new JPanel();
        final JPanel mPanel =  new JPanel(false);
        Dimension SizeOfmPanel = new Dimension(300,250);
        mPanel.setPreferredSize(SizeOfmPanel);
        VvodPanel.add(new JLabel("Название"));
        VvodPanel.add(NameF);
        mPanel.add(B1);
        mPanel.add(B2);
        mPanel.add(B3);
        mPanel.add(DeleteDetailBut);
        mPanel.add(DeleteProductBut);
        mPanel.add(SearchBut);

        mPanel.add(VvodPanel);

        add(mPanel,BorderLayout.WEST);

        Scroll.setSize(new Dimension(400,250));

        ProductScroll.setSize(new Dimension(400,250));

        JTabbedPane T=new JTabbedPane();
        T.add(Scroll,"Детали");
        T.add(ProductScroll,"Продукты");
        add(T);


        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DBwork.closeDB();
            }
        });
        this.setVisible(true);
    }

}
class MyBut extends JButton {
    public static Dimension ButtonSize = new Dimension(250,22);
    public MyBut(String s){
        super(s);
        this.setMinimumSize(ButtonSize);
        this.setPreferredSize(ButtonSize);

    }
}
//Диалоговое окно с выпадающим списком
class MyComboBoxDialog extends JDialog{
    JPanel ComboDPanel=new JPanel();
    String item = "a";
    MyBut Ok = new MyBut("OK");
    JComboBox box;
    public MyComboBoxDialog(JFrame parent, String[] list,ActionListener okListener){
        super(parent);
        //this.getParent().setVisible(true);
        this.setTitle("Тип новой записи");
        box = new JComboBox(list);
        box.setPreferredSize(MyBut.ButtonSize);
        box.setMinimumSize(MyBut.ButtonSize);
        Dimension DialogSize = new Dimension(500,200);

        Ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyComboBoxDialog.this.setVisible(false);
            }
        });
        Ok.addActionListener(okListener);

        box.setPreferredSize(new Dimension(100,22));
        box.setMaximumSize(new Dimension(100,22));

        // ComboDPanel.add(new JLabel("Выберите тип. Его нельзя будет изменить"),BorderLayout.CENTER);
        ComboDPanel.add(box,BorderLayout.CENTER);
        ComboDPanel.add(Ok,BorderLayout.SOUTH);

        add(ComboDPanel);
        this.setLocationRelativeTo(null);
        this.setSize(DialogSize);
        this.setPreferredSize(DialogSize);
        this.setMinimumSize(DialogSize);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MyComboBoxDialog.this.getParent().setVisible(true);
            }
        });
        this.setVisible(false);
    }
}

class SpinnerPanel extends JPanel {
    //JLabel[] labels = new JLabel[];
    HashMap<String, Integer> detailsMap = new HashMap<>();
    HashMap<String, JSpinner> ListOfSpinners = new HashMap<>();

    public SpinnerPanel() {
        this.setLayout(new GridLayout(3, 2));
        for (String el : Detail.DTypesList) {
            JPanel jpanelAL = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            jpanelAL.add(new JLabel(el));

            JPanel jpanelA = new JPanel(new FlowLayout(FlowLayout.LEFT));
            ListOfSpinners.put(el, new JSpinner());
            ListOfSpinners.get(el).setModel(new SpinnerNumberModel());
            jpanelA.add(ListOfSpinners.get(el));

            this.add(jpanelAL);
            this.add(jpanelA);
            detailsMap.put(el, (Integer) ListOfSpinners.get(el).getValue());
        }      //  System.out.println(jpanel.getLayout().getClass());

       /*labelPanel.setLayout(new BoxLayout(labelPanel,BoxLayout.Y_AXIS));
        labelPanel.add(new MyBut("jkj"));
        labelPanel.add(AL);
        labelPanel.add(BL);
        labelPanel.add(CL);
        add(labelPanel);
        spinnerPanel.setLayout(new BoxLayout(spinnerPanel,BoxLayout.Y_AXIS));
        spinnerPanel.add(A);
        spinnerPanel.add(B);
        spinnerPanel.add(C);
        add(spinnerPanel);
*/
        //Dimension SpinnerSize=new Dimension(50,25);
        //A.setPreferredSize(SpinnerSize);
        //B.setPreferredSize(SpinnerSize);
        //C.setPreferredSize(SpinnerSize);
        this.setSize(550, 30);
        this.setMaximumSize(new Dimension(550, 30));
    }

    public HashMap<String, Integer> getDetailsMap() {
        for (String el : Detail.DTypesList) {
            detailsMap.put(el, (Integer) ListOfSpinners.get(el).getValue());
        }
        return detailsMap;
    }
}

