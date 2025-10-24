package service;

import classes.LigneCommandeProduit;
import dao.AbstractDao;

public class LigneCommandeService extends AbstractDao<LigneCommandeProduit> {
    public LigneCommandeService() { super(LigneCommandeProduit.class); }
}
