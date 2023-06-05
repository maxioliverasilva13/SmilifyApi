/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import ENTITIES.CategoriaArancel;
import ENTITIES.Paciente;
import ENTITIES.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import util.EncryptService;

/**
 *
 * @author rodrigo
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;
    @PersistenceContext(unitName = "my_persistence_unit", type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.em = Persistence.createEntityManagerFactory("my_persistence_unit").createEntityManager();
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        
        getEntityManager().merge(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
     public CategoriaArancel findArancel(Long id) {
        return getEntityManager().find(CategoriaArancel.class, id);
    }
     
    public Paciente findPaciente(Long id) {
        return getEntityManager().find(Paciente.class, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
     
     
    
     public Usuario validarUsuario(String email,String password){
         javax.persistence.criteria.CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
         javax.persistence.criteria.CriteriaQuery query = builder.createQuery(Usuario.class);
         javax.persistence.criteria.Root<Usuario> root = query.from(Usuario.class);
         query.select(root).where(builder.equal(root.get("email"), email));
         Usuario user;
         try{
             user = (Usuario)getEntityManager().createQuery(query).getSingleResult();
         }catch(Exception e){
             return null;
         }
         String hashPass = EncryptService.encryptPass(password);
         if(!hashPass.equals(user.getPassword())){
             return null;
         }
         
         return user;
    }
     
    public Usuario getUserByEmail(String email){
         javax.persistence.criteria.CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
         javax.persistence.criteria.CriteriaQuery query = builder.createQuery(Usuario.class);
         javax.persistence.criteria.Root<Usuario> root = query.from(Usuario.class);
         query.select(root).where(builder.equal(root.get("email"), email));
         Usuario user;
         try{
             user = (Usuario)getEntityManager().createQuery(query).getSingleResult();
             return user;
         }catch(Exception e){
             return null;
         }
   
    }
    
     
}
