import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        
        Graphe test = Graphe.ImporterGraphe("CommunesFrance_5000coord");


        System.out.println(test.parent(0));
        System.out.println(test.getListeAdjacence().get(0).get(0).getValeurs(0));
        test.affichage_resume();
        /* pour tester le graph 
        for (int i = 0; i < test.getListeAdjacence().get(0).size() ; i++) {
        System.out.println(0+" "+test.getListeAdjacence().get(0).get(i).affichage());
        }
        System.out.println(test.toString());
        */
    }
}
