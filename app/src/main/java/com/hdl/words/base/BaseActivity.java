package com.hdl.words.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by HDL on 2018/1/15.
 */

public abstract class BaseActivity extends SupportActivity implements ISupportActivity,View.OnClickListener {
    protected Context context;
   // protected int theme;
    /** 是否沉浸状态栏 **/
    private boolean isSetStatusBar;
    /** 是否允许全屏 **/
    private boolean mAllowFullScreen = false;
    /** 是否禁止旋转屏幕 **/
    private boolean isAllowScreenRoate = false;
    //上个activity传过来的参数
    protected Bundle bundle;
    /** 当前Activity渲染的视图View **/
    protected View mContextView ;
    /** 日志输出标志 **/
    protected final String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "BaseActivity-->onCreate()");
        if(mContextView==null){
            mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
            bundle = getIntent().getExtras();
            initParms(bundle);
            //loadTheme();
            loadConfigure();
            ButterKnife.bind(this);
            initTopBar();
            initData();
            context=this;
            ActivityCollector.addActivity(this);
        }else{

        }

    }

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//             //透明状态栏
//            getWindow().addFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 透明导航栏
//            getWindow().addFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        QMUIStatusBarHelper.translucent(this);
    }

    /**
     * [初始化参数]
     *
     * @param parms
     */
    public abstract void initParms(Bundle parms);

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [加载数据]
     */
    public abstract void initData();
    protected abstract void initTopBar();
    @Override
    public void onClick(View view) {
    }
    protected void loadConfigure(){
        if (mAllowFullScreen) {
            QMUIDisplayHelper.setFullScreen(this);
        }
        isSetStatusBar=false;
        if (isSetStatusBar) {
            steepStatusBar();
        }
        setContentView(mContextView);
        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
//    protected void loadTheme(){
//        theme= MySession.getTheme(this);
//        switch (theme){
//            case 1:
//                setTheme(R.style.AppTheme1);
//                break;
//            case 2:
//                setTheme(R.style.AppTheme2);
//                break;
//            case 3:
//                setTheme(R.style.AppTheme3);
//                break;
//            case 4:
//                setTheme(R.style.AppTheme4);
//                break;
//            default:
//                break;
//        }
//    }
//    public int getThemeColor(){
//        TypedValue typedValue = new TypedValue();
//        getTheme().resolveAttribute(R.attr.colorPrimary,typedValue,true);
//        return typedValue.data;
//    }
    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this,clz));
    }
    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    public void startActivityAndCloseThis(Class<?> clz){
        startActivity(clz);
        this.finish();
    }
    public void startActivityAndCloseThis(Class<?> clz, Bundle bundle) {
        startActivity(clz, bundle);
        this.finish();
    }
    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.w(TAG, "onRestart()");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "onStart()");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "onResume()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "onStop()");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        Log.w(TAG, "onDestroy()");

    }
    //若为栈顶，最小化应用,否则杀死该Activity
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            //if(this==task.topActivity.getPackageName())
//            if(getTopFragment() instanceof MainFragment||getTopFragment()instanceof LoginFragment){
//                moveTaskToBack(true);
//                return true;//return true;拦截事件传递,从而屏蔽back键。
//            }else{
//
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }



    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    /**
     * [是否允许全屏]
     *
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * [是否设置沉浸状态栏]
     *
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }


}
