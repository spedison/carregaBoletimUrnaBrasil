package br.com.spedison.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

@Data
public class ConexaoJPA implements Closeable {

    private EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    private static final Logger logger = LoggerFactory.getLogger(ConexaoJPA.class);

    public ConexaoJPA() {
        entityManager = entityManagerFactory.createEntityManager();
        logger.info("Conexoes com o banco initializadas");
    }

    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    public void commitTransaction() {
        entityManager.getTransaction().commit();
        logger.info("Transacao commitada.");
    }

    public void executaSQLNativeUpdate(String sql){
        beginTransaction();
        entityManager.createNativeQuery(sql).executeUpdate();
        commitTransaction();
    }

    public void grava(Object obj) {
        entityManager.persist(obj);
    }

    public void delete(Object obj) {
        entityManager.remove(obj);
    }

    static public void terminaConexoes(){
        entityManagerFactory.close();
        logger.info("Gerenciador de entidades finalizado.");
    }

    @Override
    public void close() throws IOException {
        entityManager.close();
        logger.info("Conexoes com o banco finalizadas.");
    }
}
