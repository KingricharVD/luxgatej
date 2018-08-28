package io.luxcore.fx;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.luxcore.LuxgateService;
import io.luxcore.dto.rs.StatusResponse;
import io.luxcore.events.LuxgateStatusEvent;
import io.luxcore.events.ShutdownEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
public class StatusCheckTask {
    final private static Logger logger = LoggerFactory.getLogger(StatusCheckTask.class);

    @Inject
    private EventBus eventBus;
    @Inject
    private LuxgateService service;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public StatusCheckTask() {
    }

    public void start() {
        eventBus.register(this);
        Runnable statusCheck = () -> {
            try {
                StatusResponse status = service.status();
                logger.info("Status: {}", status);
                boolean statusIsGood = "success".equals(status.getResult());
                eventBus.post(new LuxgateStatusEvent(statusIsGood));
            } catch (Exception e) {
                if (e.getCause() instanceof ConnectException) {
                    logger.info("Status: {}", e.getMessage());
                } else {
                    logger.error("Error during status check", e);
                }
                eventBus.post(new LuxgateStatusEvent(false));
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
