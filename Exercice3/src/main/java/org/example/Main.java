package org.example;

import classes.*;
import service.*;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var hSrv = new HommeService();
        var fSrv = new FemmeService();
        var mSrv = new MariageService();

        // --- Créer 5 hommes ---
        Homme h1 = new Homme(); h1.setNom("SAFI"); h1.setPrenom("SAID"); hSrv.create(h1);
        Homme h2 = new Homme(); h2.setNom("AMINE"); h2.setPrenom("KHALID"); hSrv.create(h2);
        Homme h3 = new Homme(); h3.setNom("NABIL"); h3.setPrenom("OMAR"); hSrv.create(h3);
        Homme h4 = new Homme(); h4.setNom("ADIL"); h4.setPrenom("HASSAN"); hSrv.create(h4);
        Homme h5 = new Homme(); h5.setNom("YASSINE"); h5.setPrenom("ALI"); hSrv.create(h5);

        // --- Créer 10 femmes ---
        Femme f1 = mkF("SALIMA","RAMI", fSrv);
        Femme f2 = mkF("AMAL","ALI", fSrv);
        Femme f3 = mkF("WAFA","ALAQUI", fSrv);
        Femme f4 = mkF("KARIMA","ALAMI", fSrv);
        Femme f5 = mkF("NADIA","SAAD", fSrv);
        Femme f6 = mkF("SARA","OMARI", fSrv);
        Femme f7 = mkF("HIBA","ZAHRA", fSrv);
        Femme f8 = mkF("LINA","BEN", fSrv);
        Femme f9 = mkF("IMANE","FARAH", fSrv);
        Femme f10= mkF("ASMAA","RAFI", fSrv);

        // --- Créer des mariages (exemple proche de l’énoncé) ---
        mSrv.create(mkM(h1,f1, d(1990,9,3),  null, 4)); // en cours
        mSrv.create(mkM(h1,f2, d(1995,9,3),  null, 2)); // en cours
        mSrv.create(mkM(h1,f3, d(2000,11,4), null, 3)); // en cours
        mSrv.create(mkM(h1,f4, d(1989,9,3),  d(1990,9,3), 0)); // échoué

        // quelques autres pour les requêtes
        mSrv.create(mkM(h2,f5, d(2010,1,1), null, 1));
        mSrv.create(mkM(h2,f6, d(2011,1,1), null, 0));
        mSrv.create(mkM(h2,f7, d(2012,1,1), null, 0));
        mSrv.create(mkM(h2,f8, d(2013,1,1), null, 2)); // h2 marié à 4 femmes

        // --- Affichages demandés ---

        // 1) Fiche d’un homme (mariages en cours + échoués)
        System.out.println("Nom : " + h1.getNom() + " " + h1.getPrenom());
        List<Mariage> marr = hSrv.mariages(h1.getId());

        System.out.println("Mariages En Cours :");
        int i = 1;
        for (Mariage m : marr) {
            if (m.getDateFin() == null) {
                System.out.printf("%d. Femme : %s %s   Date Début : %s   Nbr Enfants : %d%n",
                        i++, m.getFemme().getNom(), m.getFemme().getPrenom(),
                        m.getDateDebut(), m.getNbrEnfant());
            }
        }
        System.out.println("\nMariages échoués :");
        i = 1;
        for (Mariage m : marr) {
            if (m.getDateFin() != null) {
                System.out.printf("%d. Femme : %s %s   Date Début : %s   Date Fin : %s   Nbr Enfants : %d%n",
                        i++, m.getFemme().getNom(), m.getFemme().getPrenom(),
                        m.getDateDebut(), m.getDateFin(), m.getNbrEnfant());
            }
        }

        // 2) Épouses d’un homme entre deux dates
        System.out.println("\nÉpouses de SAID entre 1994 et 2001 :");
        hSrv.epousesEntre(h1.getId(), d(1994,1,1), d(2001,12,31))
                .forEach(f -> System.out.println("- " + f.getNom() + " " + f.getPrenom()));

        // 3) Nb d’enfants d’une femme entre deux dates (requête native nommée)
        int nb = fSrv.nombreEnfantsEntre(f1.getId(), d(1989,1,1), d(2020,12,31));
        System.out.println("\nNb d’enfants de " + f1.getNom() + " entre 1989 et 2020 : " + nb);

        // 4) Femmes mariées deux fois ou plus (requête nommée)
        System.out.println("\nFemmes mariées au moins deux fois :");
        fSrv.marieesDeuxFoisOuPlus().forEach(f ->
                System.out.println("- " + f.getNom() + " " + f.getPrenom())
        );

        // 5) Criteria API : nombre d’hommes mariés à 4 femmes entre deux dates
        long nbh = mSrv.nbHommesMarieAQuatreFemmesEntre(d(2009,1,1), d(2014,12,31));
        System.out.println("\nHommes mariés à 4 femmes (2009-2014) : " + nbh);
    }

    private static Femme mkF(String nom, String prenom, FemmeService fSrv){
        Femme f = new Femme(); f.setNom(nom); f.setPrenom(prenom); return fSrv.create(f);
    }
    private static Mariage mkM(Homme h, Femme f, LocalDate d1, LocalDate d2, int n){
        Mariage m = new Mariage();
        m.setHomme(h); m.setFemme(f); m.setDateDebut(d1); m.setDateFin(d2); m.setNbrEnfant(n);
        return m;
    }
    private static LocalDate d(int y,int m,int d){ return LocalDate.of(y,m,d); }
}
