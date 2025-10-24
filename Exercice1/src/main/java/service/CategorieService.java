package service;

import classes.Categorie;
import dao.AbstractDao;

public class CategorieService extends AbstractDao<Categorie> {
    public CategorieService() { super(Categorie.class); }
}
