package edu.hitsz.application;

import edu.hitsz.shuju.Record;
import edu.hitsz.shuju.RecordDaolmpl;

import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static edu.hitsz.application.Game.score;
import static edu.hitsz.application.menuPanel.level;


public class scoreDisplayPanel {
    JPanel MainJpanel;
    private JTable scoreTable;
    private JButton deleteButton;
    private JPanel topPanel;
    private JScrollPane tableScrallPanel;
    private JPanel ButtonPanel;
    private JLabel headLabel;
    private JLabel nandu;


    public scoreDisplayPanel() throws IOException {
        String pp=JOptionPane.showInputDialog(null,"请输入你的名字");
        if(level==1)
        {
            nandu.setText("easy难度");
        }
        if(level==2)
        {
            nandu.setText("medium难度");
        }
        if(level==3)
        {
            nandu.setText("hard难度");
        }
        RecordDaolmpl r=new RecordDaolmpl();
        r.setPathName(level);
        r.getnow();
        if(pp!=null)//有输入名字，则添加新纪录
        {
            Date date1 = new Date();
            String t = date1.toString();
            r.addRecord(pp,score+"",t);
        }
        r.sortbyScore();
        List<Record> records = r.getAll();
        r.writeToFile(records);
        String[] columnName = {"排名","姓名","成绩","时间"};
        final String[][][] tableData = {getmessage(records)};

        final DefaultTableModel[] model = {new DefaultTableModel(tableData[0], columnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        }};
        //从表格模型那里获取数据
        scoreTable.setModel(model[0]);
        tableScrallPanel.setViewportView(scoreTable);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int op=JOptionPane.showConfirmDialog(null,"确定删除吗");
                if(op==JOptionPane.YES_OPTION)
                {
                    int row = scoreTable.getSelectedRow();
                    if(row != -1){
                        records.remove(row);
                        tableData[0] =getmessage(records);
                        model[0]=new DefaultTableModel(tableData[0],columnName);
                        scoreTable.setModel(model[0]);
                        tableScrallPanel.setViewportView(scoreTable);//重新制作可视化图并排名，并向文件中重新写入records信息
                        try {
                            r.writeToFile(records);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    //从记录中读取信息
    public String[][] getmessage(List<Record>r)
    {
        int size=r.size();
        String[][]res=new String[size][];
        int num=1;
        for(Record i:r)
        {
            String[]s = new String[4];
            s[0]=num+"";
            s[1]=i.getId();
            s[2]=i.getScore();
            s[3]=i.getTime();
            res[num-1]=s;
            num++;
        }
        return res;
    }

}
