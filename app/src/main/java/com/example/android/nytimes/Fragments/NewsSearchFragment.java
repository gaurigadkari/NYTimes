package com.example.android.nytimes.Fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.nytimes.Adapters.ArticleAdapter;
import com.example.android.nytimes.Models.Article;
import com.example.android.nytimes.Models.ResponseBody;
import com.example.android.nytimes.Network.ApiInterface;
import com.example.android.nytimes.R;
import com.example.android.nytimes.Utils.Constants;
import com.example.android.nytimes.Utils.EndlessRecyclerViewScrollListener;
import com.example.android.nytimes.Utils.Utilities;
import com.example.android.nytimes.databinding.FragmentNewsSearchBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.android.nytimes.Utils.Constants.BASE_URL;


public class NewsSearchFragment extends Fragment {
    FragmentNewsSearchBinding binding;
    private OnFragmentInteractionListener mListener;
    private String searchQuery;
    Context context;
    ArrayList<Article> articles;
    ArticleAdapter adapter;
    RecyclerView newsRecyclerView;
    private EndlessRecyclerViewScrollListener scrollListener;
    public final static String LIST_STATE_KEY = "recycler_list_state";
    Parcelable listState;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    public NewsSearchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_search, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = binding.toolbar;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        newsRecyclerView = binding.newsList;
        context = getContext();
        articles = new ArrayList<>();
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        newsRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        adapter = new ArticleAdapter(getContext(), articles);
        newsRecyclerView.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);
            }
        };
        newsRecyclerView.addOnScrollListener(scrollListener);
        if (Utilities.isNetworkAvailable(context) && Utilities.isOnline()) {
            retroNetworkCall("", 0);
        } else {
            //Snackbar.make(findViewById(R.id.searchActivity), "Make sure your device is connected to the internet", Snackbar.LENGTH_LONG).show();
        }
        super.onViewCreated(view, savedInstanceState);
    }

//    protected void onRestoreInstanceState(Bundle state) {
//        super.onRestoreInstanceState(state);
//        // Retrieve list state and list/item positions
//        if(state != null)
//            listState = state.getParcelable(LIST_STATE_KEY);
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (listState != null) {
            staggeredGridLayoutManager.onRestoreInstanceState(listState);
        }
    }

    private void loadNextDataFromApi(int page) {
        if (Utilities.isNetworkAvailable(context) && Utilities.isOnline()) {
            retroNetworkCall(searchQuery, page);
        } else {
            //Snackbar.make(findViewById(R.id.searchActivity), "Make sure your device is connected to the internet", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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

    private void search(String query) {
        int page = 0;
        if (query.length() == 0) {
            Toast.makeText(getContext(), "Search text can not be empty", Toast.LENGTH_LONG).show();
        }
        articles.clear();
        //networkCall(query, page);
        if (Utilities.isNetworkAvailable(context) && Utilities.isOnline()) {
            retroNetworkCall(query, page);
        } else {
            //Snackbar.make(findViewById(R.id.searchActivity), "Make sure your device is connected to the internet", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = getActivity();
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
        this.context = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    public void retroNetworkCall(String query, final int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Map<String, String> params = new HashMap<>();
//        if (newsDeskBoolean) {
//            params.put("fq", newsDesk);
//        }
        params.put("api-key", "d31fe793adf546658bd67e2b6a7fd11a");
        params.put("page", page + "");
        if (query != "") {
            params.put("q", query);
        } else {
            searchQuery = "";
        }
        //params.put("sort", sortString);
//        if (date != "") {
//            params.put("begin_date", date);
//        }

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.getSearchResultsWithFilter(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("DEBUG", response.toString());
                if(response.body() != null){
                    if(response.body().getStatus().equals("OK")) {
                        List<Article> responseArticles = response.body().getResponse().getArticles();
                        if (responseArticles.size() != 0) {
                            articles.addAll(responseArticles);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
