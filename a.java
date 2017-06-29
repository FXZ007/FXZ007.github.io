package com.study.wnw.recyclerviewrefresh;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        //��ӷָ���
        mRecyclerView.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));

        //ΪRecyclerView����Adapter
        mRecyclerView.setAdapter(mAdapter);

        //����SwipeRefreshLayout.OnRefreshListener
        mRefreshLayout.setOnRefreshListener(this);

        /**
         * ����addOnScrollListener����������½����ǵ�EndLessOnScrollListener
         * ��onLoadMore������ȥ����������صĲ���
         * */
        mRecyclerView.addOnScrollListener(new EndLessOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMoreData();
            }
        });

    }

    //��ʼ������
    private void initView(){
        mRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.layout_swipe_refresh);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        mAdapter = new MyAdapter(this,mData);

        //���������ˢ�³��ֵ��Ǹ�ȦȦҪ��ʾ����ɫ
        mRefreshLayout.setColorSchemeResources(
                R.color.colorRed,
                R.color.colorYellow,
                R.color.colorGreen
        );
    }

    //��ʼ��һ��ʼ���ص�����
    private void initData(){
        mData = new ArrayList<String>();
        for (int i = 0; i < 20; i++){
            mData.add("Item"+i);
        }
    }

    //ÿ���������ص�ʱ�򣬾ͼ���ʮ�����ݵ�RecyclerView��
    private void loadMoreData(){
        for (int i =0; i < 10; i++){
            mData.add("�٣����ǡ��������ء���������"+i);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * ��дSwipeRefreshLayout.OnRefreshListener��OnRefresh����
     * ��������ȥ������ˢ�µĲ���
    */
    @Override
    public void onRefresh() {
        updateData();
        //�������¼�����ɺ���ʾ���ݷ����ı䣬�����������ڲ���ˢ��
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    private void updateData(){
        //����List��ǰ�����һ������
        mData.add(0, "�٣����ǡ�����ˢ�¡���������");
    }
}
