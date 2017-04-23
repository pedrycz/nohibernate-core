package cz.pedry.nohibernate;

public interface NoHibernate {

    interface Type {
        String BASIC = "cz.pedry.nohibernate.types.NoHibernateBasicType";
    }

    interface Field {
        String CLASS = "class";
        String DATA = "data";
    }

}
