package co.poemgen;

import co.poemgen.node.Node;
import co.poemgen.node.implementation.RuleNode;
import co.poemgen.rule.Rule;


public class Poem {

    private final Rule startRule;

    public Poem(String file){
        TxtInterpreter txtInterpreter = new TxtInterpreter(file);
        startRule = txtInterpreter.getInitialRule();
    }

    public String genPoem(){
        Node poem = startRule.generated(new RuleNode(startRule));
        return poem.getTextValue();
    }
}
