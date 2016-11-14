package com.example.xh.retrofit.potting;

import java.util.List;

/**
 * Created by xiehui on 2016/11/10.
 */
public class BookList {
    private int count;

    private int start;

    private int total;

    private List<BookInfo> books ;

    @Override
    public String toString() {
        return "BookList{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", books=" + books.toString() +
                '}';
    }

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setStart(int start){
        this.start = start;
    }
    public int getStart(){
        return this.start;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setBooks(List<BookInfo> books){
        this.books = books;
    }
    public List<BookInfo> getBooks(){
        return this.books;
    }


}