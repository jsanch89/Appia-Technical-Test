package co.appia.rule;

import co.appia.node.Node;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Rule {
    protected Node[] ruleOptions;

    protected Node[] wordOptions;

    public Node generated() {
        throw new NotImplementedException();
    }
}
