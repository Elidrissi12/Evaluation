package service;

import classes.EmployeTache;
import dao.AbstractDao;

public class EmployeTacheService extends AbstractDao<EmployeTache> {
    public EmployeTacheService() { super(EmployeTache.class); }
}
