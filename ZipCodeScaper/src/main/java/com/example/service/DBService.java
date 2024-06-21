package com.example.service;

import com.example.util.DBConnection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBService {
    public static final Logger LOGGER = Logger.getLogger(DBService.class.getName());

    public void pushData(List<?> entities) {
        try (Session session = DBConnection.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            LOGGER.log(Level.INFO, "Found {0} rows! Pushing to the database...", entities.size());

            for (Object entity : entities) {
                session.persist(entity);
            }

            transaction.commit();
            LOGGER.log(Level.INFO, "Values were pushed successfully!");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error pushing data to database");
//            throw new RuntimeException("Error pushing data to database", e);
        }
    }
}
