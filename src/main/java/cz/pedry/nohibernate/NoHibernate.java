package cz.pedry.nohibernate;

/**
 * Most important string definitions used with NoHibernate framework
 */
public interface NoHibernate {

    interface Type {
        String BASIC = "cz.pedry.nohibernate.types.NoHibernateBasicType";
    }

    interface Field {
        String HEAD = "HEAD";
        String BODY = "BODY";

        interface Head {
            String JAVA_CLASS = "JAVA_CLASS";
        }
    }

}
