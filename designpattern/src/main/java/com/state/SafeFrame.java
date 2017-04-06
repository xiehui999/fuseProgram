package com.state;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SafeFrame extends Frame implements ActionListener, Context {
    private TextField textClock = new TextField(60);        // 显示当前时间
    private TextArea textScreen = new TextArea(10, 60);     // 显示警报中心的记录
    private Button buttonUse = new Button("buttonUse");      // 金库使用按钮
    private Button buttonAlarm = new Button("buttonAlarm");    // 按下警铃按钮
    private Button buttonPhone = new Button("buttonPhone");    // 正常通话按钮
    private Button buttonExit = new Button("buttonExit");         // 结束按钮

    private State state = DayState.getInstance();           // 当前的状态

    // 构造函数
    public SafeFrame(String title) {
        super(title);
        setBackground(Color.lightGray);
        setLayout(new BorderLayout());
        //  配置textClock
        add(textClock, BorderLayout.NORTH);
        textClock.setEditable(false);
        // 配置textScreen
        add(textScreen, BorderLayout.CENTER);
        textScreen.setEditable(false);
        // 为界面添加按钮
        Panel panel = new Panel();
        panel.add(buttonUse);
        panel.add(buttonAlarm);
        panel.add(buttonPhone);
        panel.add(buttonExit);
        // 配置界面
        add(panel, BorderLayout.SOUTH);
        // 显示
        pack();
        show();
        // 设置监听器
        buttonUse.addActionListener(this);
        buttonAlarm.addActionListener(this);
        buttonPhone.addActionListener(this);
        buttonExit.addActionListener(this);
    }
    // 按钮被按下后该方法会被调用
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.toString());
        if (e.getSource() == buttonUse) {           // 金库使用按钮
            state.doUse(this);
        } else if (e.getSource() == buttonAlarm) {  // 按下警铃按钮
            state.doAlarm(this);
        } else if (e.getSource() == buttonPhone) {  // 正常通话按钮
            state.doPhone(this);
        } else if (e.getSource() == buttonExit) {   // 结束按钮
            System.exit(0);
        } else {
            System.out.println("?");
        }
    }
    // 设置时间
    @Override
    public void setClock(int hour) {
        String clockstring = "现在时间是";
        if (hour < 10) {
            clockstring += "0" + hour + ":00";
        } else {
            clockstring += hour + ":00";
        }
        System.out.println(clockstring);
        textClock.setText(clockstring);
        state.doClock(this, hour);
    }
    // 改变状态
    @Override
    public void changeState(State state) {
        System.out.println("从" + this.state + "状態变为了" + state + "状态。");
        this.state = state;
    }
    // 联系警报中心
    @Override
    public void callSecurityCenter(String msg) {
        textScreen.append("call! " + msg + "\n");
    }
    // 在警报中心留下记录
    @Override
    public void recordLog(String msg) {
        textScreen.append("record ... " + msg + "\n");
    }
}
