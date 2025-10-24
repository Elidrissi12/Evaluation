package service;

import classes.Mariage;
import dao.AbstractDao;
import org.hibernate.Session;
import util.HibernateUtil;

import jakarta.persistence.criteria.*;
import java.time.LocalDate;

public class MariageService extends AbstractDao<Mariage> {
    public MariageService(){ super(Mariage.class); }

    /** Criteria API: nombre d’hommes mariés à 4 femmes entre deux dates */
    public long nbHommesMarieAQuatreFemmesEntre(LocalDate d1, LocalDate d2){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);

            Root<Mariage> m = cq.from(Mariage.class);
            // count distinct hommes dont le nombre de femmes distinctes = 4
            cq.select(cb.countDistinct(m.get("homme")))
                    .where(cb.between(m.get("dateDebut"), d1, d2))
                    .groupBy(m.get("homme"))
                    .having(cb.equal(cb.countDistinct(m.get("femme")), 4));

            return s.createQuery(cq).getResultList().stream().mapToLong(Long::longValue).sum();
        }
    }
}
