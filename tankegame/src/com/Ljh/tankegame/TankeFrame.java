package com.Ljh.tankegame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * 坦克大战游戏窗口
 */
@SuppressWarnings({"all"})
public class TankeFrame extends JFrame {
    public static void main(String[] args) {
        TankeFrame tankeFrame = new TankeFrame();
    }
    //初始化坦克大战窗口
    public  TankeFrame(){
        System.out.println("请选择：1：开始新的游戏 2：继续上局游戏");
        Scanner scanner = new Scanner(System.in);
        String key = scanner.next();
        //实例化面板
        Mypanel mypanel = new Mypanel(key);
        //设置窗口标题
        this.setTitle("坦克大战");
        //将mypanel面板放入Thread，并启动线程
        Thread thread = new Thread(mypanel);
        thread.start();
        //将面板放入窗口
        this.add(mypanel);
        //让窗口监听键盘事件
        this.addKeyListener(mypanel);
        //设置窗口的大小
        this.setSize(1300,950);
        //是窗口的关闭按钮生效
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口能显示
        this.setVisible(true);

        //在JFrame中增加监听关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}
