package weblab.webproject3;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Named;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("entries")
@ApplicationScoped
public class EntriesBean implements Serializable {
    private static final String persistenceUnit = "StudsPU";

    private Entry entry;
    private List<Entry> entries;


    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext(unitName = "StudsPU")
    private EntityManager entityManager;
    private EntityTransaction transaction;


    public EntriesBean() {
        entry = new Entry();
        entries = new ArrayList<>();
        connect();
        loadEntries();
    }

    private void connect() {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
    }

    private void loadEntries() {
        try {
            transaction.begin();
            Query query = entityManager.createQuery("SELECT e FROM Entry e");
            entries = query.getResultList();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public String addEntry() {
        try {
            transaction.begin();
            entry.checkHit();
            entityManager.persist(entry);
            entries.add(entry);
            entry = new Entry();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
        return "redirect";
    }

    public String clearEntries() {
        try {
            transaction.begin();
            Query query = entityManager.createQuery("DELETE FROM Entry");
            query.executeUpdate();
            entries.clear();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
        return "redirect";
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
