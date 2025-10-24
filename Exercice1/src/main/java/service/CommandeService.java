package service;

import classes.Commande;
import dao.AbstractDao;

public class CommandeService extends AbstractDao<Commande> {
    public CommandeService() { super(Commande.class); }
}
