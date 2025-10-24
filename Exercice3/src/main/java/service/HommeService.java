package service;

import classes.Femme;
import classes.Mariage;
import classes.Homme;
import dao.AbstractDao;
import org.hibernate.Session;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class HommeService extends AbstractDao<Homme> {
    public HommeService(){ super(Homme.class); }

    /** Épouses d’un homme entre deux dates (sur la date de début du mariage) */
    public List<Femme> epousesEntre(Integer hommeId, LocalDate d1, LocalDate d2){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("""
                SELECT DISTINCT m.femme
                FROM Mariage m
                WHERE m.homme.id = :hid
                  AND m.dateDebut BETWEEN :d1 AND :d2
            """, Femme.class)
                    .setParameter("hid", hommeId)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getResultList();
        }
    }

    /** Tous les mariages d’un homme (avec détails pour l’affichage final) */
    public List<Mariage> mariages(Integer hommeId){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("""
                SELECT m FROM Mariage m
                JOIN FETCH m.femme
                WHERE m.homme.id = :hid
                ORDER BY m.dateDebut
            """, Mariage.class)
                    .setParameter("hid", hommeId)
                    .getResultList();
        }
    }
}
