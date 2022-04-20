package co.poemgen;

import co.poemgen.node.Node;
import co.poemgen.node.implementation.RuleNode;
import co.poemgen.rule.Rule;

import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class Poem {

    public static final int POEM_NUM_LINES = 5;

    private final Supplier<Node>[] ruleOptionsLine;

    private final Random random = new Random();

    public Poem(){
        TxtInterpreter txtInterpreter = new TxtInterpreter("src/main/resources/gramatica.txt");
        Map<String, Rule> rules = txtInterpreter.getRules();
        ruleOptionsLine = new Supplier[]{
                () -> new RuleNode(rules.get("<NOUN>")),
                () -> new RuleNode(rules.get("<PREPOSITION>")),
                () -> new RuleNode(rules.get("<PRONOUN>")),
        };
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
        int ruleIndex = ruleOptionsLine.length;
        RuleNode lineInitialRuleNode = (RuleNode) ruleOptionsLine[random.nextInt(ruleIndex)].get();
        Rule lineRule = lineInitialRuleNode.getRule();
        return lineRule.generated(lineInitialRuleNode);
    }
}
