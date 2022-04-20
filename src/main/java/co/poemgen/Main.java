package co.poemgen;

public class Main {
    public static void main(String[] args) {
      final Poem poemGen = new Poem(args[0]);
      System.out.println(poemGen.genPoem());
    }
}
