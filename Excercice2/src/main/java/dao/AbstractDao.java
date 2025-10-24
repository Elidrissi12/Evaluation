package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;
import java.util.List;

public abstract class AbstractDao<T> implements IDao<T> {
    private final Class<T> clazz;
    protected AbstractDao(Class<T> clazz) { this.clazz = clazz; }

    public T create(T o){
        Transaction tx = null;
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            tx = s.beginTransaction();
            s.persist(o);
            tx.commit();
            return o;
        } catch(Exception e){ if(tx!=null) tx.rollback(); throw e; }
    }

    public T update(T o){
        Transaction tx = null;
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            tx = s.beginTransaction();
            s.merge(o);
            tx.commit();
            return o;
        } catch(Exception e){ if(tx!=null) tx.rollback(); throw e; }
    }

    public boolean delete(T o){
        Transaction tx = null;
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            tx = s.beginTransaction();
            s.remove(s.contains(o) ? o : s.merge(o));
            tx.commit();
            return true;
        } catch(Exception e){ if(tx!=null) tx.rollback(); return false; }
    }

    public T findById(Integer id){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            return s.get(clazz, id);
        }
    }

    public List<T> findAll(){
        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("FROM "+clazz.getSimpleName(), clazz).getResultList();
        }
    }
}
