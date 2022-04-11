package co.poemgen.node.implementation;

import co.poemgen.node.Node;
import co.poemgen.rule.Rule;

public class RuleNode implements Node {

    private Rule rule;

    private Node value;

    private Node nextRule;

    public RuleNode(Rule rule) {
        this.rule = rule;
    }

    @Override
    public String getTextValue() {
        if(nextRule == null){
            return value.getTextValue();
        }
        return value.getTextValue() + " " + nextRule.getTextValue();
    }

    public Node getValueNode() {
        return value;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public void setValue(Node value) {
        this.value = value;
    }

    public Node getNextRule() {
        return nextRule;
    }

    public void setNextRule(Node nextRule) {
        this.nextRule = nextRule;
    }
}
