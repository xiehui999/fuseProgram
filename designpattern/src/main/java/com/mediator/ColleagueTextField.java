package com.mediator;

import java.awt.TextField;
import java.awt.Color;
import java.awt.event.TextListener;
import java.awt.event.TextEvent;

public class ColleagueTextField extends TextField implements TextListener, Colleague {
    private Mediator mediator;
    public ColleagueTextField(String text, int columns) {   // 构造函数
        super(text, columns);
    }
    @Override
    public void setMediator(Mediator mediator) {            // 保存Mediator
        this.mediator = mediator;
    }
    @Override
    public void setColleagueEnabled(boolean enabled) {      // Mediator下达启用/禁用的指示
        setEnabled(enabled);
        setBackground(enabled ? Color.white : Color.lightGray);
    }
    public void textValueChanged(TextEvent e) {             // 当文字发生变化时通知Mediator
        mediator.colleagueChanged();
    }
}
