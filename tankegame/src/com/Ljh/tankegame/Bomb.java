package com.Ljh.tankegame;

/**
 * 坦克爆炸效果
 */

public class Bomb {
    int x, y; //爆炸效果的坐标
    int life = 9; //爆炸效果的生命周期
    boolean islive = true; //是否存活

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //减少爆炸效果的生命周期
    public void LifeDown() { //配合出现图片的爆炸效果
        if (life > 0) {
            life--;
        }else {
            islive = false;
        }
    }
}
