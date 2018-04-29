package entity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
 private static final SessionFactory sessionFactory; 
 static {
 try {
 // Create the SessionFactory from standard (hibernate.cfg.xml) 
 // config file
     // Configuration configuration=new Configuration();
Configuration configuration=new Configuration().configure();
StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
serviceRegistryBuilder.applySettings(configuration.getProperties());
//ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.build());
 } catch (Throwable ex) {
 // Log the exception. 
 System.err.println("Initial SessionFactory creation failed." + ex);
 throw new ExceptionInInitializerError(ex);
 }
 } 
 public static SessionFactory getSessionFactory() {
 return sessionFactory;
 }
}
