package cz.pedry.nohibernate.types;

import cz.pedry.nohibernate.utils.NoHibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

public class NoHibernateBasicType implements UserType, ParameterizedType {

    private Class<?> classType = null;

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.LONGVARCHAR};
    }

    @Override
    public Class<?> returnedClass() {
        return classType;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        Object deserialized = NoHibernateUtils.customString2Object(rs.getString(names[0]));
        return deserialized;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        String serialized = NoHibernateUtils.object2CustomString(value);
        st.setString(index, serialized);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value != null) {
            String serialized = NoHibernateUtils.object2CustomString(value);
            Object deserialized = NoHibernateUtils.customString2Object(serialized);
            return deserialized;
        } else {
            return null;
        }
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }

    @Override
    public void setParameterValues(Properties properties) {
        try {
            classType = Class.forName(properties.getProperty("classType"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}