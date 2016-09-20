package linhao.redridinghood.inject.module;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import linhao.redridinghood.ui.fragment.HotNewFirstFragemnt;
import linhao.redridinghood.ui.fragment.HotNewFourFragemnt;
import linhao.redridinghood.ui.fragment.HotNewSecondFragemnt;
import linhao.redridinghood.ui.fragment.HotNewThridFragemnt;

/**
 * Created by linhao on 2016/9/10.
 */
@Module
public class ClientFragmentModule {


    private FragmentActivity activity;
    public ClientFragmentModule(FragmentActivity activity){
        this.activity=activity;
    }
 /**
    @Provides
    @Singleton
    public Fragment providerHotNewFirstFragment(){
        return new HotNewFirstFragemnt();
    }

    @Provides
    @Singleton
    public Fragment providerHotNewSecondFragment(){
        return new HotNewSecondFragemnt();
    }

    @Provides
    @Singleton
    public Fragment providerHotNewThridFragment(){
        return new HotNewThridFragemnt();
    }

    @Provides
    @Singleton
    public Fragment providerHotNewFourFragment(){
        return new HotNewFourFragemnt();
    }

  /**
    @Provides
    @Singleton
    public FragmentTransaction providerFragmentTransaction(){
        return activity.getSupportFragmentManager().beginTransaction();
    }
  */
}
