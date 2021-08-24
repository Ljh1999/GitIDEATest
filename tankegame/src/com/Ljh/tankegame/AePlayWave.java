package com.Ljh.tankegame;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * 实现背景音乐播放
 */
public class AePlayWave extends Thread{
    private String filename;

    public AePlayWave(String filename) { //构造器，指定文件
        this.filename = filename;
    }
    @Override
    public void run() {
        File file = new File(filename);
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }
        auline.start();
        int nBytesRead = 0;
        //z这是缓冲
        byte[] abData = new byte[512];

        while (nBytesRead != -1) {
            try {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0) {
                    auline.write(abData, 0, nBytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            } finally {
                auline.drain();
                auline.close();
            }
        }
    }
}
