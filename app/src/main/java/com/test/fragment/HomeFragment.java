package com.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.test.Constants;
import com.test.DataFetchListener;
import com.test.R;
import com.test.adapter.NewsListAdapter;
import com.test.handler.DataFetchTask;
import com.test.model.Response;
import com.test.utils.Utils;

public class HomeFragment extends Fragment{
    public static String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private boolean isLoading = false;
    private NewsListAdapter adapter;
    private Response response;
    private int lastVisibleItem, totalItemCount;
    private int visibleThreshold = 2;
    private TextView txtHeading;
    private boolean isSearch;

    public static HomeFragment newInstance(String tag, boolean isSearch){
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_search", isSearch);
        bundle.putString("val", tag);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        txtHeading = (TextView)view.findViewById(R.id.txtHeading);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = mLayoutManager.getItemCount();
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (isSearch && !isLoading && totalItemCount < lastVisibleItem + visibleThreshold &&(response.getProducts().size()%10 == 0)) {
                    isLoading = true;
                    fetchNewsFeed(Utils.getUrl(Constants.URL_SEARCH, getArguments().getString("val")) + "&page=" + response.getProducts().size()/10, true);
                }
            }
        });

        txtHeading.setText(getString(R.string.results));
        isSearch = getArguments().getBoolean("is_search");
        if(isSearch){
            fetchNewsFeed(Utils.getUrl(Constants.URL_SEARCH, getArguments().getString("val")) + "&page=0", true);
        }else {
            fetchNewsFeed(Utils.getUrl(Constants.URL_TOP_STORY, getArguments().getString("val")), false);
        }
        return view;
    }

    private void fetchNewsFeed(String url, boolean isSearch) {
        if(!Utils.isNetworkOnline(getActivity())){
            Toast.makeText(getActivity(), getString(R.string.no_network), Toast.LENGTH_LONG).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        DataFetchTask fetchTask = new DataFetchTask(getActivity(), isSearch, new DataFetchListener() {
            @Override
            public void doAfterFetchData(Response response) {
                isLoading = false;
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    txtHeading.setVisibility(View.GONE);
                    if (adapter == null) {
                        HomeFragment.this.response = response;
                        adapter = new NewsListAdapter(response.getProducts(), getActivity());
                        recyclerView.setAdapter(adapter);
                        if(response.getTotal() == 0){
                            txtHeading.setVisibility(View.VISIBLE);
                            txtHeading.setText(getString(R.string.no_result));
                        }
                    } else {
                        HomeFragment.this.response.getProducts().addAll(response.getProducts());
                        adapter.setArray(response.getProducts());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        fetchTask.execute(url);
    }
}
