package service;

import classes.Femme;
import dao.AbstractDao;
import org.hibernate.Session;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class FemmeService extends AbstractDao<Femme> {

    public FemmeService() {
        super(Femme.class);
    }

    /**
     * Requête nommée JPQL : femmes mariées au moins deux fois
     */
    public List<Femme> marieesDeuxFoisOuPlus() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Femme.marieesDeuxFoisOuPlus", Femme.class)
                    .getResultList();
        }
    }

    /**
     * Requête JPQL : nombre d’enfants d’une femme entre deux dates
     * ✅ Compatible Hibernate 6
     * ✅ Utilise les noms d’attributs Java (pas les noms SQL)
     */
    public int nombreEnfantsEntre(Integer femmeId, LocalDate d1, LocalDate d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Number res = s.createQuery("""
                    SELECT COALESCE(SUM(m.nbrEnfant), 0)
                    FROM Mariage m
                    WHERE m.femme.id = :fid
                      AND m.dateDebut BETWEEN :d1 AND :d2
                    """, Number.class)
                    .setParameter("fid", femmeId)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getSingleResult();

            return res != null ? res.intValue() : 0;
        }
    }
}
