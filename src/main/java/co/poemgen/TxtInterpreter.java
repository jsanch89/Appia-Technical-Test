package co.poemgen;

import co.poemgen.node.Node;
import co.poemgen.node.implementation.RuleNode;
import co.poemgen.node.implementation.WordNode;
import co.poemgen.rule.Rule;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TxtInterpreter {

    final private Map<String,Rule> rules = new HashMap<>();
    final private Map<String,String[]> nexRules = new HashMap<>();

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
        final String[] ruleDefinition = ruleSplit[1].substring(1).split(" ");
        final String[] ruleWords = ruleDefinition[0].split("\\|");
        final String[] ruleNextRules = ruleDefinition[1].split("\\|");

        if(rules.containsKey(ruleName)){
            throw new Exception("Duplicated rule");
        }
        final Rule ruleObject = new Rule(ruleName, ruleWords);
        rules.put(ruleName,ruleObject);
        nexRules.put(ruleName,ruleNextRules);
    }

    public void populateNextRules() {

        Map<String, Supplier<Node>[]> rulesNextNode = rules.entrySet()
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

    private Supplier<Node>[] getNextRules(final String[] rulesName){
        List<Rule> nextRules = Arrays.stream(rulesName)
                .map(name -> rules.get(name))
                .collect(Collectors.toList());

        Supplier<Node>[] rulesSuppliers = new Supplier[rulesName.length];
        for (int i=0; i<rulesName.length; i++){
            if(nextRules.get(i) == null){
                rulesSuppliers[i] = () -> new WordNode("\n");
            }
            else {
                Rule rule = nextRules.get(i);
                rulesSuppliers[i] = () -> new RuleNode(rule);
            }
        }
        return rulesSuppliers;
    }

    public Map<String, Rule> getRules() {
        return rules;
    }
}
