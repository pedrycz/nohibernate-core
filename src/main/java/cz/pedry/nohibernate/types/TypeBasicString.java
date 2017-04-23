package cz.pedry.nohibernate.types;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.pedry.nohibernate.utils.NoHibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class TypeBasicString implements UserType {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.LONGVARCHAR};
    }

    @Override
    public Class returnedClass() {
        return Object.class;
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
        Object deserialized = NoHibernateUtils.customString2Object(rs.getString(names[0]), objectMapper);
        return deserialized;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        String serialized = NoHibernateUtils.object2CustomString(value, objectMapper);
        st.setString(index, serialized);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value != null) {
            String serialized = NoHibernateUtils.object2CustomString(value, objectMapper);
            Object deserialized = NoHibernateUtils.customString2Object(serialized, objectMapper);
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

}
