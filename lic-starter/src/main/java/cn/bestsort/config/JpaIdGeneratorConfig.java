package cn.bestsort.config;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;


/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-04 09:02
 */
public class JpaIdGeneratorConfig extends IdentityGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) throws HibernateException {
        Serializable id = s.getEntityPersister(null, obj).getClassMetadata().getIdentifier(obj, s);
        // if obj's id not null, use obj's id
        if (id != null && Integer.parseInt(id.toString()) > 0) {
            return id;
        } else {
            //else used auto id
            return super.generate(s, obj);
        }
    }
}
