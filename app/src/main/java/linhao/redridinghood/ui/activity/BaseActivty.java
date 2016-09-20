package linhao.redridinghood.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by linhao on 2016/8/13.
 */
public abstract class BaseActivty extends AppCompatActivity {

    protected abstract int  getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        afterCreate(savedInstanceState);
    }

    protected abstract void afterCreate(Bundle bundle);


}
