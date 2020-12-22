package ClientDemo;

import com.sun.jna.NativeLong;

import javax.swing.*;

/*****************************************************************************
 *类 ：JDialogPTZCruiseTimeSettings
 *类描述 ：设置轮询时间
 ****************************************************************************/
public class JDialogPTZCruiseTimeSettings extends javax.swing.JDialog{
    public static int circleTime;
    public static int pauseTime;
    public static int picTime;

    /*************************************************
     函数:      JDialogPTZCruiseTimeSettings
     函数描述:	构造函数   Creates new form JDialogPTZCruiseTimeSettings
     *************************************************/
    public JDialogPTZCruiseTimeSettings(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("轮询时间设置");
        getContentPane().setLayout(null);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setLayout(null);

        jPanel1.add(jLabel1);
        jLabel1.setBounds(30, 20, 80, 21);
        jLabel1.setText("轮询周期");

        jPanel1.add(jLabel2);
        jLabel2.setBounds(30, 60, 120, 21);
        jLabel2.setText("预置点停留时间");

        jPanel1.add(jTextField1);
        jTextField1.setBounds(100,20,50,21);

        jPanel1.add(jTextField2);
        jTextField2.setBounds(140,60,50,21);

        jPanel1.add(jLabel3);
        jLabel3.setBounds(170,20,40,21);
        jLabel3.setText("分钟");

        jPanel1.add(jLabel4);
        jLabel4.setBounds(210,60,40,21);
        jLabel4.setText("秒钟");

        jPanel1.add(jLabel5);
        jLabel5.setBounds(30,100,100,21);
        jLabel5.setText("轮询总时间");

        jPanel1.add(jTextField3);
        jTextField3.setBounds(110,100,50,21);

        jPanel1.add(jLabel6);
        jLabel6.setBounds(180,100,40,21);
        jLabel6.setText("小时");

        jPanel1.add(jButton1);
        jButton1.setBounds(30,140,70,23);
        jButton1.setText("确定");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton2);
        jButton2.setBounds(220,140,70,23);
        jButton2.setText("退出");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        getContentPane().add(jPanel1);
        jPanel1.setBounds(10, 10, 320, 170);

        pack();
    }

    public void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if(!jTextField1.getText().isEmpty())
            circleTime = Integer.parseInt(jTextField1.getText().replaceAll("[^\\d]+", ""));
        else
            JOptionPane.showMessageDialog(this, "请输入轮询周期！");
        if(!jTextField2.getText().isEmpty())
            pauseTime = Integer.parseInt(jTextField2.getText().replaceAll("[^\\d]+", ""));
        else
            JOptionPane.showMessageDialog(this, "请输入预置点停留时间！");
        if(!jTextField3.getText().isEmpty())
            picTime = Integer.parseInt(jTextField3.getText().replaceAll("[^\\d]+", ""));
        else
            JOptionPane.showMessageDialog(this, "请输入轮询总时间！");
        if(!jTextField1.getText().isEmpty() && !jTextField2.getText().isEmpty() && !jTextField3.getText().isEmpty())
            this.dispose();
    }

    public void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
}
