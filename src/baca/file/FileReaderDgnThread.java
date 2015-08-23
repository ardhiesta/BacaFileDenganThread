/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package baca.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author linuxluv
 */
public class FileReaderDgnThread implements Runnable {

    private AtomicBoolean keepRunning = new AtomicBoolean(true);
    private static Queue<File> queueFile = new LinkedList<File>();

    public void stopServicing() {
        keepRunning.set(false);
        synchronized (queueFile) {
            queueFile.notify();
        }
    }

    public void addQueue(File file) {
        synchronized (queueFile) {
            queueFile.offer(file);
            queueFile.notify();
        }
    }

    @Override
    public void run() {
        File file = null;
        while (keepRunning.get()) {
            synchronized (queueFile) {
                try {
                    while (queueFile.isEmpty()) {
                        queueFile.wait();
                    }
                    if (!queueFile.isEmpty()) {
                        file = queueFile.poll();
                    }
                } catch (InterruptedException e) {
                    System.out.println("--> ERROR occured: " + e.toString());
                }

                if (file != null) {
                    //baca file satu per satu di sini
                    BufferedReader br = null;

                    try {
                        String sCurrentLine;

                        br = new BufferedReader(new FileReader(file.getAbsolutePath()));

                        int jmlBaris = 0;
                        while ((sCurrentLine = br.readLine()) != null) { //dihitung jumlah barisnya
                            jmlBaris++;
                        }
                        System.out.println("file : "+file.getAbsolutePath()+", jumlah baris : "+jmlBaris); //ditampilkan hasilnya
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (br != null) {
                                br.close();
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    //selesai membaca file
                }
            }
        }
    }
}
