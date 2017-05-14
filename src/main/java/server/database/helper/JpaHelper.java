package server.database.helper;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Klissan on 14.05.2017.
 */
public class JpaHelper implements DbHelper {
    private static EntityManagerFactory emf;
    static {
        emf = Persistence.createEntityManagerFactory("PU");
    }

    private static EntityManager create(){
        return emf.createEntityManager();
    }
    @Override
    public <T> List<T> selectTransactional(Function<Session, List<T>> selectAction) {
        return null;
    }

    @Override
    public void doTransactional(Function<Session, ?> f) {

    }
}
