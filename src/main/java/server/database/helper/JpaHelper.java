package server.database.helper;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Klissan on 14.05.2017.
 */
public class JpaHelper{
    private static EntityManagerFactory emf;
    static {
        emf = Persistence.createEntityManagerFactory("Server");
    }

    private static EntityManager create(){
        return emf.createEntityManager();
    }


    public <T> List<T> selectTransactional(Function<EntityManager, List<T>> selectAction) {
        List<T> ts;
        EntityManager manager = JpaHelper.create();
        manager.getTransaction().begin();
        ts = selectAction.apply(manager);
        manager.getTransaction().commit();
        return ts;
    }


    public void doTransactional(Function<EntityManager, ?> f) {
        EntityManager manager = JpaHelper.create();
        manager.getTransaction().begin();
        f.apply(manager);
        manager.getTransaction().commit();
    }
}
