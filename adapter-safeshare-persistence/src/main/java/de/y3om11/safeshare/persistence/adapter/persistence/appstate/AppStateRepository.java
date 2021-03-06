package de.y3om11.safeshare.persistence.adapter.persistence.appstate;

import de.y3om11.safeshare.domain.gateway.repository.IAppStateRepository;
import de.y3om11.safeshare.domain.objects.appstate.AppState;
import jetbrains.exodus.core.crypto.MessageDigestUtil;
import jetbrains.exodus.entitystore.PersistentEntityStoreImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppStateRepository implements IAppStateRepository {

    private final Logger log = LoggerFactory.getLogger(AppStateRepository.class);
    @Autowired
    private PersistentEntityStoreImpl store;
    @Autowired
    private AppStateMapper appStateMapper;

    public void increment(final AppState appState){
        log.info("AppState increment: Height: " + appState.height + " Hash: " + appState.hash);
        store.executeInTransaction(tx -> Optional.ofNullable(tx.getAll(AppState.name).getFirst())
                .ifPresentOrElse(state -> {
                    final String newState = MessageDigestUtil.sha256(appState.hash + appState.height);
                    final Long newHeight = appState.height + 1L;
                    appStateMapper.update(tx, newState, newHeight);
                    log.info(String.format("Updating AppState from %s to %s", appState.hash, newState));
                }, () -> {
                    log.info("Not present... create");
                    appStateMapper.create(tx, appState);
                }));
    }

    public AppState getAppState(){
        return store.computeInReadonlyTransaction(tx -> appStateMapper.getOrDefault(tx));
    }
}
