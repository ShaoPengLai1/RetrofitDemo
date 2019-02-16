package com.example.retrofitdemo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitdemo.adapter.ByNameAdapter;
import com.example.retrofitdemo.api.Apis;
import com.example.retrofitdemo.bean.ByName;
import com.example.retrofitdemo.bean.DetailsBean;
import com.example.retrofitdemo.bean.EventBean;
import com.example.retrofitdemo.entity.ShopEntityDao;
import com.example.retrofitdemo.gen.DaoSession;
import com.example.retrofitdemo.gen.ShopEntityDaoDao;
import com.example.retrofitdemo.mvp.presenter.IPresenterImpl;
import com.example.retrofitdemo.mvp.view.IView;
import com.example.retrofitdemo.network.GreenDaoUtils;
import com.example.retrofitdemo.network.NetUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView {

    @BindView(R.id.home_ed)
    EditText homeEd;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.byName)
    RecyclerView byNameView;
    private IPresenterImpl iPresenter;
    private ByNameAdapter byNameAdapter;
    private ShopEntityDaoDao entityDaoDao;
    private List<ByName.ResultBean> result;
    private ByName mByName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        initView();
        //
    }

    private void initView() {

        iPresenter = new IPresenterImpl(this);
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        byNameView.setLayoutManager(manager);
        byNameAdapter = new ByNameAdapter(this);
        byNameView.setAdapter(byNameAdapter);
        entityDaoDao=GreenDaoUtils.getInsanner().getDaoSession().getShopEntityDaoDao();
        if(!NetUtil.hsaNetWork(MainActivity.this)){

        }
    }


    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof ByName){
            final ByName byNameBean = (ByName) data;
            result=byNameBean.getResult();

            byNameAdapter.setData(byNameBean.getResult());
            addDao(result);
            byNameAdapter.result(new ByNameAdapter.Cicklistener() {
                @Override
                public void setonclicklisener(int index) {
                    int commodityId = byNameBean.getResult().get(index).getCommodityId();
                    Displaydetails(commodityId);
                }
            });
        }
        if (data instanceof DetailsBean){
            EventBus.getDefault().postSticky(new EventBean("details",data));
            startActivity(new Intent(this, DetailsActivity.class));
        }

    }

    private void addDao(List<ByName.ResultBean> result) {
        for (int i=0;i<result.size();i++){

            ShopEntityDao shopEntityDao=new ShopEntityDao();
            shopEntityDao.setId((long) i);
            shopEntityDao.setName(result.get(i).getCommodityName());
            shopEntityDao.setUrl(result.get(i).getMasterPic());
            shopEntityDao.setPrice(result.get(i).getPrice()+"");
            shopEntityDao.setSayMo(result.get(i).getSaleNum()+"");
            entityDaoDao.insert(shopEntityDao);
        }
    }

    private void Displaydetails(int commodityId) {
        iPresenter.startRequestGet(Apis.URL_FIND_COMMODITY_DETAILS_BYID_GET+
                "?commodityId=" + commodityId,null,DetailsBean.class);
    }

    @Override
    public void getDataFail(String error) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }

    @OnClick(R.id.search)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                if (!NetUtil.hsaNetWork(MainActivity.this)){
                    Toast.makeText(MainActivity.this,"当前网络不可用",Toast.LENGTH_LONG).show();

                    List<ShopEntityDao> daoList = entityDaoDao.loadAll();
                    selectDao(daoList);
                    byNameAdapter.setData(mByName.getResult());

                }else {
                    iPresenter.startRequestGet(Apis.URL_FIND_COMMODITY_BYKEYWORD_GET+ "?keyword=" +
                            homeEd.getText().toString() + "&page=" + "1" + "&count=5",null,ByName.class);
                }

                break;
                default:
                    break;
        }
    }

    private void selectDao(List<ShopEntityDao> daoList) {
        mByName = new ByName();

        for (int i = 0; i < daoList.size(); i++) {
            ByName.ResultBean resultBean=new ByName.ResultBean();
            resultBean.setCommodityName(daoList.get(i).getName());
            resultBean.setMasterPic(daoList.get(i).getUrl());
            resultBean.setPrice(Integer.valueOf(daoList.get(i).getPrice()));
            resultBean.setSaleNum(Integer.valueOf(daoList.get(i).getSayMo()));
            System.out.println("---------hasee---------");
            mByName.setResult(resultBean);
        }

    }
}
