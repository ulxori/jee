package pl.edu.pg.eti.kask.lab.config;

import lombok.Getter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
@ApplicationScoped
public class Config {
    public static final Properties properties = new Properties();

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) throws IOException {
        load();
    }
    public void load() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("app.properties");
        properties.load(input);
    }
}
