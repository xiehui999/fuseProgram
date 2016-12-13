package com.example.xh.uploadfile;

import java.io.Serializable;

/**
 * Created by xiehui on 2016/10/14.
 */
public class FileInfo implements Serializable{
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 管理员guid
     */
    private String gguid;

    /**
     * 文件的MD5值
     */
    private String md5;
    /**
     * 文件长度
     */
    private long fileLength;


    /**
     * 是不是一个数据块
     * true 表示是一个文件，false表示是一个块（分片的数据）
     */
    private boolean isChunk;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getGguid() {
        return gguid;
    }

    public void setGguid(String gguid) {
        this.gguid = gguid;
    }


    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean isChunk() {
        return isChunk;
    }

    public void setIsChunk(boolean isChunk) {
        this.isChunk = isChunk;
    }
    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "filePath='" + filePath + '\'' +
                ", gguid='" + gguid + '\'' +
                ", md5='" + md5 + '\'' +
                ", fileLength=" + fileLength +
                ", isChunk=" + isChunk +
                '}';
    }
}
