package server.database.helper;

import org.hibernate.Session;

import java.util.List;
import java.util.function.Function;

/**
 * Created by Klissan on 14.05.2017.
 */
public interface DbHelper {

    /**
     * Transactional selection from database
     *
     * @param selectAction function will be executed in current session
     * @param <T>          object type to extract
     * @return list of extracted objects
     */
    <T> List<T> selectTransactional(Function<Session, List<T>> selectAction);

    /**
     * Execute transactional action in current session
     *
     * @param f action which will be executed
     */
    void doTransactional(Function<Session, ?> f);
}
