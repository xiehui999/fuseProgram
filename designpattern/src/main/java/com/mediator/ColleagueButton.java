package com.mediator;

import java.awt.Button;

public class ColleagueButton extends Button implements Colleague {
    private com.mediator.Mediator mediator;
    public ColleagueButton(String caption) {
        super(caption);
    }
    @Override
    public void setMediator(com.mediator.Mediator mediator) {            // 保存Mediator
        this.mediator = mediator;
    }
    @Override
    public void setColleagueEnabled(boolean enabled) {      // Mediator下达启用/禁用的指示 
        setEnabled(enabled);
    }
}
