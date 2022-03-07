import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");
        Graphe test = Graphe.ImporterGraphe("Cours_Representation");
        //System.out.println(test.toString());
        test.affichage();
    }
}
