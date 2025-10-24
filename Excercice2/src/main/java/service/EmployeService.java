package service;

import classes.EmployeTache;
import classes.Projet;
import classes.Tache;
import dao.AbstractDao;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.List;

public class EmployeService extends AbstractDao<classes.Employe> {
    public EmployeService() { super(classes.Employe.class); }

    /** Tâches réalisées par un employé (via EmployeTache) */
    public List<EmployeTache> tachesRealisees(Integer employeId){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("""
                SELECT et FROM EmployeTache et
                JOIN FETCH et.tache t
                WHERE et.employe.id = :id
            """, EmployeTache.class).setParameter("id", employeId).getResultList();
        }
    }

    /** Projets gérés par un employé (en tant que chef) */
    public List<Projet> projetsGerés(Integer employeId){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("""
                SELECT p FROM Projet p
                WHERE p.chefProjet.id = :id
            """, Projet.class).setParameter("id", employeId).getResultList();
        }
    }
}
