package company;
import java.util.Set;
import javax.ws.rs.core.Application;

// start path: 'localhost:8080/resources'
@javax.ws.rs.ApplicationPath("resources")

public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return getRestResourceClasses();
    }

    private Set<Class<?>> getRestResourceClasses() {

        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        resources.add(company.CompanyServices.class);

        return resources;
    }
}
