package server.database.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Created by xakep666 on 03.11.16.
 * <p>
 * Helper for Hibernate
 */
public class HibernateHelper implements DbHelper{
    private static final SessionFactory sessionFactory;
    private static final Logger log = LogManager.getLogger(HibernateHelper.class);

    static {
        sessionFactory = new Configuration().configure()
            .buildSessionFactory();

        log.info("Session factory configured.");
    }

    public HibernateHelper() {
    }

    private static Session createSession() {
        return sessionFactory.openSession();
    }

    /**
     * Transactional selection from database
     *
     * @param selectAction function will be executed in current session
     * @param <T>          object type to extract
     * @return list of extracted objects
     */
    public <T> List<T> selectTransactional(Function<Session, List<T>> selectAction) {
        Transaction txn = null;
        List<T> ts = Collections.emptyList();
        try (Session session = HibernateHelper.createSession()) {
            txn = session.beginTransaction();
            ts = selectAction.apply(session);
            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null) {
                txn.rollback();
            }
        }

        return ts;
    }

    /**
     * Execute transactional action in current session
     *
     * @param f action which will be executed
     */
    public void doTransactional(Function<Session, ?> f) {
        Transaction txn = null;
        try (Session session = HibernateHelper.createSession()) {
            txn = session.beginTransaction();
            f.apply(session);
            txn.commit();
        } catch (RuntimeException e) {
            log.error("Transaction failed.", e);
            if (txn != null) {
                txn.rollback();
            }
        }
    }
}
