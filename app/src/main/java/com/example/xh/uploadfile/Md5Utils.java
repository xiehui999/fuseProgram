package com.example.xh.uploadfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xiehui on 2016/10/13.
 */
public class Md5Utils {

    /**
     * 获取文件的MD5值
     *
     * @param file 文件路径
     * @return md5
     */
    public static String getFileMd5(File file) {
        MessageDigest messageDigest;
        //MappedByteBuffer byteBuffer = null;
        FileInputStream fis = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            if (file == null) {
                return "";
            }
            if (!file.exists()) {
                return "";
            }
            int len = 0;
            fis = new FileInputStream(file);
            //普通流读取方式
            byte[] buffer = new byte[1024 * 1024 * 10];
            while ((len = fis.read(buffer)) > 0) {
                //该对象通过使用 update（）方法处理数据
                messageDigest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            String md5 = bigInt.toString(16);
            while (md5.length() < 32) {
                md5 = "0" + md5;
            }
            return md5;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }
    /**
     * FileChannel 获取文件的MD5值
     *
     * @param file 文件路径
     * @return md5
     */
    public static String getFileMd52(File file) {
        MessageDigest messageDigest;
        FileInputStream fis = null;
        FileChannel ch=null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            if (file == null) {
                return "";
            }
            if (!file.exists()) {
                return "";
            }
            fis = new FileInputStream(file);
            ch = fis.getChannel();
            int size = 1024 * 1024 * 10;
            long part = file.length() / size + (file.length() % size > 0 ? 1 : 0);
            System.err.println("文件分片数" + part);
            for (int j = 0; j < part; j++) {
                MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, j * size, j == part - 1 ? file.length() : (j + 1) * size);
                messageDigest.update(byteBuffer);
                byteBuffer.clear();
            }
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            String md5 = bigInt.toString(16);
            while (md5.length() < 32) {
                md5 = "0" + md5;
            }
            return md5;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
                if (ch!=null){
                    ch.close();
                    ch=null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }
    /**
     * RandomAccessFile 获取文件的MD5值
     *
     * @param file 文件路径
     * @return md5
     */
    public static String getFileMd53(File file) {
        MessageDigest messageDigest;
        RandomAccessFile randomAccessFile = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            if (file == null) {
                return "";
            }
            if (!file.exists()) {
                return "";
            }
            randomAccessFile=new RandomAccessFile(file,"r");
            byte[] bytes=new byte[1024*1024*10];
            int len=0;
            while ((len=randomAccessFile.read(bytes))!=-1){
                messageDigest.update(bytes,0, len);
            }
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            String md5 = bigInt.toString(16);
            while (md5.length() < 32) {
                md5 = "0" + md5;
            }
            return md5;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                    randomAccessFile = null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }
    /**
     * 获取文件的MD5值
     *
     * @param file 文件路径
     * @return md5
     */
    public static String getFileMd51(File file) {
        MessageDigest messageDigest;
        //MappedByteBuffer byteBuffer = null;
        FileInputStream is = null;
        RandomAccessFile randomAccessFile;
        try {

            messageDigest = MessageDigest.getInstance("MD5");
            if (file == null) {
                return "";
            }
            if (!file.exists()) {
                return "";
            }
            randomAccessFile = new RandomAccessFile(file, "r");
            int i = 0;
            FileChannel fileChannel = randomAccessFile.getChannel();
            int size = 1024 * 1024 * 10;
            long part = file.length() / size + (file.length() % size > 0 ? 1 : 0);
            System.err.println("文件分片数" + part);
            for (int j = 0; j < part; j++) {
                System.err.println(i++);
                MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, j * size, j == part - 1 ? file.length() : (j + 1) * size);
                messageDigest.update(byteBuffer);
                byteBuffer.clear();
            }
            fileChannel.close();
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            String md5 = bigInt.toString(16);
            while (md5.length() < 32) {
                md5 = "0" + md5;
            }
            return md5;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }

}
