package util;

import classes.Categorie;
import classes.Commande;
import classes.LigneCommandeProduit;
import classes.Produit;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // charge application.properties
            Properties props = new Properties();
            try (InputStream in = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("application.properties")) {
                if (in != null) props.load(in);
            }

            Configuration cfg = new Configuration();
            // enregistre les entités
            cfg.addAnnotatedClass(Categorie.class);
            cfg.addAnnotatedClass(Produit.class);
            cfg.addAnnotatedClass(Commande.class);
            cfg.addAnnotatedClass(LigneCommandeProduit.class);

            // copie toutes les propriétés
            props.forEach((k, v) -> cfg.setProperty(k.toString(), v.toString()));

            return cfg.buildSessionFactory();
        } catch (Exception ex) {
            throw new RuntimeException("Erreur init SessionFactory", ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}