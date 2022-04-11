package co.poemgen.node.implementation;

import co.poemgen.node.Node;

public class WordNode implements Node {

    private String value;

    public WordNode(String value) {
        this.value = value;
    }

    @Override
    public String getTextValue() {
        return value;
    }
}
