package com.Ljh.tankegame;

/**
 * 坦克类
 */
public class Tank {
    private int x;  //坦克的横坐标
    private int y;  //坦克的纵坐标
    private int direct; //坦克的方向 0 1 2 3 上右下左
    private int speed = 1;   //坦克的速度，默认为1
    boolean isLive = true;   //坦克是否存活

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //坦克移动向上方法
    public void moveUp(){
        y -= speed;
    }
    //坦克移动向右方法
    public void moveRight(){
        x += speed;
    }
    //坦克移动向下方法
    public void moveDown(){
        y += speed;
    }
    //坦克移动向左方法
    public void moveLeft(){
        x -= speed;
    }
    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
