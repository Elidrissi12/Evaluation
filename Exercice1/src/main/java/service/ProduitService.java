package service;

import classes.LigneCommandeProduit;
import classes.Produit;
import dao.AbstractDao;
import org.hibernate.Session;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class ProduitService extends AbstractDao<Produit> {
    public ProduitService() { super(Produit.class); }

    /** Liste des produits par catégorie */
    public List<Produit> findByCategorie(Integer categorieId){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("""
                    SELECT p FROM Produit p WHERE p.categorie.id = :cid
                """, Produit.class)
                    .setParameter("cid", categorieId)
                    .getResultList();
        }
    }

    /** Produits commandés entre deux dates */
    public List<Produit> findCommandesEntre(LocalDate d1, LocalDate d2){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("""
                    SELECT DISTINCT l.produit
                    FROM LigneCommandeProduit l
                    WHERE l.commande.date BETWEEN :d1 AND :d2
                """, Produit.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getResultList();
        }
    }

    /** Lignes d'une commande donnée (pour référence/prix/quantité) */
    public List<LigneCommandeProduit> findLignesByCommande(Integer cmdId){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("""
                    SELECT l FROM LigneCommandeProduit l
                    JOIN FETCH l.produit
                    JOIN FETCH l.commande
                    WHERE l.commande.id = :id
                """, LigneCommandeProduit.class)
                    .setParameter("id", cmdId)
                    .getResultList();
        }
    }

    /** Produits dont le prix > min (requête nommée) */
    public List<Produit> findPrixSuperieur(float min){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            return s.createNamedQuery("Produit.findPrixSuperieur", Produit.class)
                    .setParameter("minPrix", min)
                    .getResultList();
        }
    }
}
