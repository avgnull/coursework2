package hibernateControllers;

import model.Cargo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.List;

public class HibernateController {
    private EntityManagerFactory emf;

    public HibernateController(EntityManagerFactory emf){
        this.emf=emf;
    }

    private EntityManager getEntityManager(){
        return this.emf.createEntityManager();
    }

    public <T> void create(T entity){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public <T> void update(T entity){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public <T> List getAllRecords(Class<T> entity){  //getAllRecords(Truck.class)
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(entity));
            Query q =em.createQuery(query);
            return (q.getResultList());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(em!=null){
                em.close();
            }
        }
        return null;
    }

    public <T> T getEntityById(Class<T> entity, int id){
        EntityManager em = getEntityManager();
        T result = null;
        try {
            em.getTransaction().begin();
            result = em.find(entity, id);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(em!=null){
                em.close();
            }
        }
        return (result);
    }

    public <T> void delete(T entity){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            //System.out.println(em.contains(entity));
            //em.refresh(entity); // refresh the entity with the latest information from the database
            //T toDelete = (T) em.find(entity.getClass(), ((Cargo)entity).getId());
            em.remove(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(em!=null){
                em.close();
            }
        }
    }

    public <T> void deleteById(Class<T> entityClass, int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            int deletedCount = em.createQuery("DELETE FROM " + entityClass.getName() + " e WHERE e.id = :id").setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            System.out.println("Number of records deleted: " + deletedCount);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /*
    public <T, R> R getColumnValue(Class<T> entityClass, String columnName, int id) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<R> query = cb.createQuery((Class<R>) Object.class);
            Root<T> root = query.from(entityClass);
            query.select(root.get(columnName));
            query.where(cb.equal(root.get("id"), id));
            TypedQuery<R> typedQuery = em.createQuery(query);
            return typedQuery.getSingleResult();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
     */

    private <T> Class<?> getColumnJavaType(Class<T> entityClass, String columnName) {
        try {
            Field field = entityClass.getDeclaredField(columnName);
            return field.getType();
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Invalid column name: " + columnName, e);
        }
    }

/*
    public String getUserType(int id) {
        EntityManager em = getEntityManager();
        String result = null;
        try {
            Query query = em.createQuery("SELECT u.DTYPE FROM user u WHERE u.id = :id");
            query.setParameter("id", id);
            result = (String) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }
 */

    public <T> T getColumnValue(Class<T> clazz, String columnName, int id) {
        EntityManager em = getEntityManager();
        T result = null;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(clazz);
            Root<T> root = query.from(clazz);
            query.select(root.get(columnName));
            query.where(cb.equal(root.get("id"), id));
            TypedQuery<T> typedQuery = em.createQuery(query);
            result = typedQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return result;
    }
}
