package com.Ljh.tankegame;

/**
 * 子弹线程类
 */
public class Shot implements Runnable {
    int x;  //子弹的x坐标
    int y;  //子弹的y坐标
    int direct;  //子弹的方向
    int speed = 2;  //子弹的速度
    boolean isLive = true; //子弹是否存活

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void run() { //射击
        while (true) {
            //子弹休眠
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //子弹射击的方向，让子弹发射
            switch (direct) {
                case 0: //向上
                    y -= speed;
                    break;
                case 1: //向右
                    x += speed;
                    break;
                case 2: //向下
                    y += speed;
                    break;
                case 3: //向左
                    x -= speed;
                    break;
            }
            //System.out.println("当前x坐标" + x + " y坐标" + y);
            //判断子弹是否碰到面板边界，碰到边界即销毁子弹线程，碰到敌人坦克也销毁子弹线程
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)) {
                System.out.println("子弹线程退出");
                isLive = false;
                break;
            }
        }
    }
}
