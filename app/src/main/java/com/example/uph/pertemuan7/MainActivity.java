package com.example.uph.pertemuan7;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.example.uph.pertemuan7.api.Model.Project;
import com.example.uph.pertemuan7.api.Model.Repository;
import com.example.uph.pertemuan7.api.TestingAPI;
import com.example.uph.pertemuan7.api.Services.TestingService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private LoadMoreListView lvItem;
    private SwipeRefreshLayout swipeMain;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    private int maxPage = 0;
    private int currentPage = 1;
    private int nPerPage = 18;

    private TestingService testingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItem = (LoadMoreListView)findViewById(R.id.lv_item);
        swipeMain = (SwipeRefreshLayout)findViewById(R.id.swipe_main);
        list = new ArrayList<>();
        testingService = TestingAPI.getClient().create(TestingService.class);

        populateData();

        swipeMain.setOnRefreshListener(this);
    }

    private void populateData(){
//        Load 18 item pertama di halaman 1
        Call<Repository> callRepo = testingService.repositories("tetris",
                "starts", "desc", currentPage, nPerPage);
        callRepo.enqueue(new Callback<Repository>() {
            @Override
            public void onResponse(Call<Repository> call, Response<Repository> response) {
                Repository repository = response.body();
                maxPage = repository.getTotalCount() / nPerPage;
                System.out.println(repository);

                if(repository.getTotalCount()<=0) {
                    Intent intent = new Intent(MainActivity.this, Empty.class);
                    startActivity(intent);
                }

                for (Project item:
                     repository.getItems()) {
                    list.add(item.getFullName());
                }
                System.out.println(list);
                adapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1, list);
                lvItem.setAdapter(adapter);

                lvItem.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        if (currentPage < maxPage) {
                            loadMore();
                        } else {
                            lvItem.onLoadMoreComplete();
                        }
                    }
                });
                currentPage++;
            }
            @Override
            public void onFailure(Call<Repository> call, Throwable t) {
                Intent intent = new Intent(MainActivity.this, Error_Page.class);
                intent.putExtra("ERROR_INFO",t.toString());
                startActivity(intent);
                System.out.println("failure : "+t);
            }
        });
    }

    public void loadMore() {
        Call<Repository> callRepo = testingService.repositories("tetris",
                "starts", "desc", currentPage, nPerPage);
        callRepo.enqueue(new Callback<Repository>() {
            @Override
            public void onResponse(Call<Repository> call, Response<Repository> response) {
                Repository repository;
                repository = response.body();
                if (response.body() != null) {
                    for (Project item :
                            repository.getItems()) {
                        list.add(item.getFullName());
                    }

                    adapter.notifyDataSetChanged();
                    lvItem.onLoadMoreComplete();
//                    menujukkan posisi listview sekarang
                    System.out.println("list size : "+list.size());
                    System.out.println("repo size : "+repository.getItems().size());
                    lvItem.setSelection(list.size() - repository.getItems().size());
                    currentPage++;
                } else {
                    maxPage = currentPage;
                    lvItem.onLoadMoreComplete();
                }
            }

            @Override
            public void onFailure(Call<Repository> call, Throwable t) {
                lvItem.onLoadMoreComplete();
            }
        });
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        list.clear();
        Call<Repository> callRepo = testingService.repositories("tetris",
                "starts", "desc", currentPage, nPerPage);
        callRepo.enqueue(new Callback<Repository>() {
            @Override
            public void onResponse(Call<Repository> call, Response<Repository> response) {
                Repository repository;
                repository = response.body();
                if (response.body() != null) {
                    for (Project item :
                            repository.getItems()) {
                        list.add(item.getFullName());
                    }

                    adapter.notifyDataSetChanged();
                    swipeMain.setRefreshing(false);
                    lvItem.setSelection(0);
                    currentPage++;
                } else {
                    maxPage = currentPage;
                    swipeMain.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Repository> call, Throwable t) {
                swipeMain.setRefreshing(false);
            }
        });
    }
}