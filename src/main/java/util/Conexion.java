/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import javax.persistence.EntityManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conexion {
    
    private static Conexion instance;
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    private Conexion() {
        entityManagerFactory = Persistence.createEntityManagerFactory("my_persistence_unit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public static  Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void closeEntityManager() {
        entityManager.close();
        entityManagerFactory.close();
    }
    
    public void guardar(Object object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
    }
    
    public <T> T merge(T object) {
        entityManager.getTransaction().begin();
        T mergedObject = entityManager.merge(object);
        entityManager.getTransaction().commit();
        return mergedObject;
    }
    
    public void delete(Object object) {
        entityManager.getTransaction().begin();
        entityManager.remove(object);
        entityManager.getTransaction().commit();
    }
}