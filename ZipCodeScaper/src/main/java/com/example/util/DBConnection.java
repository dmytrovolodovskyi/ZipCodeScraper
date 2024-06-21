package com.example.util;

import com.example.model.entity.Address;
import com.example.model.entity.Country;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class DBConnection implements AutoCloseable {
    private static final DBConnection INSTANCE;

    private final SessionFactory sessionFactory;

    static {
        INSTANCE = new DBConnection();
    }

    private DBConnection() {
        Configuration configuration = new Configuration();

        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("hibernate.properties")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("hibernate.properties not found");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load hibernate.properties", e);
        }

        configuration.addProperties(properties)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Address.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static DBConnection getInstance() {
        return INSTANCE;
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}

