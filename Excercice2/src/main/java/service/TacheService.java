package service;

import classes.EmployeTache;
import classes.Tache;
import dao.AbstractDao;
import org.hibernate.Session;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class TacheService extends AbstractDao<Tache> {
    public TacheService() { super(Tache.class); }

    /** Requête nommée: prix > 1000 DH */
    public List<Tache> prixSuperieur(double min){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            return s.createNamedQuery("Tache.findPrixSuperieur", Tache.class)
                    .setParameter("min", min)
                    .getResultList();
        }
    }

    /** Tâches réalisées entre deux dates (sur les dates réelles) */
    public List<EmployeTache> realiseesEntre(LocalDate d1, LocalDate d2){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("""
                SELECT et FROM EmployeTache et
                JOIN FETCH et.tache t
                WHERE et.dateDebutReelle >= :d1 AND et.dateFinReelle <= :d2
                ORDER BY et.dateDebutReelle
            """, EmployeTache.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getResultList();
        }
    }
}
