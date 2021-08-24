package com.Ljh.tankegame;

import java.io.*;
import java.util.Vector;

/**
 * 记录信息，击垮坦克数等等
 */
@SuppressWarnings({"all"})
public class Recorder {
    private static int allEnmeyTanke = 0; //记录我方坦克击败敌人坦克数，初始化为0
    private static BufferedReader bufferedReader = null;  //处理输入流
    private static BufferedWriter bufferedWriter = null;  //处理输出流
    private static String recordFile = "src\\myRecord.txt"; //保存文件路径
    private static Vector<EnemyTanke> enemyTankes = null; //定义一个Vector，指向Mypanel中敌人坦克的Vector，通过Set方法得到敌人坦克的Vector
    private static Vector<Node> nodes = new Vector<>(); //定义一个Node的Vector，用于保存敌人的信息

    public static String getRecordFile() {
        return recordFile;
    }

    //得到敌人坦克的Vector
    public static void setEnemyTankes(Vector<EnemyTanke> enemyTankes) {
        Recorder.enemyTankes = enemyTankes;
    }

    public static int getAllEnmeyTanke() {
        return allEnmeyTanke;
    }

    public static void setAllEnmeyTanke(int allEnmeyTanke) {
        Recorder.allEnmeyTanke = allEnmeyTanke;
    }

    //当我方击败一个敌方坦克，allEnmeyTanke就应该++
    public static void addAllEnmeyTanke() {
        Recorder.allEnmeyTanke++;
    }

    //当游戏退出时，我们将allEnmeyTanke保存到recordFile
    public static void keepRecord() {
        try {
            //将击败敌人坦克数写入文件
            bufferedWriter = new BufferedWriter(new FileWriter(recordFile));
            bufferedWriter.write(allEnmeyTanke + "\r\n");

            //遍历敌人坦克的集合，将还存活的坦克的坐标方向保存到文件
            for (int i = 0; i < enemyTankes.size(); i++) {
                EnemyTanke enemyTanke = enemyTankes.get(i);
                if (enemyTanke.isLive) {
                    String tankerecord = enemyTanke.getX() + " " + enemyTanke.getY() + " " + enemyTanke.getDirect() + "\r\n";
                    bufferedWriter.write(tankerecord);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //用于读取recordFile，恢复坦克信息
    public static Vector<Node> getNodesAndEnemyTanke() {
        try {
            bufferedReader = new BufferedReader(new FileReader(recordFile));
            allEnmeyTanke = Integer.parseInt(bufferedReader.readLine());
            //循环读取文件，生成nodes集合
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] s = line.split(" ");
                Node node = new Node(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }
}
