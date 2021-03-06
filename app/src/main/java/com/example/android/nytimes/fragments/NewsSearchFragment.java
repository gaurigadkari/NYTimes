package com.example.android.nytimes.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.nytimes.R;
import com.example.android.nytimes.adapters.ArticleAdapter;
import com.example.android.nytimes.databinding.FragmentNewsSearchBinding;
import com.example.android.nytimes.models.Article;
import com.example.android.nytimes.models.ResponseBody;
import com.example.android.nytimes.network.RetrofitClient;
import com.example.android.nytimes.utils.EndlessRecyclerViewScrollListener;
import com.example.android.nytimes.utils.SharedPref;
import com.example.android.nytimes.utils.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsSearchFragment extends Fragment implements FilterFragment.FilterSubmit {
    private FragmentNewsSearchBinding binding;
    private String searchQuery;
    private Context context;
    private ArrayList<Article> articles;
    private ArticleAdapter adapter;
    private RecyclerView newsRecyclerView;
    private EndlessRecyclerViewScrollListener scrollListener;
    private StaggeredGridLayoutManager listGridLayoutManager;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private final int GRID_LAYOUT = 0, LIST_LAYOUT = 1;
    private int currentLayoutType = GRID_LAYOUT;
    private final String FRAGMENT_FILTER_TAG = "Filters";

    public NewsSearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Indicating the fragment will populate its own menu
        setHasOptionsMenu(true);
        // Indicating the fragment will retain itself on config changes even if activity is destroyed
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Verifying if a saved instance exists before inflating the fragment
        if (savedInstanceState == null) {
            // Inflate the layout for this fragment
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_search, container, false);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Verifying if a saved instance exists before intializing views and class level variables
        if (savedInstanceState == null) {
            toolbar = binding.toolbar;
            newsRecyclerView = binding.newsList;
            progressBar = binding.progressBar;
            articles = new ArrayList<>();
            setLayoutManager(GRID_LAYOUT);

            newsRecyclerView.addOnScrollListener(scrollListener);
            if (Utilities.isNetworkAvailable(context) && Utilities.isOnline()) {
                retroNetworkCall("", 0);
            } else {
                Snackbar.make(newsRecyclerView, R.string.device_offline, Snackbar.LENGTH_LONG).show();
            }

        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        adapter = new ArticleAdapter(context, articles);
        newsRecyclerView.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);
    }

    private void setLayoutManager(int layoutType) {
        if (layoutType == GRID_LAYOUT) {
            listGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        } else if (layoutType == LIST_LAYOUT) {
            listGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        }
        newsRecyclerView.setLayoutManager(listGridLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(listGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);
            }
        };
    }

    private void loadNextDataFromApi(int page) {
        if (Utilities.isNetworkAvailable(context) && Utilities.isOnline()) {
            retroNetworkCall(searchQuery, page);
        } else {
            Snackbar.make(newsRecyclerView, R.string.device_offline, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                search(query);
                searchView.clearFocus();
                searchView.setQuery("", false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                FragmentManager fm = getChildFragmentManager();
                FilterFragment filterFragment = new FilterFragment();
                filterFragment.show(fm, FRAGMENT_FILTER_TAG);
                break;
            case R.id.action_list:
                if (currentLayoutType == GRID_LAYOUT) {
                    setLayoutManager(LIST_LAYOUT);
                    item.setIcon(R.drawable.ic_view_module_white_48px);
                    currentLayoutType = LIST_LAYOUT;
                } else if (currentLayoutType == LIST_LAYOUT) {
                    setLayoutManager(GRID_LAYOUT);
                    item.setIcon(R.drawable.ic_list_white_48px);
                    currentLayoutType = GRID_LAYOUT;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void search(String query) {
        if (query.length() == 0) {
            Snackbar.make(newsRecyclerView, R.string.empty_search, Toast.LENGTH_LONG).show();
        }
        articles.clear();
        if (Utilities.isNetworkAvailable(context) && Utilities.isOnline()) {
            retroNetworkCall(query, 0);
        } else {
            Snackbar.make(newsRecyclerView, R.string.device_offline, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Changing the activity reference for the retained fragment as the activity is getting recreated
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = getActivity();
    }

    /**
     * Clearing activity's old reference
     */
    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    public void retroNetworkCall(String query, final int page) {
        Context context = getActivity();
        Boolean newsDeskBoolean = SharedPref.getBoolean(context, SharedPref.NEWS_DESK_BOOLEAN);
        Boolean art = SharedPref.getBoolean(context, SharedPref.ART);
        Boolean fashion = SharedPref.getBoolean(context, SharedPref.FASHION);
        Boolean sports = SharedPref.getBoolean(context, SharedPref.SPORTS);
        Boolean education = SharedPref.getBoolean(context, SharedPref.EDUCATION);
        Boolean health = SharedPref.getBoolean(context, SharedPref.HEALTH);
        String newsDesk = SharedPref.createNewsDeskString(art, fashion, sports, education, health);
        Map<String, String> params = new HashMap<>();
        if (newsDeskBoolean) {
            params.put(RetrofitClient.NEWSDESK_PARAMETER, newsDesk);
        }
        params.put(RetrofitClient.API_KEY, RetrofitClient.API_KEY_VALUE);
        params.put(RetrofitClient.PAGINATION_PARAMETER, page + "");
        if (!query.equals("")) {
            params.put(RetrofitClient.QUERY_PARAMETER, query);
        } else {
            searchQuery = "";
        }

        Call<ResponseBody> call = RetrofitClient.getInstance().getApiInterface().getSearchResultsWithFilter(params);
        showProgressBar();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgressBar();
                if (response.code() == RetrofitClient.ERROR_CODE_TOO_MANY_REQUESTS) {
                    Snackbar.make(newsRecyclerView, R.string.error_429, Snackbar.LENGTH_LONG).show();
                } else if (response.body() != null) {

                    if (response.body().getStatus().equals(RetrofitClient.OK_STATUS)) {
                        List<Article> responseArticles = response.body().getResponse().getArticles();
                        if (responseArticles.size() == 0 && page == 0) {
                            Snackbar.make(newsRecyclerView, R.string.no_results, Snackbar.LENGTH_LONG).show();
                        } else if (responseArticles.size() != 0) {
                            articles.addAll(responseArticles);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgressBar();
            }
        });
    }


    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSubmit() {
        articles.clear();
        retroNetworkCall("", 0);
    }

}
