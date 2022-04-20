package co.poemgen;

import co.poemgen.node.Node;
import co.poemgen.node.implementation.RuleNode;
import co.poemgen.node.implementation.WordNode;
import co.poemgen.rule.Rule;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TxtInterpreter {

    final private Map<String,Rule> rules = new HashMap<>();
    final private Map<String,String[][]> nexRules = new HashMap<>();

    public TxtInterpreter(String fileName){
        readFile(fileName);
        populateNextRules();
    }

    private void readFile(String fileName) {
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String rule = myReader.nextLine();
                createRule(rule);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void createRule(final String rule) throws Exception{
        final String[] ruleSplit = rule.split(":");
        final String ruleName = "<"+ruleSplit[0]+">";
        final String[] ruleDefinition = ruleSplit[1].split(" ");
        final List<String[]> rulesOptions = new ArrayList<>();

        if(rules.containsKey(ruleName)){
            throw new Exception("Duplicated rule");
        }
        final Rule ruleObject = new Rule(ruleName);
        rules.put(ruleName,ruleObject);

        for(int i=0; i<ruleDefinition.length; i++) {
            String ruleNextRules = ruleDefinition[i];
            if(ruleNextRules != null && !ruleNextRules.isEmpty()){
                String[] ruleNextRulesSplit = ruleNextRules.split("\\|");
                rulesOptions.add(ruleNextRulesSplit);
            }
        }
        String[][] options = new String[rulesOptions.size()][];
        nexRules.put(ruleName,rulesOptions.toArray(options));
    }

    public void populateNextRules() {

        Map<String, Supplier<Node>[][]> rulesNextNode = rules.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(Entry::getKey,
                            (entry) -> getNextRules(nexRules.get(entry.getKey()))
                        )
                );

        rulesNextNode
                .entrySet()
                .stream()
                .forEach(entry -> {
                    rules.get(entry.getKey()).setRuleOptions(entry.getValue());
                });
    }

    private Supplier<Node>[][] getNextRules(final String[][] rulesName){
        Supplier<Node>[][] nextRules = new Supplier[rulesName.length][];
        for(int i=0; i<rulesName.length; i++) {
            String[] rulesOptions = rulesName[i];
            Supplier<Node>[] rulesOptionsNode = new Supplier[rulesOptions.length];
            for(int j=0; j<rulesOptions.length; j++){
                String ruleName = rulesOptions[j];
                if(ruleName.contains("<") && ruleName.contains(">")) {
                    Rule rule = rules.get(rulesOptions[j]);
                    rulesOptionsNode[j] = () -> new RuleNode(rule);
                }
                else if(ruleName.equals("$END") || ruleName.equals("$LINEBREAK")) {
                    rulesOptionsNode[j] = () -> new WordNode("\n");
                } else {
                    rulesOptionsNode[j] = () -> new WordNode(ruleName);
                }
            }
            nextRules[i] = rulesOptionsNode;
        }
        return nextRules;
    }

    public Rule getInitialRule() {
        return rules.get("<POEM>");
    }
}
