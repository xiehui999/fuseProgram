package com.iterator;

public class BookShelfIterator implements Iterator {
	private int index;
	private BookShelf bookShelf;

	public BookShelfIterator(BookShelf bookShelf) {
		this.bookShelf = bookShelf;
		this.index = 0;
	}

	@Override
	public boolean hasNext() {
		if (index < bookShelf.getLength()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object next() {
		Book book=bookShelf.getBookAt(index);
		index++;
		return book;
	}

}
