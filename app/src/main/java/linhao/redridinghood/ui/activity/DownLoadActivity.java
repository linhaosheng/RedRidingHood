package linhao.redridinghood.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import linhao.redridinghood.R;
import linhao.redridinghood.application.MyApplication;

/**
 * Created by linhao on 2016/9/18.
 */
public class DownLoadActivity extends BaseActivty {

    @BindView(R.id.webview)
    WebView webView;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.webview;
    }

    @Override
    protected void afterCreate(Bundle bundle) {
        url = getIntent().getStringExtra("url");
         webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.loadUrl(url);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }

}
