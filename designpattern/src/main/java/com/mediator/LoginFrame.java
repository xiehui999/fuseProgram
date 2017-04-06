package com.mediator;

import java.awt.Frame;
import java.awt.Label;
import java.awt.Color;
import java.awt.CheckboxGroup;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginFrame extends Frame implements ActionListener, Mediator {
    private com.mediator.ColleagueCheckbox checkGuest;
    private com.mediator.ColleagueCheckbox checkLogin;
    private com.mediator.ColleagueTextField textUser;
    private com.mediator.ColleagueTextField textPass;
    private com.mediator.ColleagueButton buttonOk;
    private com.mediator.ColleagueButton buttonCancel;

    // 构造函数。
    // 生成并配置各个Colleague后，显示对话框。
    public LoginFrame(String title) {
        super(title);
        setBackground(Color.lightGray);
        // 使用布局管理器生成4×2窗格
        setLayout(new GridLayout(4, 2));
        // 生成各个Colleague
        createColleagues();
        // 配置
        add(checkGuest);
        add(checkLogin);
        add(new Label("Username:"));
        add(textUser);
        add(new Label("Password:"));
        add(textPass);
        add(buttonOk);
        add(buttonCancel);
        // 设置初始的启用起用/禁用状态
        colleagueChanged();
        // 显示
        pack();
        show();
    }

    // 生成各个Colleague。
    @Override
    public void createColleagues() {
        // 生成
        CheckboxGroup g = new CheckboxGroup();
        checkGuest = new com.mediator.ColleagueCheckbox("Guest", g, true);
        checkLogin = new com.mediator.ColleagueCheckbox("Login", g, false);
        textUser = new com.mediator.ColleagueTextField("", 10);
        textPass = new com.mediator.ColleagueTextField("", 10);
        textPass.setEchoChar('*');
        buttonOk = new com.mediator.ColleagueButton("OK");
        buttonCancel = new com.mediator.ColleagueButton("Cancel");
        // 设置Mediator
        checkGuest.setMediator(this);
        checkLogin.setMediator(this);
        textUser.setMediator(this);
        textPass.setMediator(this);
        buttonOk.setMediator(this);
        buttonCancel.setMediator(this);
        // 设置Listener
        checkGuest.addItemListener(checkGuest);
        checkLogin.addItemListener(checkLogin);
        textUser.addTextListener(textUser);
        textPass.addTextListener(textPass);
        buttonOk.addActionListener(this);
        buttonCancel.addActionListener(this);
    }

    // 接收来自于Colleage的通知然后判断各Colleage的启用/禁用状态。
    @Override
    public void colleagueChanged() {
        if (checkGuest.getState()) { // Guest mode
            textUser.setColleagueEnabled(false);
            textPass.setColleagueEnabled(false);
            buttonOk.setColleagueEnabled(true);
        } else { // Login mode
            textUser.setColleagueEnabled(true);
            userpassChanged();
        }
    }
    // 当textUser或是textPass文本输入框中的文字发生变化时
    // 判断各Colleage的启用/禁用状态
    private void userpassChanged() {
        if (textUser.getText().length() > 0) {
            textPass.setColleagueEnabled(true);
            if (textPass.getText().length() > 0) {
                buttonOk.setColleagueEnabled(true);
            } else {
                buttonOk.setColleagueEnabled(false);
            }
        } else {
            textPass.setColleagueEnabled(false);
            buttonOk.setColleagueEnabled(false);
        }
    }
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.toString());
        System.exit(0);
    }
}
