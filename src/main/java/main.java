import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        
        Graphe test = Graphe.ImporterGraphe("CommunesFrance_5000coord");
        test.lecture_csv();
        
        
        //System.out.println(test.d_gps(3857, 379));
        //test.Djikstrafares(3857,379);
        //test.Astarfares(3857, 379);
        //test.DjikstrafaresFibo(3857,379);
        //test.DjikstrafaresFibototal(3857);
        //test.vrpPosition();


        long time0 = System.currentTimeMillis();
        test.vrpRoute(100000);
        System.out.println("temps d'execution (en ms) : " +(System.currentTimeMillis() - time0));
        /*long time1 = System.currentTimeMillis();
        test.Astarfares(3857, 379);
        System.out.println("temps : " +(System.currentTimeMillis() - time1));
        long time2 = System.currentTimeMillis();
        test.DjikstrafaresFibo(3857,379);
        System.out.println("temps : " +(System.currentTimeMillis() - time2));
        long time3 = System.currentTimeMillis();
        System.out.println("temps : " +(System.currentTimeMillis() - time3));
        long time4 = System.currentTimeMillis();
        System.out.println("temps : " +(System.currentTimeMillis() - time4));*/
    }
}
