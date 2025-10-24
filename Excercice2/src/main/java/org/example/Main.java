package org.example;

import classes.*;
import service.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        var empSrv = new EmployeService();
        var prjSrv = new ProjetService();
        var tchSrv = new TacheService();
        var etSrv  = new EmployeTacheService();

        // === Données d'exemple ===
        Employe chef = new Employe();
        chef.setNom("Dupont"); chef.setPrenom("Jean"); chef.setTelephone("0600000000");
        empSrv.create(chef);

        Employe e1 = new Employe();
        e1.setNom("Martin"); e1.setPrenom("Sophie");
        empSrv.create(e1);

        Employe e2 = new Employe();
        e2.setNom("Ben"); e2.setPrenom("Ali");
        empSrv.create(e2);

        Projet p = new Projet();
        p.setNom("Gestion de stock");
        p.setDateDebut(LocalDate.of(2013,1,14));
        p.setDateFin(LocalDate.of(2013,5,1));
        p.setChefProjet(chef);
        prjSrv.create(p);

        Tache t1 = new Tache();
        t1.setNom("Analyse");
        t1.setDateDebut(LocalDate.of(2013,2,10));
        t1.setDateFin(LocalDate.of(2013,2,20));
        t1.setPrix(1500);
        t1.setProjet(p);
        tchSrv.create(t1);

        Tache t2 = new Tache();
        t2.setNom("Conception");
        t2.setDateDebut(LocalDate.of(2013,3,10));
        t2.setDateFin(LocalDate.of(2013,3,15));
        t2.setPrix(900);
        t2.setProjet(p);
        tchSrv.create(t2);

        Tache t3 = new Tache();
        t3.setNom("Développement");
        t3.setDateDebut(LocalDate.of(2013,4,10));
        t3.setDateFin(LocalDate.of(2013,4,25));
        t3.setPrix(2000);
        t3.setProjet(p);
        tchSrv.create(t3);

        // Réalisations (dates réelles) via EmployeTache
        EmployeTache r1 = new EmployeTache();
        r1.setEmploye(e1); r1.setTache(t1);
        r1.setDateDebutReelle(LocalDate.of(2013,2,10));
        r1.setDateFinReelle(LocalDate.of(2013,2,20));
        etSrv.create(r1);

        EmployeTache r2 = new EmployeTache();
        r2.setEmploye(e1); r2.setTache(t2);
        r2.setDateDebutReelle(LocalDate.of(2013,3,10));
        r2.setDateFinReelle(LocalDate.of(2013,3,15));
        etSrv.create(r2);

        EmployeTache r3 = new EmployeTache();
        r3.setEmploye(e2); r3.setTache(t3);
        r3.setDateDebutReelle(LocalDate.of(2013,4,10));
        r3.setDateFinReelle(LocalDate.of(2013,4,25));
        etSrv.create(r3);

        // === Affichages demandés ===

        // 1) Projet: tâches planifiées + tâches réalisées (format exemple)
        System.out.printf("Projet : %d    Nom : %s    Date début : %s%n",
                p.getId(), p.getNom(), p.getDateDebut());

        System.out.println("Liste des tâches:");
        System.out.println("Num  Nom           Date Début Réelle   Date Fin Réelle");

        prjSrv.tachesRealiseesAvecDates(p.getId()).forEach(et -> {
            var t = et.getTache();
            System.out.printf("%-4d %-13s %-17s %-15s%n",
                    t.getId(), t.getNom(),
                    et.getDateDebutReelle(), et.getDateFinReelle());
        });

        // 2) EmployeService: tâches réalisées par e1 + projets gérés par 'chef'
        System.out.println("\nTâches réalisées par " + e1.getNom() + " " + e1.getPrenom() + " :");
        empSrv.tachesRealisees(e1.getId()).forEach(et ->
                System.out.println("- " + et.getTache().getNom() + " (" + et.getDateDebutReelle() + " → " + et.getDateFinReelle() + ")")
        );

        System.out.println("\nProjets gérés par " + chef.getNom() + " :");
        empSrv.projetsGerés(chef.getId()).forEach(pr ->
                System.out.println("- " + pr.getNom())
        );

        // 3) TacheService: prix > 1000 (requête nommée) + réalisées entre deux dates
        System.out.println("\nTâches prix > 1000 DH :");
        tchSrv.prixSuperieur(1000).forEach(t ->
                System.out.println("- " + t.getNom() + " (" + t.getPrix() + " DH)")
        );

        System.out.println("\nTâches réalisées entre 2013-02-01 et 2013-03-31 :");
        tchSrv.realiseesEntre(LocalDate.of(2013,2,1), LocalDate.of(2013,3,31)).forEach(et ->
                System.out.println("- " + et.getTache().getNom() + " par " +
                        et.getEmploye().getNom() + " (" + et.getDateDebutReelle() + " → " + et.getDateFinReelle() + ")")
        );
    }
}
