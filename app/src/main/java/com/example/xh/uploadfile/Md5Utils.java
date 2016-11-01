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
     * @param file
     *            文件路径
     * @return md5
     */
    public static String getFileMd5(File file) {
        MessageDigest messageDigest;
        //MappedByteBuffer byteBuffer = null;
        FileInputStream is = null;
        RandomAccessFile randomAccessFile;
        byte[] resultByteArray = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            if (file == null) {
                return "";
            }
            if (!file.exists()) {
                return "";
            }
            int i=0;
            int len = 0;
            is = new FileInputStream(file);
            //普通流读取方式
            byte[] buffer = new byte[1024 * 1024 * 10];
            while ((len = is.read(buffer)) >0) {
                //该对象通过使用 update（）方法处理数据
                System.err.println(i++);
                messageDigest.update(buffer, 0, len);
            }

/*            FileChannel fileChannel = is.getChannel();
            int size=1024 * 1024 * 10;
            long part=file.length()/size+(file.length()%size>0 ?1:0);
            System.err.println("文件分片数"+part);
            for (int j = 0; j < part; j++) {
                System.err.println(i++);
                MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, j*size, j==part-1?file.length():(j+1)*size);
                messageDigest.update(byteBuffer);
            }
            fileChannel.close();*/
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            String md5 = bigInt.toString(16);
            while(md5.length() < 32){
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
        }finally {
            try {
                if (is!=null) {
                    is.close();
                    is=null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (resultByteArray == null) {
            return "";
        }
        return resultByteArray.toString();
    }
    /**
     * 获取文件的MD5值
     *
     * @param file
     *            文件路径
     * @return md5
     */
    public static String getFileMd51(File file) {
        MessageDigest messageDigest;
        //MappedByteBuffer byteBuffer = null;
        FileInputStream is = null;
        RandomAccessFile randomAccessFile;
        byte[] resultByteArray = null;
        try {

            messageDigest = MessageDigest.getInstance("MD5");
            if (file == null) {
                return "";
            }
            if (!file.exists()) {
                return "";
            }
            randomAccessFile=new RandomAccessFile(file,"r");
            int i=0;
            int len = 0;
            FileChannel fileChannel = randomAccessFile.getChannel();
            int size=1024 * 1024 * 10;
            long part=file.length()/size+(file.length()%size>0 ?1:0);
            System.err.println("文件分片数"+part);
            for (int j = 0; j < part; j++) {
                System.err.println(i++);
                MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, j*size, j==part-1?file.length():(j+1)*size);
                messageDigest.update(byteBuffer);
                byteBuffer.clear();
            }
            fileChannel.close();
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            String md5 = bigInt.toString(16);
            while(md5.length() < 32){
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
        }finally {
            try {
                if (is!=null) {
                    is.close();
                    is=null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (resultByteArray == null) {
            return "";
        }
        return resultByteArray.toString();
    }
    private static String byteArrayToHex(byte[] resultByteArray) {
        // TODO Auto-generated method stub
        BigInteger bigInt = new BigInteger(1, resultByteArray);
        String md5 = bigInt.toString(16);
        while(md5.length() < 32){
            md5 = "0" + md5;
        }
        return md5;
    }
}
