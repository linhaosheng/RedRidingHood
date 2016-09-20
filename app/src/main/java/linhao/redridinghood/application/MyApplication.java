package linhao.redridinghood.application;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import linhao.redridinghood.inject.component.ClientApiComponent;
import linhao.redridinghood.inject.component.DaggerClientApiComponent;
import linhao.redridinghood.inject.module.ClientApiModule;

/**
 * Created by linhao on 2016/8/17.
 */
public class MyApplication extends Application {

    private static ClientApiComponent clientApiComponent;
    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        clientApiComponent= DaggerClientApiComponent
                .builder()
                .clientApiModule(new ClientApiModule())
                .build();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher=LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context){
        MyApplication application = (MyApplication)context.getApplicationContext();
        return application.refWatcher;
    }

    public static ClientApiComponent Instance() {
        return clientApiComponent;
    }


}
