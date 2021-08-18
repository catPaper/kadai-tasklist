package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * tasklistデータベースとやり取りをするDAOクラス
 * @author ryouta.osada
 *
 */
public class DBUtil {
    private static final String PERSISTENCE_UNIT_NAME = "tasklist";
    private static EntityManagerFactory emf;

    /**
     * 生成したEntityManagerFactoryからEntityManagerを生成し、返すメソッド
     * @return 生成されたEntityManager
     */
    public static EntityManager createEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    private static EntityManagerFactory getEntityManagerFactory() {
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return emf;
    }
}
