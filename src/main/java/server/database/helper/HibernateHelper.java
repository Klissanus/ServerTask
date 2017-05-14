package server.database.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Helper for Hibernate
 */
public class HibernateHelper {
    private static final SessionFactory sessionFactory;
    private static final StandardServiceRegistry registry;
    private static final Logger log = LogManager.getLogger(HibernateHelper.class);

    //Hibernate initialize
    static {
        registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
        sessionFactory = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

        log.info("Session factory configured.");
    }

    private HibernateHelper() {
    }

    private static Session createSession() {
        return sessionFactory.openSession();
    }

    /**
     * Transactional selection from database
     *
     * @param selectAction
     *     function will be executed in current session
     * @param <T>
     *     object type to extract
     *
     * @return list of extracted objects
     */
    public static <T> List<T> selectTransactional(Function<Session, List<T>> selectAction) {
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
     * @param f
     *     action which will be executed
     */
    public static void doTransactional(Function<Session, ?> f) {
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
