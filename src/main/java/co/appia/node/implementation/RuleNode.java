package co.appia.node.implementation;

import co.appia.node.Node;
import co.appia.rule.Rule;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RuleNode implements Node {

    private Rule rule;

    private Node value;

    private Node nextRule;

    @Override
    public String getValue() {
        throw new NotImplementedException();
    }
}
