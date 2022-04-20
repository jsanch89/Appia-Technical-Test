package co.poemgen.rule;

import co.poemgen.node.Node;
import co.poemgen.node.implementation.RuleNode;
import co.poemgen.node.implementation.WordNode;

import java.util.Random;
import java.util.function.Supplier;

public class Rule {

    public String name;

    protected Supplier<Node>[][] ruleOptions;

    private Random random = new Random();

    public Rule(String name) {
        this.name = name;
    }

    public void setRuleOptions(Supplier<Node>[][] ruleOptions) {
        this.ruleOptions = ruleOptions;
    }

    public Node generated(RuleNode currentNode) {
        int nextRuleLength = ruleOptions.length;
        Node[] nextNodesRules = new Node[nextRuleLength];

        for(int i=0; i<ruleOptions.length; i++) {
            Supplier<Node>[] ruleOption = ruleOptions[i];
            Supplier<Node> valueNode = ruleOption[getRandomInt(ruleOption.length)];
            Node currentNodeValue = valueNode.get();

            if(currentNodeValue instanceof WordNode){
                nextNodesRules[i] = currentNodeValue;
            }
            else {
                Rule rule = currentNode.getRule();
                nextNodesRules[i] = rule.generated((RuleNode) currentNodeValue);
            }
        }

        currentNode.setNextRule(nextNodesRules);
        return currentNode;
    }

    private int getRandomInt(int length) {
        return random.nextInt(length);
    }

    public Supplier<Node>[][] getRuleOptions() {
        return ruleOptions;
    }
}
