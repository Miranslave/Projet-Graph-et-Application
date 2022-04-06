import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        
        Graphe test = Graphe.ImporterGraphe("CommunesFrance_5000coord");

        //System.out.println(test.estfils(3652, 0));


        //System.out.println(test.getListeSommets().get(3857).getNom());
        test.algoDjikstra(3857,379);
        test.algoDjikstraSkipList(3857,379);
        test.aEtoiles(3857, 379);
        //System.out.println(test.getListeSommets().get(0).affichage()+test.getListeAdjacence().get(0));
        
        //System.out.println(test.Besttrack(0));
        
        //test.lecture_csv();


        /* pour tester le graph 
        for (int i = 0; i < test.getListeAdjacence().get(0).size() ; i++) {
        System.out.println(0+" "+test.getListeAdjacence().get(0).get(i).affichage());
        }
        System.out.println(test.toString());
        */
    }
}
