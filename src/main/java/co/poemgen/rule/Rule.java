package co.poemgen.rule;

import co.poemgen.node.Node;
import co.poemgen.node.implementation.RuleNode;
import co.poemgen.node.implementation.WordNode;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Rule {

    protected Supplier<Node>[] ruleOptions;

    protected Node[] wordOptions;

    private Random random = new Random();

    public Rule(String[] words) {
        wordOptions = new Node[words.length];
        Arrays.stream(words)
                .map(WordNode::new)
                .collect(Collectors.toList())
                .toArray(wordOptions);
    }

    public void setRuleOptions(Supplier<Node>[] ruleOptions) {
        this.ruleOptions = ruleOptions;
    }

    public Node generated(RuleNode currentNode) {

        Node valueNode = wordOptions[getRandomInt(wordOptions.length)];
        currentNode.setValue(valueNode);

        Node nextRule = ruleOptions[getRandomInt(ruleOptions.length)].get();

        if(nextRule instanceof WordNode){
            currentNode.setValue(nextRule);
            return currentNode;
        }

        Rule rule = ((RuleNode) nextRule).getRule();

        currentNode.setNextRule(rule.generated((RuleNode) nextRule));

        return currentNode;
    }

    private int getRandomInt(int length) {
        return random.nextInt(length);
    }
}
