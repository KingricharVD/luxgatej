package io.luxcore.di;

import com.google.api.client.http.GenericUrl;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import io.luxcore.LuxgateHttpService;
import io.luxcore.LuxgateInstance;
import io.luxcore.LuxgateService;
import io.luxcore.fx.StatusCheckTask;

/**
 * Main module for Guice
 */
public class MainModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(LuxgateService.class).toInstance(new LuxgateHttpService());
        LuxgateInstance instance = new LuxgateInstance();
        instance.setUrl(new GenericUrl("http://127.0.0.1:9883"));
        bind(LuxgateInstance.class).toInstance(instance);
        bind(EventBus.class).asEagerSingleton();
        bind(StatusCheckTask.class).asEagerSingleton();
    }
}
