package com.Ljh.tankegame;

import java.util.Random;
import java.util.Vector;

/**
 * 敌人的坦克
 */
@SuppressWarnings({"all"})
public class EnemyTanke extends Tank implements Runnable {
    boolean isLive = true;  //坦克是否存活
    //定义敌人坦克的子弹集合
    Vector<Shot> shots = new Vector<>();

    //增加成员，EnemyTank 得到敌人坦克的Vector
    Vector<EnemyTanke> enemyTankes = new Vector<>();

    //这里我们提供一个set方法，可以将Mypanel中的Vector<EnemyTanke> enemyTankes = new Vector<>();
    // 设置到EnemyTanke的成员enemyTankes
    public void setEnemyTankes(Vector<EnemyTanke> enemyTankes) {
        this.enemyTankes = enemyTankes;
    }

    //定义一个方法，用于判断当前的这个敌人坦克，是否和Mypanel中enemyTankes集合中的其他坦克重叠或碰撞
    public boolean isTouchEnemyTanke() {
        //判断当前敌人坦克（this）方向
        switch (this.getDirect()) {
            case 0: //当前坦克向上
                //让当前敌人坦克和其他所有的敌人坦克比较
                for (int i = 0; i < enemyTankes.size(); i++) {
                    //循环从Vector中取出敌人的坦克
                    EnemyTanke enemyTanke = enemyTankes.get(i);
                    //不和自己比较，和自己比较一定是碰撞的
                    if (enemyTanke != this) {
                        // 如果敌人坦克是上/下
                        // 如果敌人坦克是上/下 x坐标的范围[enemyTanke.getX(),enemyTanke.getX() + 40]
                        //                    y坐标的范围[enemyTanke.getY(),enemyTanke.getY() + 60]
                        if (enemyTanke.getDirect() == 0 || enemyTanke.getDirect() == 2) {
                            //当前坦克 左上角的坐标[this.getX(),this.getY()]
                            if (this.getX() >= enemyTanke.getX()
                                    && this.getX() <= enemyTanke.getX() + 40
                                    && this.getY() >= enemyTanke.getY()
                                    && this.getY() <= enemyTanke.getY() + 60) {
                                return true;
                            }
                            //当前坦克 右上角的坐标[this.getX() + 40,this.getY()]
                            if (this.getX() + 40 >= enemyTanke.getX()
                                    && this.getX() + 40 <= enemyTanke.getX() + 40
                                    && this.getY() >= enemyTanke.getY()
                                    && this.getY() <= enemyTanke.getY() + 60) {
                                return true;
                            }
                        }
                        // 如果敌人坦克是右/左
                        // 如果敌人坦克是右/左 x坐标的范围[enemyTanke.getX(),enemyTanke.getX() + 60]
                        //                    y坐标的范围[enemyTanke.getY(),enemyTanke.getY() + 40]
                        if (enemyTanke.getDirect() == 1 || enemyTanke.getDirect() == 3) {
                            //当前坦克 左上角的坐标[this.getX(),this.getY()]
                            if (this.getX() >= enemyTanke.getX()
                                    && this.getX() <= enemyTanke.getX() + 60
                                    && this.getY() >= enemyTanke.getY()
                                    && this.getY() <= enemyTanke.getY() + 40) {
                                return true;
                            }
                            //当前坦克 右上角的坐标[this.getX() + 40,this.getY()]
                            if (this.getX() + 40 >= enemyTanke.getX()
                                    && this.getX() + 40 <= enemyTanke.getX() + 60
                                    && this.getY() >= enemyTanke.getY()
                                    && this.getY() <= enemyTanke.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1: //当前坦克向右
                //让当前敌人坦克和其他所有的敌人坦克比较
                for (int i = 0; i < enemyTankes.size(); i++) {
                    //循环从Vector中取出敌人的坦克
                    EnemyTanke enemyTanke = enemyTankes.get(i);
                    //不和自己比较，和自己比较一定是碰撞的
                    if (enemyTanke != this) {
                        // 如果敌人坦克是上/下
                        // 如果敌人坦克是上/下 x坐标的范围[enemyTanke.getX(),enemyTanke.getX() + 40]
                        //                    y坐标的范围[enemyTanke.getY(),enemyTanke.getY() + 60]
                        if (enemyTanke.getDirect() == 0 || enemyTanke.getDirect() == 2) {
                            //当前坦克 右上角的坐标[this.getX() + 60,this.getY()]
                            if (this.getX() + 60 >= enemyTanke.getX()
                                    && this.getX() + 60 <= enemyTanke.getX() + 40
                                    && this.getY() >= enemyTanke.getY()
                                    && this.getY() <= enemyTanke.getY() + 60) {
                                return true;
                            }
                            //当前坦克 右下角的坐标[this.getX() + 60,this.getY() + 40]
                            if (this.getX() + 60 >= enemyTanke.getX()
                                    && this.getX() + 60 <= enemyTanke.getX() + 40
                                    && this.getY() + 40 >= enemyTanke.getY()
                                    && this.getY() + 40 <= enemyTanke.getY() + 60) {
                                return true;
                            }
                        }
                        // 如果敌人坦克是右/左
                        // 如果敌人坦克是右/左 x坐标的范围[enemyTanke.getX(),enemyTanke.getX() + 60]
                        //                    y坐标的范围[enemyTanke.getY(),enemyTanke.getY() + 40]
                        if (enemyTanke.getDirect() == 1 || enemyTanke.getDirect() == 3) {
                            //当前坦克 右上角的坐标[this.getX() + 60,this.getY()]
                            if (this.getX() + 60 >= enemyTanke.getX()
                                    && this.getX() + 60 <= enemyTanke.getX() + 60
                                    && this.getY() >= enemyTanke.getY()
                                    && this.getY() <= enemyTanke.getY() + 40) {
                                return true;
                            }
                            //当前坦克 右下角的坐标[this.getX() + 60,this.getY() + 40]
                            if (this.getX() + 60 >= enemyTanke.getX()
                                    && this.getX() + 60 <= enemyTanke.getX() + 60
                                    && this.getY() + 40 >= enemyTanke.getY()
                                    && this.getY() + 40 <= enemyTanke.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2: //当前坦克向下
                //让当前敌人坦克和其他所有的敌人坦克比较
                for (int i = 0; i < enemyTankes.size(); i++) {
                    //循环从Vector中取出敌人的坦克
                    EnemyTanke enemyTanke = enemyTankes.get(i);
                    //不和自己比较，和自己比较一定是碰撞的
                    if (enemyTanke != this) {
                        // 如果敌人坦克是上/下
                        // 如果敌人坦克是上/下 x坐标的范围[enemyTanke.getX(),enemyTanke.getX() + 40]
                        //                    y坐标的范围[enemyTanke.getY(),enemyTanke.getY() + 60]
                        if (enemyTanke.getDirect() == 0 || enemyTanke.getDirect() == 2) {
                            //当前坦克 左下角的坐标[this.getX(),this.getY() + 60]
                            if (this.getX() >= enemyTanke.getX()
                                    && this.getX() <= enemyTanke.getX() + 40
                                    && this.getY() + 60 >= enemyTanke.getY()
                                    && this.getY() + 60 <= enemyTanke.getY() + 60) {
                                return true;
                            }
                            //当前坦克 右下角的坐标[this.getX() + 40,this.getY() + 60]
                            if (this.getX() + 40 >= enemyTanke.getX()
                                    && this.getX() + 40 <= enemyTanke.getX() + 40
                                    && this.getY() + 60 >= enemyTanke.getY()
                                    && this.getY() + 60 <= enemyTanke.getY() + 60) {
                                return true;
                            }
                        }
                        // 如果敌人坦克是右/左
                        // 如果敌人坦克是右/左 x坐标的范围[enemyTanke.getX(),enemyTanke.getX() + 60]
                        //                    y坐标的范围[enemyTanke.getY(),enemyTanke.getY() + 40]
                        if (enemyTanke.getDirect() == 1 || enemyTanke.getDirect() == 3) {
                            //当前坦克 左下角的坐标[this.getX(),this.getY() + 60]
                            if (this.getX() >= enemyTanke.getX()
                                    && this.getX() <= enemyTanke.getX() + 60
                                    && this.getY() + 60 >= enemyTanke.getY()
                                    && this.getY() + 60 <= enemyTanke.getY() + 40) {
                                return true;
                            }
                            //当前坦克 右下角的坐标[this.getX() + 40,this.getY() + 60]
                            if (this.getX() + 40 >= enemyTanke.getX()
                                    && this.getX() + 40 <= enemyTanke.getX() + 60
                                    && this.getY() + 60 >= enemyTanke.getY()
                                    && this.getY() + 60 <= enemyTanke.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3: //当前坦克向左
                //让当前敌人坦克和其他所有的敌人坦克比较
                for (int i = 0; i < enemyTankes.size(); i++) {
                    //循环从Vector中取出敌人的坦克
                    EnemyTanke enemyTanke = enemyTankes.get(i);
                    //不和自己比较，和自己比较一定是碰撞的
                    if (enemyTanke != this) {
                        // 如果敌人坦克是上/下
                        // 如果敌人坦克是上/下 x坐标的范围[enemyTanke.getX(),enemyTanke.getX() + 40]
                        //                    y坐标的范围[enemyTanke.getY(),enemyTanke.getY() + 60]
                        if (enemyTanke.getDirect() == 0 || enemyTanke.getDirect() == 2) {
                            //当前坦克 左上角的坐标[this.getX(),this.getY()]
                            if (this.getX() >= enemyTanke.getX()
                                    && this.getX() <= enemyTanke.getX() + 40
                                    && this.getY() >= enemyTanke.getY()
                                    && this.getY() <= enemyTanke.getY() + 60) {
                                return true;
                            }
                            //当前坦克 左下角的坐标[this.getX(),this.getY() + 40]
                            if (this.getX() >= enemyTanke.getX()
                                    && this.getX() <= enemyTanke.getX() + 40
                                    && this.getY() + 40 >= enemyTanke.getY()
                                    && this.getY() + 40 <= enemyTanke.getY() + 60) {
                                return true;
                            }
                        }
                        // 如果敌人坦克是右/左
                        // 如果敌人坦克是右/左 x坐标的范围[enemyTanke.getX(),enemyTanke.getX() + 60]
                        //                    y坐标的范围[enemyTanke.getY(),enemyTanke.getY() + 40]
                        if (enemyTanke.getDirect() == 1 || enemyTanke.getDirect() == 3) {
                            //当前坦克 左上角的坐标[this.getX(),this.getY()]
                            if (this.getX() >= enemyTanke.getX()
                                    && this.getX() <= enemyTanke.getX() + 60
                                    && this.getY() >= enemyTanke.getY()
                                    && this.getY() <= enemyTanke.getY() + 40) {
                                return true;
                            }
                            //当前坦克 左下角的坐标[this.getX(),this.getY() + 40]
                            if (this.getX() >= enemyTanke.getX()
                                    && this.getX() <= enemyTanke.getX() + 60
                                    && this.getY() + 40 >= enemyTanke.getY()
                                    && this.getY() + 40 <= enemyTanke.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    public EnemyTanke(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (true) {

            Shot s = null;
            //如果子弹集合的数量为0，就创建一个子弹，放到子弹集合，并启动线程
            if (isLive && shots.size() < 1) {
                switch (getDirect()) {
                    case 0:
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        s = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:
                        s = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:
                        s = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();
            }

            switch (getDirect()) {//敌人坦克随机移动
                case 0:
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0 && !isTouchEnemyTanke()) {
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000 && !isTouchEnemyTanke()) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750 && !isTouchEnemyTanke()) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !isTouchEnemyTanke()) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            //随机改变敌人坦克方向
            Random random = new Random();
            int i = random.nextInt(4);
            setDirect(i);

            if (!isLive) {
                break; //退出线程
            }


        }
    }
}
