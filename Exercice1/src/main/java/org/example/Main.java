package org.example;

import classes.*;
import service.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        var catSrv = new CategorieService();
        var prodSrv = new ProduitService();
        var cmdSrv  = new CommandeService();
        var ligSrv  = new LigneCommandeService();

        // 1) Données de base
        var cat = new Categorie();
        cat.setCode("PC");
        cat.setLibelle("Ordinateurs");
        catSrv.create(cat);

        var p1 = new Produit(); p1.setReference("ES12"); p1.setPrix(120); p1.setCategorie(cat); prodSrv.create(p1);
        var p2 = new Produit(); p2.setReference("ZR85"); p2.setPrix(100); p2.setCategorie(cat); prodSrv.create(p2);
        var p3 = new Produit(); p3.setReference("EE85"); p3.setPrix(200); p3.setCategorie(cat); prodSrv.create(p3);

        var c = new Commande(); c.setDate(LocalDate.of(2013,3,14)); cmdSrv.create(c);

        var l1 = new LigneCommandeProduit(); l1.setCommande(c); l1.setProduit(p1); l1.setQuantite(7);  ligSrv.create(l1);
        var l2 = new LigneCommandeProduit(); l2.setCommande(c); l2.setProduit(p2); l2.setQuantite(14); ligSrv.create(l2);
        var l3 = new LigneCommandeProduit(); l3.setCommande(c); l3.setProduit(p3); l3.setQuantite(5);  ligSrv.create(l3);

        // 2) Affichage des fonctionnalités demandées

        System.out.println("== Produits par catégorie PC ==");
        prodSrv.findByCategorie(cat.getId()).forEach(
                p -> System.out.println(p.getReference() + " - " + p.getPrix() + " DH")
        );

        System.out.println("\n== Produits commandés entre 2013-03-01 et 2013-03-31 ==");
        prodSrv.findCommandesEntre(LocalDate.of(2013,3,1), LocalDate.of(2013,3,31))
                .forEach(p -> System.out.println(p.getReference()));

        System.out.println("\nCommande : " + c.getId() + "    Date : " + c.getDate());
        System.out.println("Liste des produits :");
        System.out.println("Référence   Prix      Quantité");
        prodSrv.findLignesByCommande(c.getId()).forEach(l -> {
            var p = l.getProduit();
            System.out.printf("%-10s  %-6.0f DH  %d%n", p.getReference(), p.getPrix(), l.getQuantite());
        });

        System.out.println("\n== Produits prix > 100 DH ==");
        prodSrv.findPrixSuperieur(100)
                .forEach(p -> System.out.println(p.getReference() + " - " + p.getPrix() + " DH"));
    }
}