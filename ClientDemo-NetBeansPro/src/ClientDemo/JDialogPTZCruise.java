/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogCruise.java
 *
 * Created on 2009-11-2, 19:36:07
 */
/**
 *
 * @author Administrator
 */

package ClientDemo;

import com.sun.jna.NativeLong;
import javax.swing.JOptionPane;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/*****************************************************************************
 *类 ：JDialogPTZCruise
 *类描述 ：设置轮巡路径
 ****************************************************************************/
public class JDialogPTZCruise extends javax.swing.JDialog
{

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    public static final int MAX_CRUISE_SEQ = 32;
    public static final int MAX_CRUISE_POINT = 32;
    public static final int MAX_CRUISE_PRESET = 128;
    public static final int MAX_CRUISE_TIME = 255;
    public static final int MAX_CRUISE_SPEED = 15;
    private int m_iCruiseNum;
    private int m_iSeqPoint;
    private int m_iPresetNum;
    private int m_iSeqDwell;//time interval
    private int m_iSeqSpeed;
    private int m_iChanNum;//通道号，不是通道索引
    private NativeLong m_lRealHandle;//预览句柄

    public static ArrayList<Integer> cruiseRoute1 = new ArrayList<>();
    public static ArrayList<Integer> cruiseRoute2 = new ArrayList<>();
    public static ArrayList<Integer> cruiseRoute3 = new ArrayList<>();
    public static ArrayList<Integer> cruiseRoute4 = new ArrayList<>();
    public static ArrayList<Integer> cruiseRoute5 = new ArrayList<>();
    public static HashMap<Integer, ArrayList<Integer>> cruiseRoute = new HashMap<>();
    public static HashMap<Integer, String> cruiseString = new HashMap<>();

    public static String cruiseString1 = "";
    public static String cruiseString2 = "";
    public static String cruiseString3 = "";
    public static String cruiseString4 = "";
    public static String cruiseString5 = "";

    /*************************************************
    函数:      JDialogPTZCruise
    函数描述:	构造函数   Creates new form JDialogPTZCruise
     *************************************************/
    public JDialogPTZCruise(java.awt.Frame parent, boolean modal, NativeLong lRealHandle) throws IOException {
        super(parent, modal);
        initComponents();
        initDialog();
        m_lRealHandle = lRealHandle;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() throws IOException {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxCruiseRoute = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxCruisePoint = new javax.swing.JComboBox();
        jComboBoxTime = new javax.swing.JComboBox();
        jComboBoxSpeed = new javax.swing.JComboBox();
        jComboBoxPresetPoint = new javax.swing.JComboBox();
        jButtonAdd = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();

        jTextField = new javax.swing.JTextField();
        jButtonRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("轮巡路径");
        getContentPane().setLayout(null);

        cruiseRoute1 = readFileArrayList(1);
        cruiseRoute2 = readFileArrayList(2);
        cruiseRoute3 = readFileArrayList(3);
        cruiseRoute4 = readFileArrayList(4);
        cruiseRoute5 = readFileArrayList(5);

        cruiseRoute.put(1, cruiseRoute1);
        cruiseRoute.put(2, cruiseRoute2);
        cruiseRoute.put(3, cruiseRoute3);
        cruiseRoute.put(4, cruiseRoute4);
        cruiseRoute.put(5, cruiseRoute5);

        cruiseString1 = readFileString(1);
        cruiseString2 = readFileString(2);
        cruiseString3 = readFileString(3);
        cruiseString4 = readFileString(4);
        cruiseString5 = readFileString(5);

        cruiseString.put(1, cruiseString1);
        cruiseString.put(2, cruiseString2);
        cruiseString.put(3, cruiseString3);
        cruiseString.put(4, cruiseString4);
        cruiseString.put(5, cruiseString5);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        jLabel1.setText("轮巡路径");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 20, 60, 15);

        jPanel1.add(jComboBoxCruiseRoute);
        jComboBoxCruiseRoute.setBounds(100, 20, 110, 21);
        jComboBoxCruiseRoute.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent jComboBoxCruiseRoute) {

                if(jComboBoxCruiseRoute.getItem().equals("轮巡路径1"))
                    jTextField.setText(cruiseString.get(1));
                else if(jComboBoxCruiseRoute.getItem().equals("轮巡路径2"))
                    jTextField.setText(cruiseString.get(2));
                else if(jComboBoxCruiseRoute.getItem().equals("轮巡路径3"))
                    jTextField.setText(cruiseString.get(3));
                else if(jComboBoxCruiseRoute.getItem().equals("轮巡路径4"))
                    jTextField.setText(cruiseString.get(4));
                else if(jComboBoxCruiseRoute.getItem().equals("轮巡路径5"))
                    jTextField.setText(cruiseString.get(5));
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("轮巡点")));
        jPanel2.setLayout(null);

        jLabel2.setText("轮巡点");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(10, 20, 40, 15);

        //jLabel3.setText("预置点");
        //jPanel2.add(jLabel3);
        //jLabel3.setBounds(140, 20, 50, 15);

        jLabel4.setText("当前路径轮巡点");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(10, 60, 100, 15);

        //jLabel5.setText("速度");
        //jPanel2.add(jLabel5);
        //jLabel5.setBounds(140, 60, 30, 15);

        jPanel2.add(jComboBoxCruisePoint);
        jComboBoxCruisePoint.setBounds(60, 20, 60, 21);

//        jButtonRefresh.setText("刷新");
//        jPanel2.add(jButtonRefresh);
//        jButtonRefresh.setBounds(190,20,60,21);

        //jPanel2.add(jComboBoxTime);
        //jComboBoxTime.setBounds(60, 60, 60, 21);

        //jPanel2.add(jComboBoxSpeed);
        //jComboBoxSpeed.setBounds(190, 60, 70, 21);

        //jPanel2.add(jComboBoxPresetPoint);
        //jComboBoxPresetPoint.setBounds(190, 20, 70, 21);

        jPanel2.add(jTextField);
        jTextField.setBounds(110,60,180,15);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 50, 300, 90);

        jButtonAdd.setText("添加轮巡点");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonAdd);
        jButtonAdd.setBounds(30, 150, 100, 23);

        jButtonDelete.setText("删除轮巡点");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonDelete);
        jButtonDelete.setBounds(170, 150, 100, 23);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 320, 190);

        jButtonExit.setText("退出");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonExit);
        jButtonExit.setBounds(250, 210, 70, 23);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
    函数:      "添加轮巡点" 按钮单击响应函数
    函数描述:	添加轮巡点
     *************************************************/
    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
//        boolean bRet = false;
//        m_iCruiseNum = jComboBoxCruiseRoute.getSelectedIndex() + 1;
//        m_iSeqPoint = jComboBoxCruisePoint.getSelectedIndex() + 1;
//        m_iPresetNum = jComboBoxPresetPoint.getSelectedIndex() + 1;
//        m_iSeqDwell = jComboBoxTime.getSelectedIndex() + 1;
//        m_iSeqSpeed = jComboBoxSpeed.getSelectedIndex() + 1;
//
//        bRet = Set_NET_DVR_PTZCruise(m_iChanNum, HCNetSDK.FILL_PRE_SEQ, (byte) m_iCruiseNum, (byte) m_iSeqPoint, (short) m_iPresetNum);
//        if (bRet)
//        {
//            bRet = Set_NET_DVR_PTZCruise(m_iChanNum, HCNetSDK.SET_SEQ_DWELL, (byte) m_iCruiseNum, (byte) m_iSeqPoint, (short) m_iSeqDwell);
//            if (bRet)
//            {
//                bRet = Set_NET_DVR_PTZCruise(m_iChanNum, HCNetSDK.SET_SEQ_SPEED, (byte) m_iCruiseNum, (byte) m_iSeqPoint, (short) m_iSeqSpeed);
//            }
//        }
        m_iCruiseNum = jComboBoxCruiseRoute.getSelectedIndex() + 1;
        m_iSeqPoint = jComboBoxCruisePoint.getSelectedIndex() + 1;

        if(!cruiseRoute.get(m_iCruiseNum).contains(m_iSeqPoint)) {
            cruiseString.put(m_iCruiseNum, cruiseString.get(m_iCruiseNum) + " " + String.valueOf(m_iSeqPoint));
            cruiseRoute.get(m_iCruiseNum).add(m_iSeqPoint);
            writeFile(m_iCruiseNum, cruiseString.get(m_iCruiseNum));
            jTextField.setText(cruiseString.get(m_iCruiseNum));
        }
        else
            JOptionPane.showMessageDialog(this, "此轮巡点已存在！");
    }//GEN-LAST:event_jButtonAddActionPerformed

    /*************************************************
    函数:      "删除轮巡点" 按钮单击响应函数
    函数描述:	删除轮巡点
     *************************************************/
    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
//        m_iSeqPoint = jComboBoxCruisePoint.getSelectedIndex() + 1;
//        m_iPresetNum = jComboBoxPresetPoint.getSelectedIndex() + 1;
//        if (!hCNetSDK.NET_DVR_PTZCruise(m_lRealHandle, HCNetSDK.CLE_PRE_SEQ, (byte) m_iCruiseNum, (byte) m_iSeqPoint, (short) m_iPresetNum))
//        {
//            JOptionPane.showInternalMessageDialog(this, "删除失败");
//            return;
//        }
        m_iCruiseNum = jComboBoxCruiseRoute.getSelectedIndex() + 1;
        m_iSeqPoint = jComboBoxCruisePoint.getSelectedIndex() + 1;

        if(!cruiseRoute.get(m_iCruiseNum).contains(m_iSeqPoint)) {
            JOptionPane.showMessageDialog(this, "此轮巡点不存在！");
        }
        else {
            if(cruiseRoute.get(m_iCruiseNum).get(0) == m_iSeqPoint) {
                if(cruiseRoute.get(m_iCruiseNum).size() == 1){
                    String replace = "";
                    cruiseString.put(m_iCruiseNum, replace.trim());
                }else {
                    String replace = cruiseString.get(m_iCruiseNum).substring(2, cruiseString.get(m_iCruiseNum).length());
                    cruiseString.put(m_iCruiseNum, replace);
                }
            }
            else if(cruiseRoute.get(m_iCruiseNum).get(cruiseRoute.get(m_iCruiseNum).size() - 1) == m_iSeqPoint){
                String replace = cruiseString.get(m_iCruiseNum).substring(0, cruiseString.get(m_iCruiseNum).indexOf(String.valueOf(m_iSeqPoint)) - 1);
                cruiseString.put(m_iCruiseNum, replace);
            }
            else{
                //String replace = cruiseString.get(m_iCruiseNum).replace(String.valueOf(m_iSeqPoint), "");
                String replace = cruiseString.get(m_iCruiseNum).substring(0, cruiseString.get(m_iCruiseNum).indexOf(String.valueOf(m_iSeqPoint))) + cruiseString.get(m_iCruiseNum).substring(cruiseString.get(m_iCruiseNum).indexOf(String.valueOf(m_iSeqPoint)) + 2);
                cruiseString.put(m_iCruiseNum, replace);
            }

            Iterator<Integer> iterable = cruiseRoute.get(m_iCruiseNum).iterator();
            while (iterable.hasNext()) {
                int s = iterable.next();
                if (s == m_iSeqPoint) {
                    iterable.remove();
                    break;
                }
            }
            writeFile(m_iCruiseNum, cruiseString.get(m_iCruiseNum));
            jTextField.setText(cruiseString.get(m_iCruiseNum));
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    /*************************************************
    函数:      "退出" 按钮单击响应函数
    函数描述:	添加销毁窗口巡航点
     *************************************************/
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonExitActionPerformed

    /*************************************************
     函数:      写入文件
     函数描述:	将配置好的预置点写入文件
     *************************************************/
    public static void writeFile(int cruiseNum,String cruiseRoute) {
        String pathname = "";
        try {
            switch (cruiseNum){
                case 1:
                    pathname = "cruiseRoute1.txt";
                    break;
                case 2:
                    pathname = "cruiseRoute2.txt";
                    break;
                case 3:
                    pathname = "cruiseRoute3.txt";
                    break;
                case 4:
                    pathname = "cruiseRoute4.txt";
                    break;
                case 5:
                    pathname = "cruiseRoute5.txt";
                    break;
            }
            File writeName = new File(pathname);
            // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(cruiseRoute);
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*************************************************
     函数:      读取文件转化为String
     函数描述:	读取配置好的预置点文件
     *************************************************/
    public static String readFileString(int cruiseNum) {
        String pathname = "";
        switch (cruiseNum){
            case 1:
                pathname = "cruiseRoute1.txt";
                break;
            case 2:
                pathname = "cruiseRoute2.txt";
                break;
            case 3:
                pathname = "cruiseRoute3.txt";
                break;
            case 4:
                pathname = "cruiseRoute4.txt";
                break;
            case 5:
                pathname = "cruiseRoute5.txt";
                break;
        }// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        //不关闭文件会导致资源的泄露，读写文件都同理=
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                return line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*************************************************
     函数:      读取文件转化为ArrayList
     函数描述:	读取配置好的预置点文件
     *************************************************/
    public static ArrayList<Integer> readFileArrayList(int cruiseNum) throws IOException {
        ArrayList<Integer> arrayList = new ArrayList<>();
        String pathname = "";
        switch (cruiseNum){
            case 1:
                pathname = "cruiseRoute1.txt";
                break;
            case 2:
                pathname = "cruiseRoute2.txt";
                break;
            case 3:
                pathname = "cruiseRoute3.txt";
                break;
            case 4:
                pathname = "cruiseRoute4.txt";
                break;
            case 5:
                pathname = "cruiseRoute5.txt";
                break;
        }
        File file = new File(pathname);
        if(!file.exists()) {
            System.out.println("文件未找到");
            System.exit(0);
        }
        Scanner s = new Scanner(file);
        while(s.hasNextInt()){
            arrayList.add(s.nextInt());
        }
        s.close();
        return arrayList;
    }

    /*************************************************
    函数:      initDialog
    函数描述:	初始化窗口
     *************************************************/
    private void initDialog()
    {

        int i;
        //for (i = 0; i < MAX_CRUISE_SEQ; i++)
        for (i = 0; i < 5; i++)
        {
            jComboBoxCruiseRoute.addItem("轮巡路径" + (i + 1));
        }

        for (i = 0; i < MAX_CRUISE_POINT; i++)
        {
            jComboBoxCruisePoint.addItem(i + 1);
        }

        for (i = 0; i < MAX_CRUISE_PRESET; i++)
        {
            jComboBoxPresetPoint.addItem(i + 1);
        }

        for (i = 0; i < MAX_CRUISE_TIME; i++)
        {
            jComboBoxTime.addItem(i + 1);
        }
        jComboBoxTime.setSelectedIndex(3);

        for (i = 0; i < MAX_CRUISE_SPEED; i++)
        {
            jComboBoxSpeed.addItem(i + 1);
        }
        jComboBoxSpeed.setSelectedIndex(9);
    }

    private boolean Set_NET_DVR_PTZCruise(int iChanNum, int dwPTZCruiseCmd, byte byCruiseRoute, byte byCruisePoint, short wInput)
    {
        if (!hCNetSDK.NET_DVR_PTZCruise(m_lRealHandle, dwPTZCruiseCmd, byCruiseRoute, byCruisePoint, wInput))
        {
            JOptionPane.showInternalMessageDialog(this, "设置失败");
            return false;
        }
        return true;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonExit;
    private javax.swing.JComboBox jComboBoxCruisePoint;
    private javax.swing.JComboBox jComboBoxCruiseRoute;
    private javax.swing.JComboBox jComboBoxPresetPoint;
    private javax.swing.JComboBox jComboBoxSpeed;
    private javax.swing.JComboBox jComboBoxTime;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;

    private javax.swing.JTextField jTextField;
    private javax.swing.JButton jButtonRefresh;
    // End of variables declaration//GEN-END:variables
}
