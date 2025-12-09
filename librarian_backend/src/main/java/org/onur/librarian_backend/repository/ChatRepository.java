package org.onur.librarian_backend.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.onur.librarian_backend.domain.Chat;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ChatRepository implements PanacheRepositoryBase<Chat, UUID> {
    public List<Chat> findByUserId(UUID ownerId) {
            return find("ownerId", ownerId).list();
    }
}
