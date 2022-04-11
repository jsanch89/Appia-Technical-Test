package co.poemgen;

import co.poemgen.node.Node;
import co.poemgen.node.implementation.RuleNode;
import co.poemgen.node.implementation.WordNode;
import co.poemgen.rule.Rule;
import co.poemgen.rule.implementation.*;

import java.util.Random;
import java.util.function.Supplier;

public class Poem {

    public static final int POEM_NUM_LINES = 5;
    private final Rule noun = new Noun();
    private final Rule adjective = new Adjective();
    private final Rule preposition = new Preposition();
    private final Rule pronoun = new Pronoun();
    private final Rule verb = new Verb();

    private final Supplier<Node> nounNode = () -> new RuleNode(noun);
    private final Supplier<Node> adjectiveNode = () -> new RuleNode(adjective);
    private final Supplier<Node> prepositionNode = () -> new RuleNode(preposition);
    private final Supplier<Node> pronounNode = () -> new RuleNode(pronoun);
    private final Supplier<Node> verbNode = () -> new RuleNode(verb);
    private final static Supplier<Node> end = () -> new WordNode("\n");

    private final Supplier<Node>[] RULE_OPTIONS_ADJECTIVE = new Supplier[]{nounNode, adjectiveNode, end};
    private final Supplier<Node>[] RULE_OPTIONS_NOUN = new Supplier[]{verbNode, prepositionNode, end};
    private final Supplier<Node>[] RULE_OPTIONS_PREPOSITION = new Supplier[]{nounNode, pronounNode, adjectiveNode};
    private final Supplier<Node>[] RULE_OPTIONS_PRONOUN = new Supplier[]{nounNode, adjectiveNode};
    private final Supplier<Node>[] RULE_OPTIONS_VERB = new Supplier[]{prepositionNode, pronounNode, end};

    private final Supplier<Node>[] RULE_OPTIONS_LINE = new Supplier[]{nounNode,prepositionNode,pronounNode};

    private final Random random = new Random();

    public Poem(){
        addRules();
    }

    public String genPoem(){
        Node[] poem = new Node[POEM_NUM_LINES];
        for(int line = 0; line<POEM_NUM_LINES; line++) {
            poem[line] = generateLine();
        }
        String poemString = "";
        for (Node line: poem){
            poemString+= line.getTextValue()+"\n";
        }
        return poemString;
    }

    private Node generateLine(){
        int ruleIndex = RULE_OPTIONS_LINE.length;
        RuleNode lineInitialRuleNode = (RuleNode) RULE_OPTIONS_LINE[random.nextInt(ruleIndex)].get();
        Rule lineRule = lineInitialRuleNode.getRule();
        return lineRule.generated(lineInitialRuleNode);
    }

    private void addRules() {
        noun.setRuleOptions(RULE_OPTIONS_NOUN);
        adjective.setRuleOptions(RULE_OPTIONS_ADJECTIVE);
        preposition.setRuleOptions(RULE_OPTIONS_PREPOSITION);
        pronoun.setRuleOptions(RULE_OPTIONS_PRONOUN);
        verb.setRuleOptions(RULE_OPTIONS_VERB);
    }
}
