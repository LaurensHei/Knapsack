package nl.group37.algorithm.matrix;

public class Header extends Node {
    private String name;
    public int size;

    public Header() {
    }

    public Header(String name) {
        this.name = name;
        this.size = 0;
    }

    public void increase() {
        size++;
    }
    public void decrease() {
        size--;
    }
    public String getName() {
        return name;
    }
    public int getSize() {
        return size;
    }

}
