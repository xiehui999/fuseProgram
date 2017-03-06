package com.iterator;

public class Main {
	/**
	 * 迭代器模式
	 * @param args
     */
	public static void main(String[] args) {
		BookShelf bookShelf=new BookShelf(4);
		bookShelf.appendBook(new Book("图解设计模式"));
		bookShelf.appendBook(new Book("未来简史"));
		bookShelf.appendBook(new Book("Android性能优化"));
		bookShelf.appendBook(new Book("程序员修炼之道"));
		Iterator iterator=bookShelf.iterator();
		while (iterator.hasNext()) {
			Book book = (Book) iterator.next();
			System.out.println(book.getName());
		}
	}

}
