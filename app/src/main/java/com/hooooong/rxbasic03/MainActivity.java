package com.hooooong.rxbasic03;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;

    public List<String> dataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setAdapter();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setAdapter() {
        customAdapter = new CustomAdapter();
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Publish Subject 생성
    PublishSubject<String> publishSubject = PublishSubject.create();

    // Publish Subject
    // 구독 시점부터 데이터를 읽을 수 있다.
    // 이전에 발행된 아이템은 읽을 수 없다.
    public void doPublish(View view) {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                publishSubject.onNext("SomeThings : " + i);
                Log.e(TAG, "doPublish: "+ i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getPublish(View view) {
        dataList.clear();
        publishSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        str -> {

                            dataList.add(str);
                            customAdapter.setDataAndRefresh(dataList);
                        });
    }

    // Behavior Subject 생성

    BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();

    // Behavior Subject
    // 구독 이전에 발행된 마지막 발행된 아이템부터 구독할 수 있다.
    public void doBehavior(View view) {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                behaviorSubject.onNext("SomeThings : " + i);
                Log.e(TAG, "doBehavior: "+ i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getBehavior(View view) {
        dataList.clear();
        behaviorSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        str -> {
                            dataList.add(str);
                            customAdapter.setDataAndRefresh(dataList);
                        });
    }

    // Replay Subject 생성
    ReplaySubject<String> replaySubject = ReplaySubject.create();

    public void doReplay(View view) {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                Observable<String> observable = Observable.create(str -> str.onNext("뿌잉"));
                observable.subscribe(str -> replaySubject.onNext(str));
                replaySubject.onNext("SomeThings : " + i);
                Log.e(TAG, "doReplay: "+ i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //구독(subscribe)을 한 시점에 상관없이 모든 데이터를 읽을 수 있다.
    public void getReplay(View view) {
        dataList.clear();
        replaySubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        str -> {
                            dataList.add(str);
                            customAdapter.setDataAndRefresh(dataList);
                        });
    }

    // Async Subject 생성
    AsyncSubject<String> asyncSubject = AsyncSubject.create();

    // 구독을 하면 마지막 아이템을 구독하고 observable 의 동작이 완료된 후에야 동작한다.
    public void doAsync(View view) {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                asyncSubject.onNext("Something : " + i);
                Log.e(TAG, "doAsync: "+ i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            asyncSubject.onComplete();
        }).start();
    }

    public void getAsync(View view) {
        dataList.clear();
        asyncSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        str -> {
                            dataList.add(str);
                            customAdapter.setDataAndRefresh(dataList);
                        });
    }
}

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    List<String> data = new ArrayList<>();

    public void setDataAndRefresh(List<String> dataAndNotify) {
        this.data = dataAndNotify;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text1.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView text1;

        public ViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
        }
    }
}

