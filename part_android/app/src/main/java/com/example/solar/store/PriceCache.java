package com.example.solar.store;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PriceCache {
    private Context context;

    public PriceCache(Context co) {
        context = co;
    }

    public File getCacheDir(Context context) {
        File cacheDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "cachefolder");
            if (!cacheDir.isDirectory()) {
                cacheDir.mkdirs();
            }
        }
        if (!cacheDir.isDirectory()) {
            cacheDir = context.getCacheDir();
        }
        return cacheDir;
    }

    public void write(String obj) {
        try{
            Write(obj);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public String read() {
        String readText = null;
        try {
            readText = Read();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return readText;
    }

    private void Write(String obj) throws IOException {
        File cacheDir = getCacheDir(context);
        File cacheFile = new File(cacheDir, "Cache.txt");
        if (!cacheFile.exists()) cacheFile.createNewFile();
        FileWriter fileWriter = new FileWriter(cacheFile);
        fileWriter.write(obj);
        fileWriter.flush();
        fileWriter.close();
    }

    private String Read() throws IOException {
        File cacheDir = getCacheDir(context);
        File cacheFile = new File(cacheDir, "Cache.txt");
        if (!cacheFile.exists()) throw new IOException();
        FileInputStream inputStream = new FileInputStream(cacheFile);
        Scanner s = new Scanner(inputStream);
        String text = "";
        while (s.hasNext()) {
            text += s.nextLine();
        }
        inputStream.close();
        return text;
    }

}