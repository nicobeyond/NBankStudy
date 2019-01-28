package com.example.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class MyClass extends Thread {

    public static void main(String args[]) throws InterruptedException {
//        Object str = null;
//        System.out.println("===" + String.valueOf());


        String LOG_FORMAT = "%1$s%n%2$s";
        System.out.println(String.format(LOG_FORMAT, "aa", "bb"));

//        String str = MyClass.das();
//        System.out.println("==" + (str == "222"));
//        System.out.println("==" + (str.equals("222")));

//        File file1 = new File("D:\\123");
//        file1.delete();
        if (1 == 1) return;
//
//        MyClass.createFolder("111/222/333", "333/444/555/666");
//        if (1 == 1) return;

        File file = new File("D:\\123\\123.txt");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, false);
            fos.write("dl;aksl;d".getBytes());
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

//        file.mkdirs();
        if (1 == 1) return;

        int[] bArray = new int[]{3, 6, 6, 34, 23, 4, 4};

        // Copy all the REAL bytes, not the "first"
        int[] ret = new int[bArray.length - 1];
//        System.arraycopy(bArray, 1, ret, 0, ret.length);

        for (int i = 0; i < ret.length; i++) {
            ret[i] = bArray[i + 1];
        }

        System.out.println("==" + Arrays.toString(ret));


//        deleteNotInFileList("D:\\222", new Object[]{"asd", "qwe"});

//        ObjectClass obj = new ObjectClass();
//
//        Thread t2 = new ChangeValue(obj);
//        t2.start();
//
//        Thread t1 = new AlwaysRun(obj);
//        t1.start();
//        //6887
//        //7087
//
//        sleep(200);
//        t1.stop();
    }

    public static String das() {
        return "222";
    }

    public static void deleteNotInFileList(String path, Object[] names) {
        if (null == names) {
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] fileList = file.listFiles();
        int len = fileList.length;
        int len2 = names.length;
        for (int i = 0; i < len; i++) {
            File file2 = fileList[i];
            String nameString = file2.getName();
            if (file2.isFile()
                    && (nameString.endsWith(".png")
                    || nameString.endsWith(".jpg")
                    || nameString.endsWith(".gif")
                    || nameString.endsWith(".jpeg")
                    || nameString.endsWith(".bmp")
                    || nameString.endsWith(".wbmp")
                    || nameString.endsWith(".ico")
                    || nameString.endsWith(".jpe"))) {
                boolean del = true;
//                for (int j = 0; j < len2; j++) {
//                    if (StringUtil.isBlank(String.valueOf(names[j]))
//                            || names[j].equals(nameString)) {
//                        del = false;
//                        break;
//                    }
//                }
                if (del) {
                    file2.delete();
                }
            }
        }
    }

    /**
     * 循环创建文件夹
     *
     * @param rootPath
     * @param folderPath
     * @return
     */
    public static String createFolder(String rootPath, String folderPath) {
        StringBuffer tmpRootPath = new StringBuffer();
        if (rootPath.endsWith("/")) {
            tmpRootPath.append(rootPath.substring(0, rootPath.length() - 1));
        } else {
            tmpRootPath.append(rootPath);
        }
        folderPath = folderPath.replaceAll("\\\\", "/");
        if (!folderPath.startsWith("/")) {
            folderPath = "/" + folderPath;
        }
        if (!folderPath.endsWith("/")) {
            folderPath = folderPath + "/";
        }
        String[] folders = folderPath.split("/");
        for (int i = 0; i < folders.length; i++) {
            tmpRootPath.append("/" + folders[i]);
            System.out.println("===" + tmpRootPath.toString());
        }
        return folderPath;
    }
}

class AlwaysRun extends Thread {

    ObjectClass obj;

    public AlwaysRun(ObjectClass obj) {
        // TODO Auto-generated constructor stub
        this.obj = obj;
    }

    @Override
    public void run() {
        System.out.println("===" + System.currentTimeMillis());
        obj.Loop();
    }
}

class ChangeValue extends Thread {

    ObjectClass obj;

    ChangeValue(ObjectClass obj) {
        this.obj = obj;
    }

    @Override
    public void run() {

        System.out.println("Thread2");
        ObjClass2 obj2 = obj.getObj();

        System.out.println("Thread2 getObj+" + System.currentTimeMillis());
        try {
            sleep(100);
        } catch (InterruptedException e) {
            System.out.println("Error!");
        }


//        synchronized (obj2) {
        obj2.str = "aaa";
//        }
        System.out.println("Thread2 Finish!");
    }
}

class ObjectClass extends Thread {

    private ObjClass2 obj;
//    public Object lockTable = new Object();

    public ObjectClass() {
        // TODO Auto-generated constructor stub
        obj = new ObjClass2();
    }

    public void setObj(ObjClass2 obj) {
        synchronized (obj) {
            this.obj = obj;
        }
    }

    public ObjClass2 getObj() {
        synchronized (obj) {
            return this.obj;      //出问题处！！
        }
    }

    public void Loop() {
        synchronized (obj) {
            while (true) {
                System.out.println(obj.str);
            }
        }
    }
}

class ObjClass2 {
    public String str = "ddddddd";
}