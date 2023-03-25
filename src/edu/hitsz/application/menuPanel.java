package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static edu.hitsz.application.Main.obj;

public class menuPanel {
    private JButton easybutton;
    private JButton mediumbutton;
    private JButton hardButton;
    private JComboBox bgmset;
    JPanel air;
    public static int level=0;
    private int flag=1;
    public menuPanel() {

        easybutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level=1;

                synchronized (obj)
                {
                    obj.notify();
                }
            }
        });
        mediumbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level=2;

                synchronized (obj)
                {
                    obj.notify();
                }
            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                level=3;

                synchronized (obj)
                {
                    obj.notify();
                }
            }
        });
        bgmset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bgmset.getSelectedItem()=="音效 关")
                {
                    flag=0;
                }
                else{
                    flag=1;
                }
            }
        });
    }
    //提供返回难度登记以及是否放音乐的标志位
    public int getLevel(){
        return level;
    }
    public int getFlag(){
        return flag;
    }

}
