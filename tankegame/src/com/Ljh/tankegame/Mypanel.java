package com.Ljh.tankegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
 * 坦克大战的绘图区域
 */
@SuppressWarnings({"all"})
//为了监听键盘事件，实现KeyListener接口
public class Mypanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    MyTanke tanke = null;

    //定义敌人的坦克集合（考虑到多线程的问题，所以用Vector）
    Vector<EnemyTanke> enemyTankes = new Vector<>();

    //定义一个Vector<Node> 用于存放Node,用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();

    //定义一个Vector，用于存放爆炸
    Vector<Bomb> bombs = new Vector<>();

    //初始化敌人的坦克
    int enemyTankeSize = 3; //初始化敌人坦克的数量为3

    //定义2张炸弹图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public Mypanel(String key) {
        //先判断记录的文件是否存在，不存在则不能继续游戏
        File file = new File(Recorder.getRecordFile());
        if (file.exists()) {
            nodes = Recorder.getNodesAndEnemyTanke();
        }else {
            System.out.println("没有存档信息，不能继续游戏");
            key = "1";
        }
        tanke = new MyTanke(500, 100);//初始化自己的坦克
        //设置坦克的速度
        tanke.setSpeed(3);
        //将enemyTankes设置给Recorder 的 enemyTankes
        Recorder.setEnemyTankes(enemyTankes);
        switch (key) {
            case "1": //开始新游戏
                for (int i = 0; i < enemyTankeSize; i++) {
                    //创建一个敌人的坦克
                    EnemyTanke enemyTanke = new EnemyTanke((100 * (i + 1)), 0);
                    //将 enemyTankes敌人坦克集合 设置给 enemyTanke 坦克对象
                    enemyTanke.setEnemyTankes(enemyTankes);
                    //设置敌人坦克炮筒的方向
                    enemyTanke.setDirect(2);
                    //启动让敌人坦克自由的移动线程
                    new Thread(enemyTanke).start();
                    //加入敌人坦克集合
                    enemyTankes.add(enemyTanke);
                    //给敌人坦克初始化子弹对象
                    Shot shot = new Shot(enemyTanke.getX() + 20, enemyTanke.getY() + 60, enemyTanke.getDirect());
                    enemyTanke.shots.add(shot);
                    new Thread(shot).start(); //启动子弹线程
                }
                break;
            case "2":  //继续上局游戏
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //创建一个敌人的坦克
                    EnemyTanke enemyTanke = new EnemyTanke(node.getX(), node.getY());
                    //将 enemyTankes敌人坦克集合 设置给 enemyTanke 坦克对象
                    enemyTanke.setEnemyTankes(enemyTankes);
                    //设置敌人坦克炮筒的方向
                    enemyTanke.setDirect(node.getDirect());
                    //启动让敌人坦克自由的移动线程
                    new Thread(enemyTanke).start();
                    //加入敌人坦克集合
                    enemyTankes.add(enemyTanke);
                    //给敌人坦克初始化子弹对象
                    Shot shot = new Shot(enemyTanke.getX() + 20, enemyTanke.getY() + 60, enemyTanke.getDirect());
                    enemyTanke.shots.add(shot);
                    new Thread(shot).start(); //启动子弹线程
                }
                break;
            default:
                System.out.println("输入有误！");
        }

        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom2.jpg"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom2.jpg"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/boom2.jpg"));

        //指定播放的音乐
        //new AePlayWave("src\\哪里都是你.wav").start();
    }

    //显示我方击毁地方坦克信息
    public void showInfo(Graphics g) {
        g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 20);
        g.setFont(font);
        g.drawString("您累计击毁敌方坦克", 1020, 30);
        drawTanke(1020, 60, g, 0, 1);
        g.setColor(Color.black);
        g.drawString(Recorder.getAllEnmeyTanke() + "", 1080, 100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //绘制坦克大战游戏背景板
        g.fillRect(0, 0, 1000, 750);
        showInfo(g);
        //画出自己的坦克--封装方法
        if (tanke != null && tanke.isLive) {
            drawTanke(tanke.getX(), tanke.getY(), g, tanke.getDirect(), 0);
        }
        //画出MyTanke射击的子弹（一颗子弹的情况）
        if (tanke.shot != null && tanke.shot.isLive) {
            g.fill3DRect(tanke.shot.x, tanke.shot.y, 3, 3, false);
        }

        //发射多颗子弹的情况
//        for (int i = 0; i < tanke.shots.size(); i++) {
//            //拿到一颗子弹
//            Shot shot = tanke.shots.get(i);
//            if (shot != null && shot.isLive) {
//                g.fill3DRect(shot.x, shot.y, 3, 3, false);
//            } else {  //如子弹已经被销毁，就从子弹集合中删除
//                tanke.shots.remove(shot);
//            }
//        }

        //如果bombs中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据炸弹的Lift值去画相应的图片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 50, 50, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 50, 50, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 50, 50, this);
            }
            //让炸弹的生命值减少
            bomb.LifeDown();
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }
        //画出3个敌人的坦克，遍历敌人的坦克集合Vector
        for (EnemyTanke enemyTanke : enemyTankes) {
            //判断当前坦克是否还存活，存活才画
            if (enemyTanke.isLive) {
                drawTanke(enemyTanke.getX(), enemyTanke.getY(), g, enemyTanke.getDirect(), 1);//画出敌人的坦克
                //画出3个敌人的坦克的子弹，遍历敌人的坦克子弹集合Vector
                for (int i = 0; i < enemyTanke.shots.size(); i++) {
                    Shot shot = enemyTanke.shots.get(i);
                    if (shot.isLive) { //子弹的isLive为true就绘制子弹
                        g.fill3DRect(shot.x, shot.y, 3, 3, false);
                    } else {
                        enemyTanke.shots.remove(shot);
                    }
                }
            }
        }

    }

    /**
     * @param x      坦克的左上角x坐标
     * @param y      坦克的左上角y坐标
     * @param g      画笔
     * @param direct 坦克的方向
     * @param type   坦克的类型
     */
    //定义方法，绘制坦克
    public void drawTanke(int x, int y, Graphics g, int direct, int type) {
        //判断坦克类型
        switch (type) {
            case 0:   //我们的坦克
                g.setColor(Color.pink);
                break;
            case 1:   //敌人的坦克
                g.setColor(Color.yellow);
                break;
        }

        //判断坦克方向，绘制对应形状的坦克
        //direct(0:上 1:右 2:下 3:左)
        switch (direct) {
            case 0:  //代表向上
                g.fill3DRect(x, y, 10, 60, false); //画出坦克的左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);  //画出坦克的右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);  //画出坦克的盖子
                g.fillOval(x + 10, y + 20, 20, 20);  //画出坦克的圆盖
                g.drawLine(x + 20, y + 30, x + 20, y);    //坦克的炮筒
                break;
            case 1:  //代表向右
                g.fill3DRect(x, y, 60, 10, false); //画出坦克的上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);  //画出坦克的下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);  //画出坦克的盖子
                g.fillOval(x + 20, y + 10, 20, 20);  //画出坦克的圆盖
                g.drawLine(x + 30, y + 20, x + 60, y + 20);    //坦克的炮筒
                break;
            case 2:  //代表向下
                g.fill3DRect(x, y, 10, 60, false); //画出坦克的左边轮子
                g.fill3DRect(x + 30, y, 10, 60, false);  //画出坦克的右边轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false);  //画出坦克的盖子
                g.fillOval(x + 10, y + 20, 20, 20);  //画出坦克的圆盖
                g.drawLine(x + 20, y + 30, x + 20, y + 60);    //坦克的炮筒
                break;
            case 3:  //代表向左
                g.fill3DRect(x, y, 60, 10, false); //画出坦克的上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);  //画出坦克的下边轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false);  //画出坦克的盖子
                g.fillOval(x + 20, y + 10, 20, 20);  //画出坦克的圆盖
                g.drawLine(x + 30, y + 20, x, y + 20);    //坦克的炮筒
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }

    //监听键盘输入字符
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //当某个键被按下时
    @Override
    public void keyPressed(KeyEvent e) {
        //按下了w键，改变坦克的方向，和控制坦克向上移动
        if (e.getKeyCode() == KeyEvent.VK_W) {
            //设置坦克的方向向右
            tanke.setDirect(0);
            //使坦克向上移动
            if (tanke.getY() > 0) {
                tanke.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            //设置坦克的方向向右
            tanke.setDirect(1);
            //使坦克向右移动
            if (tanke.getX() + 60 < 1000) {
                tanke.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            //设置坦克的方向向下
            tanke.setDirect(2);
            //使坦克向下移动
            if (tanke.getY() + 60 < 750) {
                tanke.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            //设置坦克的方向向左
            tanke.setDirect(3);
            //使坦克向左移动
            if (tanke.getX() > 0) {
                tanke.moveLeft();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            //调用坦克发射子弹方法
            //判断子弹是否为空或者是否已经被摧毁，满足才能再次发射子弹
            if (tanke.shot == null || !tanke.shot.isLive) {  //发射一颗子弹的情况
                tanke.ShotEnemyTanke();
            }
            //tanke.ShotEnemyTanke(); //发射多颗子弹的情况
        }
        //让面板重绘
        this.repaint();
    }

    //当某个键松开时
    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每隔100毫秒，就重绘区域，刷新绘图区域，让子弹移动
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //我方坦克是否击中敌人
            hitEnemyTanke();
            //敌人坦克是否击中我们
            hitMyTank();
            this.repaint();
        }
    }

    //编写方法，判断敌人坦克是否击中我的坦克
    public void hitMyTank() {
        //遍历所有敌人坦克
        for (int i = 0; i < enemyTankes.size(); i++) {
            //取出敌人坦克
            EnemyTanke enemyTanke = enemyTankes.get(i);
            //遍历敌人坦克的所有子弹
            for (int j = 0; j < enemyTanke.shots.size(); j++) {
                //取出子弹
                Shot shot = enemyTanke.shots.get(j);
                //我方坦克是否存活，子弹是否存活
                if (tanke.isLive && shot.isLive) {
                    hitTank(shot, tanke);
                }
            }
        }
    }

    //编写方法，判断我方的子弹是否击中敌方坦克
    //什么时候判断子弹是否击中敌方坦克（一颗子弹的情况）
    public void hitTank(Shot shot, Tank tank) {
        //判断子弹击中坦克
        switch (tank.getDirect()) {
            case 0://坦克向上
            case 2://坦克向下
                if (shot.x > tank.getX() && shot.x < tank.getX() + 40
                        && shot.y > tank.getY() && shot.y < tank.getY() + 60) { //如果子弹进入了坦克坐标的区域，就代表子弹击中了坦克
                    shot.isLive = false;
                    tank.isLive = false;
                    //当子弹击中敌人坦克后，就将该坦克对象从Vector中删掉
                    enemyTankes.remove(tank);
                    //因为传进来的可以是自己的坦克，也可以是敌人的坦克
                    // 如果传进来的坦克对象是一个敌方坦克，就对调用addAllEnmeyTanke() 方法，让击败敌方坦克数变量allEnmeyTanke++
                    if (tank instanceof EnemyTanke) {
                        Recorder.addAllEnmeyTanke();
                    }
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1://坦克向右
            case 3://坦克向左
                if (shot.x > tank.getX() && shot.x < tank.getX() + 60
                        && shot.y > tank.getY() && shot.y < tank.getY() + 40) {
                    shot.isLive = false;
                    tank.isLive = false;
                    //当子弹击中敌人坦克后，就将该坦克对象从Vector中删掉
                    enemyTankes.remove(tank);
                    //因为传进来的可以是自己的坦克，也可以是敌人的坦克
                    //如果传进来的坦克对象是一个敌方坦克，就对调用addAllEnmeyTanke() 方法，让击败敌方坦克数变量allEnmeyTanke++
                    if (tank instanceof EnemyTanke) {
                        Recorder.addAllEnmeyTanke();
                    }
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }


    //如果我们的坦克可以发射多颗子弹，在判断我方子弹是否击中敌方坦克时，就需要遍历集合，把所有子弹取出和所有敌人的坦克，进行判断
    public void hitEnemyTanke() {
        //判断我们的子弹是否击中了敌方坦克（我方坦克只能发射一颗子弹的情况）
        if (tanke.shot != null && tanke.shot.isLive) { //当我的子弹还存活就判断
            //遍历所有敌人的坦克
            for (int i = 0; i < enemyTankes.size(); i++) {
                EnemyTanke enemyTanke = enemyTankes.get(i);
                hitTank(tanke.shot, enemyTanke);
            }
        }
        //我方坦克可以发射多颗子弹的情况
//        for (int j = 0; j < tanke.shots.size(); j++) {
//            Shot shot = tanke.shots.get(j);
//            if (shot != null && shot.isLive) { //当我的子弹还存活就判断
//                //遍历所有敌人的坦克
//                for (int i = 0; i < enemyTankes.size(); i++) {
//                    EnemyTanke enemyTanke = enemyTankes.get(i);
//                    hitTank(shot, enemyTanke);
//                }
//            }
//        }
    }
}
