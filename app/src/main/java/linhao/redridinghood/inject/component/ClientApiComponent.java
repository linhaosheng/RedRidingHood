package linhao.redridinghood.inject.component;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import linhao.redridinghood.inject.module.ClientApiModule;
import linhao.redridinghood.model.api.ApiClient;
import linhao.redridinghood.model.service.ClientApiManager;

/**
 * Created by linhao on 2016/8/14.
 */
@Singleton
@Component(modules = ClientApiModule.class)
public interface ClientApiComponent {

    void inject(ClientApiManager clientApiManager);
    Gson getGson();
    ApiClient getApiClient();
}
