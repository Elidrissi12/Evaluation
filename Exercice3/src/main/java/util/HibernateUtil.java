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

            // Enregistrer les entités
            cfg.addAnnotatedClass(Personne.class);
            cfg.addAnnotatedClass(Homme.class);
            cfg.addAnnotatedClass(Femme.class);
            cfg.addAnnotatedClass(Mariage.class);

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
