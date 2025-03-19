package weblab.webproject4.repositories;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import weblab.webproject4.entities.Entry;
import weblab.webproject4.entities.User;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findByUser(User user);

    @Transactional
    long deleteByUser(User user);
}
