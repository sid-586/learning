import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class CreateSessionFactory {

    SessionFactory sessionFactory;

    public CreateSessionFactory(String resourceName) {

        this.sessionFactory = new MetadataSources(new StandardServiceRegistryBuilder()
                .configure(resourceName)
                .build())
                .getMetadataBuilder()
                .build().getSessionFactoryBuilder().build();
    }
}

