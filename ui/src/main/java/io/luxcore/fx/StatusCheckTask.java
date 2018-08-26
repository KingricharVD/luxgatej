package io.luxcore.fx;


import com.adr.fonticon.Icon;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.luxcore.LuxgateInstance;
import io.luxcore.LuxgateService;
import io.luxcore.dto.rs.StatusResponse;
import io.luxcore.events.LuxgateStatusEvent;
import io.luxcore.events.ShutdownEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
public class StatusCheckTask {
    final private static Logger logger = LoggerFactory.getLogger(StatusCheckTask.class);

    @Inject
    private EventBus eventBus;
    @Inject
    private LuxgateInstance instance;
    @Inject
    private LuxgateService service;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public StatusCheckTask() {
    }

    public void start() {
        eventBus.register(this);
        Runnable statusCheck = () -> {
            try {
                StatusResponse status = service.status(instance);
                logger.info("Status: {}", status);
                boolean statusIsGood = "success".equals(status.getResult());
                eventBus.post(new LuxgateStatusEvent(statusIsGood));
            } catch (Exception e) {
                eventBus.post(new LuxgateStatusEvent(false));
                logger.error("Error during status check", e);
            }
        };
        scheduler.scheduleAtFixedRate(statusCheck, 0L, 5L, TimeUnit.SECONDS);
    }

    @Subscribe
    public void stop(ShutdownEvent event) {
        logger.info("Shutting down");
        scheduler.shutdown();
    }

}
