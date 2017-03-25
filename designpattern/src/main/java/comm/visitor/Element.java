package comm.visitor;

public interface Element {
    public abstract void accept(Visitor v);
}
