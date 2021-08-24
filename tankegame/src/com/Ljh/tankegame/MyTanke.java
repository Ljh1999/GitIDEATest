package com.Ljh.tankegame;

import java.util.Vector;

/**
 * 我的坦克
 */
@SuppressWarnings({"all"})
public class MyTanke extends Tank {
    //定义子弹线程
    public Shot shot = null;

    @Override
    public void moveRight() {
        super.moveRight();
    }
//定义子弹集合，用于发射多颗子弹
    //Vector<Shot> shots = new Vector<>();

    public MyTanke(int x, int y) {
        super(x, y);
    }

    //射击
    public void ShotEnemyTanke() {
//        if(shots.size() == 5){ //控制面板上只能同时出现5颗子弹
//            return;
//        }
        //创建Shot对象，根据当前MyTank对象的位置和方向来创建Shot
        switch (getDirect()) { //得到我的坦克对象方向
            case 0: //向上
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1: //向右
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2: //向下
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3: //向左
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }
        //添加子弹对象到集合
        //shots.add(shot);
        Thread thread = new Thread(shot);
        thread.start();
    }
}
