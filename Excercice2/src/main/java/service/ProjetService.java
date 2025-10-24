package service;

import classes.EmployeTache;
import classes.Tache;
import dao.AbstractDao;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;

public class ProjetService extends AbstractDao<classes.Projet> {
    public ProjetService() { super(classes.Projet.class); }

    /** Tâches planifiées (du projet) */
    public List<Tache> tachesPlanifiees(Integer projetId){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("""
                SELECT t FROM Tache t
                WHERE t.projet.id = :pid
                ORDER BY t.dateDebut
            """, Tache.class).setParameter("pid", projetId).getResultList();
        }
    }

    /** Tâches réalisées (retourne les réalisations avec dates réelles) */
    public List<EmployeTache> tachesRealiseesAvecDates(Integer projetId){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("""
                SELECT et FROM EmployeTache et
                JOIN FETCH et.tache t
                WHERE t.projet.id = :pid
                ORDER BY et.dateDebutReelle
            """, EmployeTache.class).setParameter("pid", projetId).getResultList();
        }
    }
}
