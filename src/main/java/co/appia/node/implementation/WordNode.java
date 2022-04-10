package co.appia.node.implementation;

import co.appia.node.Node;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WordNode implements Node {

    private String value;

    @Override
    public String getValue() {
        throw new NotImplementedException();
    }
}
