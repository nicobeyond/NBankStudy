package com.example.lib;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IOTest {
    public static void main(String args[]) {
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        try {
            fis = new FileInputStream(new File("d:\\Noname1.txt"));
            bis = new BufferedInputStream(fis);
//            fis.close();
            bis.close();
            System.out.println("==" + fis.available());
            System.out.println("==" + bis.available());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {

                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
