package comm.state;

public interface Context {

    public abstract void setClock(int hour);                // 设置时间
    public abstract void changeState(State state);          // 改变状态
    public abstract void callSecurityCenter(String msg);    // 联系警报中心
    public abstract void recordLog(String msg);             // 在警报中心留下记录
}
