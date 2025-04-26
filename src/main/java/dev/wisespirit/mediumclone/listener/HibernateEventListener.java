package dev.wisespirit.mediumclone.listener;

import dev.wisespirit.mediumclone.model.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.DeleteContext;
import org.hibernate.event.spi.DeleteEvent;
import org.hibernate.event.spi.DeleteEventListener;
import org.hibernate.event.spi.EventType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HibernateEventListener implements DeleteEventListener {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public void onDelete(DeleteEvent event) throws HibernateException {
        Object entity = event.getObject();
        if (entity instanceof User user) {
            //TODO handle logic...
        }

    }

    @Override
    public void onDelete(DeleteEvent event, DeleteContext transientEntities) throws HibernateException {
        Object entity = event.getObject();
        if (entity instanceof User user) {
            //TODO handle logic...
        }

    }

    @PostConstruct
    private void postConstruct() {
        SessionFactoryImplementor sessionFactory = entityManagerFactory.unwrap(SessionFactoryImplementor.class);
        EventListenerRegistry registry = sessionFactory
                .getServiceRegistry()
                .getService(EventListenerRegistry.class);
        assert registry != null;
        registry.prependListeners(EventType.DELETE, this);
    }
}