import java.lang.reflect.Array;
import java .util.Collections;
import java.util.*;
import java.io.*;
import java.lang.Math;



public class Graphe {
    private boolean oriente;
    private int nbSommet;
    private int nbArcs;
    private int nbValeursParArc;
    private ArrayList<Vertex> listeSommets;
    private ArrayList<LinkedList<Edge>> listeAdjacence;
    private int[] degree;
    public Object getListeAdjacence;

    // Constructeur
    public Graphe() {
        this.oriente = true;
        this.nbSommet = 0;
        this.nbArcs = 0;
        this.nbValeursParArc = 0;
        this.listeSommets = new ArrayList<Vertex>();
        this.listeAdjacence = new ArrayList<LinkedList<Edge>>();
        this.degree = new int[0];
    }

    // Import d'un graphique à partir d'un fichier
    public static Graphe ImporterGraphe(String nomfichier) throws IOException {
        Graphe curt_graph = new Graphe();

        // chemin relatif : "../graphs/" +nomfichier+".tgoGraph"
        File doc = new File("./graphs/" + nomfichier + ".tgoGraph");
        try (BufferedReader obj = new BufferedReader(new FileReader(doc))) {
            String strng;
            int compt = 1;
            // Boucle de lecture du fichier, on le lit tant que la ligne n'est pas null (fin
            // de fichier)
            while ((strng = obj.readLine()) != null) {
                // Determination de l'orientation du graph
                if (compt == 2) {
                    String[] tab_crt = strng.split(" ");
                    if (tab_crt[1].equalsIgnoreCase("true")) {
                        curt_graph.setOriente(true);
                    } else {
                        curt_graph.setOriente(false);
                    }
                }
                // Definition du nombre de sommet, du nombre d'arcs, du nombre de valeurs par
                // arcs.
                // On initialise pour chaque sommet son degré à 0
                if (compt == 4) {
                    String[] param_int = strng.split(" ");
                    curt_graph.setNbSommet(Integer.parseInt(param_int[0]));
                    curt_graph.setNbArcs(Integer.parseInt(param_int[2]));
                    curt_graph.setNbValeursParArc(Integer.parseInt(param_int[3]));
                    curt_graph.setDegree(new int[curt_graph.getNbSommet()]);
                    // Initialisation du degré i à 0
                    for (int i = 0; i < curt_graph.getNbSommet(); i++) {
                        curt_graph.degree[i] = 0;
                    }
                }

                // Boucle de lecteur de saisie de chaque sommet
                if (5 <= compt && compt <= 5 + curt_graph.getNbSommet()) {
                    for (int i = 0; i < curt_graph.getNbSommet(); i++) {
                        strng = obj.readLine();
                        compt += 1;
                        String[] sommet = strng.split(" ");
                        // Ajout du sommet dans ListeSommets
                        curt_graph.getListeSommets().add(new Vertex(sommet[1], sommet[3], sommet[2],i));
                    }
                }

                // Boucle de lecture des arcs
                if (6 + curt_graph.getNbSommet() <= compt
                        && compt <= 6 + curt_graph.getNbSommet() + curt_graph.getNbArcs()) {
                    // Initialisation de la listeAdjacence
                    for (int i = 0; i < curt_graph.getNbSommet(); i++) {
                        curt_graph.getListeAdjacence().add(new LinkedList<Edge>());
                    }
                    // Boucle remplissage de listeAdjacence
                    for (int i = 0; i < curt_graph.getNbArcs(); i++) {
                        strng = obj.readLine();
                        compt += 1;

                        String[] edge_nom = strng.split(" ");
                        // On créer l'arc qu'on ajoutera dans listeAdjacence
                        Edge crt_edg = new Edge(Integer.parseInt(edge_nom[1]), curt_graph.getNbValeursParArc());
                        // Si l'arc contient des valeurs, alors on les ajoutes
                        if (edge_nom.length > 0) {
                            for (int m = 2; m < edge_nom.length; m++) {
                                crt_edg.setValeurs(m % curt_graph.getNbValeursParArc(),
                                        Double.parseDouble(edge_nom[m]));
                            }
                        }
                        // On ajoute a chaque sommet de l'edge 1 à son degré
                        curt_graph.degree[Integer.parseInt(edge_nom[1])] += 1;
                        curt_graph.degree[Integer.parseInt(edge_nom[0])] += 1;
                        // On ajoute au sommet concerné l'edge qu'on vient de créer
                        curt_graph.getListeAdjacence().get(Integer.parseInt(edge_nom[0])).add(crt_edg);

                    }

                    if (!curt_graph.oriente) {
                        for (int h = 0; h < curt_graph.getListeAdjacence().size(); h++) {
                            for (int u = 0; u < curt_graph.getListeAdjacence().get(h).size(); u++) {
                                boolean already_in = false;
                                int nouvelle_indice = curt_graph.getListeAdjacence().get(h).get(u).getSommetTerminal();
                                Edge reversed_edge = new Edge(h, curt_graph.getListeAdjacence().get(h).get(u).getTab());
                                for (int o = 0; o < curt_graph.getListeAdjacence().get(nouvelle_indice).size(); o++) {
                                    if (curt_graph.getListeAdjacence().get(nouvelle_indice).get(o)
                                            .getSommetTerminal() == reversed_edge.getSommetTerminal()) {
                                        already_in = true;
                                    }
                                }
                                if (!already_in) {
                                    curt_graph.getListeAdjacence().get(nouvelle_indice).add(reversed_edge);
                                }
                            }
                        }
                    }

                }
                // A la fin de chaque traitement de ligne on incrémente le compteur de ligne
                // d'un
                compt += 1;
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return curt_graph;

    }

    // region Parcours

    public void djikstra(int depart) {
        // X sommet - U ensemble des arcs - n nb sommet - m nb nbArcs
        ArrayList<Vertex> Z = new ArrayList<Vertex>();
        double[] lambda = new double[this.listeSommets.size()-1];

        for (int i = 0; i < this.listeSommets.size()-1;i++){
            if(this.estfils(depart, i)){
                int id_adjacence = 0;
                List<Edge> chemin_possible = this.getListeAdjacence().get(depart);
                for(int j = 0; j < chemin_possible.size();j++){
                    if(this.getListeSommets().get(chemin_possible.get(j).getSommetTerminal()) ==
                    this.getListeSommets().get(i)){
                        id_adjacence = j;
                    }
                }
                lambda[i] = this.getListeAdjacence().get(depart).get(id_adjacence).getValeurs(0);
                System.out.println(lambda[i]);
            }
            else{
            lambda[i] = 10000.0;
            }
            //System.out.println(lambda[i]);
        }
        for (Vertex v : this.listeSommets) {
            if (v != this.listeSommets.get(depart)) {
                Z.add(v);
            }
        }

        while (Z.size() > 0) {

        }

        //System.out.println(Z);

    }

    //j'ai déplacé mon code à la fin du programme, j'en refais un nouveau de 0

    //Version que j'essaie de faire de l'algo, flemme de reprendre la tienne je recommence de zero
    public double[] algoDjikstra(int sommetDepart, int sommetTerminal){
        ArrayList<Vertex> listeSommetsNonTraité = new ArrayList<>(listeSommets);
        double[] lambda = new double[listeSommets.size()];
        int sommetarbitraire = 0;
        listeSommetsNonTraité.remove(sommetDepart);
        lambda[sommetDepart] = 0;
        //initialisation
        //On parcours chaque sommet non traité
        Vertex crt_vertex = new Vertex("nom", "0.0", "0.0",0);
        for (Vertex v : listeSommetsNonTraité) {
            //on parcour chaque sommet de la liste d'adjacence du sommet de départ
            for(int i = 0; i < listeAdjacence.get(sommetDepart).size(); i++){
                //si le sommet v est dans la liste d'adjacence on met à jour sa valeur
                if(listeAdjacence.get(sommetDepart).get(i).getSommetTerminal() == v.getid()){
                    lambda[v.getid()] = listeAdjacence.get(sommetDepart).get(i).getValeurs(0);
                    crt_vertex = v;
                }
                else{
                    lambda[v.getid()] = 999999999999.0;
                }
            }
        }

        int compteur = 0;
        //
        while( crt_vertex.getid() != sommetTerminal) {
            //on recherche x appartenant à listeSommetNonTraité tel que lambda[x] soit le minimun de lambda et
            crt_vertex = listeSommetsNonTraité.get(0);
            for(Vertex v : listeSommetsNonTraité){
                if(lambda[v.getid()] < lambda[crt_vertex.getid()]){
                    crt_vertex = v;
                }
            }
            listeSommetsNonTraité.remove(crt_vertex);
            //On mets potentiellement a jour la longueur de chaque sommet
            for (int i = 0; i < listeAdjacence.get(crt_vertex.getid()).size(); i++) {
                if ((lambda[crt_vertex.getid()] + listeAdjacence.get(crt_vertex.getid()).get(i).getValeurs(0) )< lambda[listeAdjacence.get(crt_vertex.getid()).get(i).getSommetTerminal()]) {
                    lambda[listeAdjacence.get(crt_vertex.getid()).get(i).getSommetTerminal()] = lambda[crt_vertex.getid()] + listeAdjacence.get(crt_vertex.getid()).get(i).getValeurs(0);
                }
            }
            compteur += 1 ;
        }
        System.out.println("La distance entre les deux sommets est :  " + lambda[sommetTerminal] + "km");
        return lambda;
    }


    // Methode de parcours en Largeur du graphique, à partir d'un sommet placé en

    public HashMap<Integer, int[]> parcoursLargeur(int sommetDepart){
        HashMap<Integer, int[]> tableau = new HashMap<Integer, int[]>();
        //Distance d'un sommet au sommet de départ
        int distance = 1;
        //Ordre de traitement du sommet
        int ordreTraitement = 2;
        //Arraylist servant de file pour les sommet à traiter
        ArrayList<Integer> file = new ArrayList<Integer>();
        //ArrayList contenant pour chaque sommet un tableau de 3 entier (resp : etat , distance au sommet placé en params , son ordre de traitement)
        //valeur des états : 0 = non marqué ; 1 = marqué; 2 = traité

        //Boucle d'initialisation de l'arraylist
        for (int i = 0; i <=getNbSommet(); i++){
            int[] crt_edge = new int[3];
            crt_edge[1] = getNbSommet()+2;
            tableau.put(i,crt_edge);

        }
        //initialisation des valeurs pour le sommet placé en param
        tableau.get(sommetDepart)[0]=1;
        tableau.get(sommetDepart)[1]=0;
        tableau.get(sommetDepart)[2]=1;

        file.add(sommetDepart);

        //Boucle de parcours
        while(file.size() >0){

            int  crt_edge = file.get(0);
            //On parcour les sommets voisins au sommet étudié
            for (int i = 0; i< listeAdjacence.get(crt_edge).size(); i++) {

                //On verifie que le voisin n'est pas déjà marqué (état 1) ou traité (état 2)
                if (tableau.get(listeAdjacence.get(crt_edge).get(i).getSommetTerminal())[0] == 0) {

                    //On ajoute chaque voisin à la file d'attente
                    file.add(listeAdjacence.get(crt_edge).get(i).getSommetTerminal());
                    //On modifie les valeurs d'états de chaque voisin
                    tableau.get(listeAdjacence.get(crt_edge).get(i).getSommetTerminal())[0] = 1;
                    tableau.get(listeAdjacence.get(crt_edge).get(i).getSommetTerminal())[1] = distance;
                    tableau.get(listeAdjacence.get(crt_edge).get(i).getSommetTerminal())[2] = ordreTraitement;

                    ordreTraitement += 1;
                }
            }
            distance+=1;
            //On marque le sommet actuellement en traitement comme traité (état 2)

            tableau.get(crt_edge)[0] = 2;
            //jsp.get(crt_edge)[0] = 2;
            //On enlève le sommet traité de la file d'attente
            file.remove(0);
        }
        //Une fois la file vide, donc que le graph a été traité on renvoie l'ordre de traitement de chaque sommet
        //On affiche l'ordre dans lequel chaque sommet a été traité

        for(int i=0; i<getNbSommet(); i++){

            System.out.println("Le sommet "+ i + " a été traité en "+ tableau.get(i)[2] + " position");
        }
        return tableau;

    }
    // endregion

    public void lecture_csv() {
        File doc = new File("./graphs/CommunesFrance.csv");
        try (BufferedReader obj = new BufferedReader(new FileReader(doc))) {
            String strng;
            obj.readLine();
            while ((strng = obj.readLine()) != null) {
                String[] param_int = strng.split(";");
                // On extrait la valeur de la population de la ville
                int pop_temp = Integer.parseInt(param_int[2]);

                // On fait une boucle de parcour de chaque sommet pour lui attribuer sa pop
                for (int i = 0; i < listeSommets.size(); i++) {
                    // Si nous observons une une correspondance entre le sommet et le
                    if (listeSommets.get(i).getNom().equalsIgnoreCase(param_int[0])) {
                        listeSommets.get(i).setPop(pop_temp);
                        System.out.println(listeSommets.get(i).affichage_pop());
                    }
                }

            }
            ;
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // region Affichage

    // Affichage des caratériqtique du graph créer
    public void affichage_resume() {
        System.out.println("\n oriente=" + oriente
                + ", \n nbSommet=" + nbSommet +
                ",\n nbArcs=" + nbArcs
                + ",\n nbValeursParArc=" + nbValeursParArc);
    }

    // Affichage de la liste d'adjacence du graph
    public void affichage_adjacence() {

        System.out.println("\n Liste d'adjacence");
        for (int i = 0; i < listeAdjacence.size(); i++) {
            System.out.print(i + " " + listeSommets.get(i).affichage());
            if (listeAdjacence.get(i).size() == 0) {
                System.out.print("Vide");
            } else {
                for (int j = 0; j < listeAdjacence.get(i).size(); j++) {
                    System.out.print(listeAdjacence.get(i).get(j).affichage() + " ");
                }
            }
            System.out.print("\n");
        }

    }

    // Affiche la liste des sommet avec le nom de la communes
    public void affiche_sommet() {
        System.out.println("\n");
        System.out.println("------------------------------------------");
        System.out.println("\n");
        System.out.println("\n Liste des Sommets");
        for (int i = 0; i < listeSommets.size(); i++) {
            System.out.print(i + ":" + listeSommets.get(i).affichage() + "\n");
        }
    }

    // Affichage de la liste des degrees par communes
    public void affichage_degree() {
        // Séparateur
        for (int x = 0; x < listeSommets.size(); x++) {
            System.out.println(x + " " + listeSommets.get(x).affichage() + ":" + degree[x]);
        }
    }
    // endregion

    public ArrayList<Vertex> parent(int v) {
        ArrayList<Vertex> parent = new ArrayList<Vertex>();
        int temp;
        for (int i = 0; i < listeAdjacence.get(v).size(); i++) {
            temp = listeAdjacence.get(v).get(i).getSommetTerminal();
            parent.add(listeSommets.get(temp));
        }
        return parent;
    }

    // region Get / Set
    public boolean isOriente() {
        return oriente;
    }

    public void setOriente(boolean oriente) {
        this.oriente = oriente;
    }

    public int getNbSommet() {
        return nbSommet;
    }

    public void setNbSommet(int nbSommet) {
        this.nbSommet = nbSommet;
    }

    public int getNbArcs() {
        return nbArcs;
    }

    public void setNbArcs(int nbArcs) {
        this.nbArcs = nbArcs;
    }

    public int getNbValeursParArc() {
        return nbValeursParArc;
    }

    public void setNbValeursParArc(int nbValeursParArc) {
        this.nbValeursParArc = nbValeursParArc;
    }

    public ArrayList<Vertex> getListeSommets() {
        return listeSommets;
    }

    public ArrayList<LinkedList<Edge>> getListeAdjacence() {
        return listeAdjacence;
    }

    public void setDegree(int[] degree) {
        this.degree = degree;
    }
    // endregion

    public Vertex Besttrack(int id_vertex) {
        double best = 10000;
        Vertex res = null;
        Boolean arrive = false;

        List<Edge> chemin_possible = this.getListeAdjacence().get(id_vertex);
        for (Edge chemin : chemin_possible) {
            double distance_crt = chemin.getValeurs(0);
            if (distance_crt < best) {
                best = distance_crt;
                res = this.getListeSommets().get(chemin.getSommetTerminal());
            }
        }

        return res;
    }

    public boolean estfils(int id_vertex_pere, int id_vertex_tmp) {
        boolean res = false;
        List<Edge> chemin_possible = this.getListeAdjacence().get(id_vertex_pere);
        for(Edge chemin : chemin_possible){
            if(this.getListeSommets().get(chemin.getSommetTerminal()) ==
            this.getListeSommets().get(id_vertex_tmp)){
                res = true;
            }
        }
        return res;
    }


    public int DoubletoInt(double v) {
        int IntValue = (int) Math.round(v);
        return IntValue;
    }

    public float DoubletoFloat(double v) {
        float floatValue = (float) Math.round(v);
        return floatValue;
    }

    public double d_gps(int a,int b){
        Vertex v_1 = this.getListeSommets().get(a);
        Vertex v_2 = this.getListeSommets().get(b);
        double longitudev1 = Math.toRadians(v_1.getLongitude());
        double longitudev2 = Math.toRadians(v_2.getLongitude());
        double latitudev1 = Math.toRadians(v_1.getLatitude());
        double latitudev2 = Math.toRadians(v_2.getLatitude());
        int r_terre = 6378137;

        System.out.println("v1 : "+ longitudev1+"|"+latitudev1);
        System.out.println("v2 : "+ longitudev2+"|"+latitudev2);
        double lambda = longitudev2 - longitudev1;
        double temp = Math.sin(latitudev1)*Math.sin(latitudev2)+Math.cos(latitudev1)*Math.cos(latitudev2)*Math.cos(lambda);
        System.out.println(temp);
        double distance;
        distance = Math.acos(temp)*r_terre*0.001;
        return distance;
    }

    @Override
    // region toString
    // toString liste adjacence
    public String toString() {
        return "Graphe [listeAdjacence=" + listeAdjacence + "]";
    }
    // endregion
}


/*
//Initialisation du programme
            int[] sommettraite = new int[listeSommets.size()];


            //tableau contenant les distances de chaque sommet a sommetDepart
            double[] longueur = new double[this.listeSommets.size()];
            for(int i = 0; i < longueur.length; i++){
                //ne pouvant mettre la distance à l'infini, on met chaque distance à 999999999. Il ne peut y avoir une distance de 1Md de km entre deux villes en France
                longueur[i] = 999999999.0;
            }

        longueur[sommetDepart] = 0.0;
        int crt_edge = sommetDepart;
        boolean arrive = false;
        sommettraite[crt_edge] = 1;

        //Parcours
        while(!arrive){
            //tableau contenant les fils du sommet étudié
            int[] sommetvoisin = new int[listeAdjacence.get(crt_edge).size()];

            //on parcour l'ensemble des sommet adjacent au sommet de départ
            for(int i =0;  i < listeAdjacence.get(crt_edge).size(); i++){
                //on ajoute chaque fils au tableau sommetvoisin
                sommetvoisin[i] = listeAdjacence.get(crt_edge).get(i).getSommetTerminal();
                //On compare la distance du fils qu'il a déjà à sa possible nouvelle longueur si on utilise le chemin avec le sommet pere
                if(longueur[listeAdjacence.get(crt_edge).get(i).getSommetTerminal()] > longueur[crt_edge] + listeAdjacence.get(crt_edge).get(i).getValeurs(0)){
                    longueur[listeAdjacence.get(crt_edge).get(i).getSommetTerminal()] = longueur[crt_edge] + listeAdjacence.get(crt_edge).get(i).getValeurs(0);
                }
            }
            //boucle de recherche du plus petit arc des fils
            int pluspetitsommet = sommetvoisin[0];
            for(int i = 1; i< sommetvoisin.length; i++){
                if(longueur[pluspetitsommet] > longueur[sommetvoisin[i]]){
                    if(sommettraite[sommetvoisin[i]] == 0) {
                        pluspetitsommet = sommetvoisin[i];
                    }
                }
            }
            System.out.println("Sommet traité : "+ crt_edge + "   , le prochain sommet étudié est :" +pluspetitsommet);

            sommettraite[pluspetitsommet] = 1;
            crt_edge = pluspetitsommet;
            if(crt_edge == sommetTerminal){
                arrive = true;
            }

        }
        System.out.println("La distance est de :  " + longueur[sommetTerminal]);
        return longueur;
 */