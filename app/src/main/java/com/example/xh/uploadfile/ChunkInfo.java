package com.example.xh.uploadfile;

/**
 * Created by xiehui on 2016/10/21.
 */
public class ChunkInfo extends FileInfo{
    /**
     * 文件的当前分片值
     */
    private int chunk=1;
    /**
     * 文件总分片值
     */
    private int chunks=1;
    /**
     * 下载进度值
     */
    private int progress=1;

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    public int getChunk() {
        return chunk;
    }

    public void setChunk(int chunk) {
        this.chunk = chunk;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "ChunkInfo{" +
                "chunk=" + chunk +
                ", chunks=" + chunks +
                ", progress=" + progress +
                '}';
    }
}
