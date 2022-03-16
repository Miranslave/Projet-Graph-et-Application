import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        
        Graphe test = Graphe.ImporterGraphe("CommunesFrance_5000");

        test.affichage_degree();

        test.parcoursLargeur(0);
        /* pour tester le graph 
        for (int i = 0; i < test.getListeAdjacence().get(0).size() ; i++) {
        System.out.println(0+" "+test.getListeAdjacence().get(0).get(i).affichage());
        }
        System.out.println(test.toString());
        */
    }
}
