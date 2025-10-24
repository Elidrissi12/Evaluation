package util;

import classes.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Properties props = new Properties();
            try (InputStream in = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("application.properties")) {
                if (in != null) props.load(in);
            }
            Configuration cfg = new Configuration();
            // Entités :
            cfg.addAnnotatedClass(Employe.class);
            cfg.addAnnotatedClass(Projet.class);
            cfg.addAnnotatedClass(Tache.class);
            cfg.addAnnotatedClass(EmployeTache.class);

            props.forEach((k,v) -> cfg.setProperty(k.toString(), v.toString()));
            return cfg.buildSessionFactory();
        } catch (Exception ex) {
            throw new RuntimeException("Erreur init SessionFactory", ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
