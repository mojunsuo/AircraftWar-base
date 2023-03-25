package edu.hitsz.application;

import edu.hitsz.application.diffrentGame.EasyGame;
import edu.hitsz.application.diffrentGame.HardGame;
import edu.hitsz.application.diffrentGame.MediumGame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * 程序入口
 * @author hitsz
 * 设置了三个frame，用于初始选择界面，游戏界面，和展示成绩界面
 */
public class Main {
    public static int GraphicFlag;
    public static int MusicFlag;
    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static Object obj=new Object();
    public static Object obj2=new Object();
    public static void main(String[] args) throws InterruptedException, IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println("Hello Aircraft War");
        JFrame frame1=new JFrame("Aircraft");
        synchronized (obj)
        {
            menuPanel m=new menuPanel();
            frame1.setContentPane(m.air);
            frame1.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            frame1.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                    WINDOW_WIDTH, WINDOW_HEIGHT);
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame1.pack();
            frame1.setVisible(true);
            try {
            obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GraphicFlag=m.getLevel();
            MusicFlag=m.getFlag();
        }
        frame1.dispose();

        JFrame frame = new JFrame("Aircraft War");
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Game game = null;
        if(GraphicFlag==1)
        {
            game=new EasyGame();
        }
        if(GraphicFlag==2)
        {
            game=new MediumGame();
        }
        if(GraphicFlag==3)
        {
            game=new HardGame();
        }
        game.setGraphicsFlag(GraphicFlag);
        game.setMusicFlag(MusicFlag);
        synchronized (obj2) {
            frame.add(game);
            frame.setVisible(true);
            game.action();
            obj2.wait();
        }
        frame.dispose();

        JFrame frame2 = new JFrame("scoreDisplayPanel");
        scoreDisplayPanel s=new scoreDisplayPanel();
        frame2.setContentPane(s.MainJpanel);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.pack();
        frame2.setVisible(true);
    }

}
