/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package baca.file;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author linuxluv
 *
 * Di source code ini terdapat contoh membaca banyak file yang terdapat di suatu
 * folder file-file tersebut kemudian akan dihitung jumlah barisnya
 */
public class Main {

    public static final ExecutorService exec = Executors.newSingleThreadExecutor();
    public static FileReaderDgnThread frdt;

    public static void main(String[] args) {
        frdt = new FileReaderDgnThread();
        exec.submit(frdt);

        //masukkan semua file yang mau dibaca di sini
        //ganti "/pink/dataset_penelitian/hotel_ratings/hotels/beijing/" (kuwi aku nganggo linux), dengan path tempat kamu menyimpan file, misal "C:\file_log"
        //nek neng kene file-file sing tak woco tak include-kan di project ini di folder contoh_file_yang_akan_dibaca, monggo dipindah ke suatu folder di laptopmu
        File directory = new File("/pink/dataset_penelitian/hotel_ratings/hotels/beijing/"); 
        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                //jika yang dibaca adalah file, masukkan ke queue
                frdt.addQueue(file);
            } else if (file.isDirectory()) {
                //jika yang dibaca adalah folder, eksekusi di sini
            }
        }
    }
}
