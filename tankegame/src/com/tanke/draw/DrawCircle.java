package com.tanke.draw;

import javax.swing.*;
import java.awt.*;

/**
 *
 * 演示如何在面板上画出图形
 */
public class DrawCircle extends  JFrame{
    public static void main(String[] args) {
       new DrawCircle();
    }
    public DrawCircle(){
        MyPanel myPanel = new MyPanel();
        this.add(myPanel);
        this.setTitle("坦克大战");
        this.setSize(400,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
class MyPanel extends JPanel{
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //绘制坦克
        g.setColor(Color.pink);
        g.fillRect(10,10,12,70);
        g.fillRect(50,10,12,70);
        g.setColor(Color.pink);
        g.fillRect(20,20,40,50);
        g.setColor(Color.CYAN);
        g.fillOval(25,35,20,20);
        g.setColor(Color.black);
        g.drawLine(35,10,35,35);
        //画圆形
        //g.drawOval(10,10,100,100);
        //画直线
        //g.drawLine(10,10,100,10);
        //画矩形边框
        //g.drawRect(10,10,100,100);
        //填充矩形
        //g.setColor(Color.pink);
        //g.fillRect(10,10,100,100);
        //画图片
        //Image image = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/崖上的波妞4k高清壁纸_彼岸图网.jpg"));
        //g.drawImage(image,10,10,400,216,this);
        //画字体
        //g.setColor(Color.CYAN);
        //g.setFont(new Font("微软雅黑",Font.BOLD,12));
        //g.drawString("我还喜欢你",100,100);
    }
}
