package com.example.retrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.retrofitdemo.adapter.ByNameAdapter;
import com.example.retrofitdemo.api.Apis;
import com.example.retrofitdemo.bean.ByName;
import com.example.retrofitdemo.bean.DetailsBean;
import com.example.retrofitdemo.bean.EventBean;
import com.example.retrofitdemo.mvp.presenter.IPresenterImpl;
import com.example.retrofitdemo.mvp.view.IView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView {

    @BindView(R.id.home_ed)
    EditText homeEd;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.byName)
    RecyclerView byName;
    private IPresenterImpl iPresenter;
    private ByNameAdapter byNameAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        iPresenter = new IPresenterImpl(this);
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        byName.setLayoutManager(manager);
        byNameAdapter = new ByNameAdapter(this);
        byName.setAdapter(byNameAdapter);
    }


    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof ByName){
            final ByName byName= (ByName) data;
            byNameAdapter.setData(byName.getResult());
            byNameAdapter.result(new ByNameAdapter.Cicklistener() {
                @Override
                public void setonclicklisener(int index) {
                    int commodityId = byName.getResult().get(index).getCommodityId();
                    Displaydetails(commodityId);
                }
            });
        }
        if (data instanceof DetailsBean){
            EventBus.getDefault().postSticky(new EventBean("details",data));
            startActivity(new Intent(this, DetailsActivity.class));
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
                iPresenter.startRequestGet(Apis.URL_FIND_COMMODITY_BYKEYWORD_GET+ "?keyword=" +
                        homeEd.getText().toString() + "&page=" + "1" + "&count=5",null,ByName.class);
                break;
                default:
                    break;
        }
    }
}
