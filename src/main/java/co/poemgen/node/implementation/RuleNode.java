package co.poemgen.node.implementation;

import co.poemgen.node.Node;
import co.poemgen.rule.Rule;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RuleNode implements Node {

    private Rule rule;

    private Node[] nextRule;

    public RuleNode(Rule rule) {
        this.rule = rule;
    }

    @Override
    public String getTextValue() {
        return Arrays.stream(nextRule)
                        .map(Node::getTextValue)
                        .collect(Collectors.joining(" "));
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public Node[] getNextRule() {
        return nextRule;
    }

    public void setNextRule(Node[] nextRule) {
        this.nextRule = nextRule;
    }
}
