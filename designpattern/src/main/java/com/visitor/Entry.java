package com.visitor;

import java.util.Iterator;

public abstract class Entry implements Element {
    public abstract String getName();                                   // 获取名字
    public abstract int getSize();                                      // 获取大小
    public Entry add(Entry entry) throws FileTreatmentException {       // 增加目录条目
        throw new FileTreatmentException();
    }
    public Iterator iterator() throws FileTreatmentException {    // 生成Iterator
        throw new FileTreatmentException();
    }
    public String toString() {                                          // 显示字符串
        return getName() + " (" + getSize() + ")";
    }
}
