package com.zavsmit.iknowtravelsimple.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.zavsmit.iknowtravelsimple.MainActivity;
import com.zavsmit.iknowtravelsimple.R;
import com.zavsmit.iknowtravelsimple.io.category.ModelCategory;
import com.zavsmit.iknowtravelsimple.io.category.ReqCategory;
import com.zavsmit.iknowtravelsimple.io.feeds.ModelFeeds;
import com.zavsmit.iknowtravelsimple.io.feeds.ReqFeeds;
import com.zavsmit.iknowtravelsimple.utils.Constants;
import com.zavsmit.iknowtravelsimple.utils.SharedPref;
import com.zavsmit.iknowtravelsimple.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sega on 14.02.2015.
 */
public class FragmentFeeds extends Fragment {

    public final static String FRAGMENT_TAG = "fragmentFeeds";
    private final static int VIEW_PROGRESS = 0;
    private final static int VIEW_DATA = 1;
    private final static int VIEW_ERROR = 2;

    private int preLast;
    private int skip = 0;
    private String currentCategory = "";
    private String category = "";

    private AdapterFeeds adapter;
    private ArrayAdapter<String> adapterSpinner;

    private ViewFlipper viewFlipper;
    private ModelFeeds data = new ModelFeeds();
    private ModelCategory dataCategory;

    private ListView listFeeds;
    private ProgressBar progressBarFooter;
    private List<String> categoryList = new ArrayList<>();

    /**
     * загрузка всех данных
     */
    private boolean isFullDate;


    public FragmentFeeds() {
    }

    public static FragmentFeeds newInstance() {
        return new FragmentFeeds();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_feeds, container, false);

        listFeeds = (ListView) view.findViewById(R.id.listViewFeeds);
        adapter = new AdapterFeeds(getActivity(), data);
        listFeeds.setAdapter(adapter);
        listFeeds.setEmptyView(view.findViewById(android.R.id.empty));

        FrameLayout footerFrameLayout = (FrameLayout) inflater.inflate(R.layout.footer_progress_in_lists, null);
        progressBarFooter = (ProgressBar) footerFrameLayout.findViewById(R.id.footer);
        listFeeds.addFooterView(footerFrameLayout, null, false);

        viewFlipper = (ViewFlipper) view;

        Button buttonRetry = (Button) view.findViewById(R.id.buttonRetry);

        // для offline
//        view.findViewById(R.id.feedsDownloadButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!SharedPref.getIsDataDownloads(getActivity())) {
//                    isFullDate = true;
//                    getFeedsFromServer();
//                } else {
//                    Toast.makeText(getActivity(), "Данные уже загружены", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        listFeeds.setOnItemClickListener(new AbsListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), data.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        listFeeds.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        progressBarFooter.setVisibility(View.VISIBLE);
                        skip += Constants.LIMIT;
                        getFeedsFromServer();
                        preLast = lastItem;
                    }
                }
            }
        });


        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFeedsFromServer();
            }
        });


        categoryList.add(getActivity().getString(R.string.all));
        adapterSpinner = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, categoryList);
        adapterSpinner.setDropDownViewResource(R.layout.spinner_dropdown_item);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        android.support.v7.app.ActionBar.OnNavigationListener x = new android.support.v7.app.ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                if (position == 0) {
                    category = "";
                } else {
                    category = dataCategory.get(position - 1).getIdCategory();
                }
                if (!currentCategory.equals(category)) {
                    skip = 0;
                    getFeedsFromServer();
                }
                return true;
            }
        };
        android.support.v7.app.ActionBar actionBar = ((ActionBarActivity) getActivity())
                .getSupportActionBar();

        actionBar.setTitle("");
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(adapterSpinner, x);

        setHasOptionsMenu(true);


        if (!Utils.hasConnection(getActivity()) && SharedPref.getIsDataDownloads(getActivity())) {
            // для offline
        } else {
            getCategoryFeedsFromServer();
            getFeedsFromServer();
        }
    }


    private void getFeedsFromServer() {

        if (isFullDate) {
            // загрузка всех фидов
            getSpiceManager().execute(new ReqFeeds(getActivity()), new GetDataFeedsRequestListener());
        } else {
            // обычная загрузка
            if (skip == 0) {
                if (viewFlipper.getDisplayedChild() != VIEW_PROGRESS)
                    viewFlipper.setDisplayedChild(VIEW_PROGRESS);
            }

            getSpiceManager().execute(new ReqFeeds(getActivity(), skip, category), Constants.GET_FEEDS_CACHE + skip + category,
                    Constants.GET_FEEDS_TIME, new GetDataFeedsRequestListener());
        }
    }

    private void setDataFeedsInterface(ModelFeeds dataFeeds) {
        if (dataFeeds == null) {
            viewFlipper.setDisplayedChild(VIEW_ERROR);
            progressBarFooter.setVisibility(View.GONE);
            return;
        }

        if (currentCategory.equals(category)) {
            //подгрузка или первая загрузка
            data.addAll(dataFeeds);
            progressBarFooter.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            //смена категории
            data.clear();
            data.addAll(dataFeeds);
            adapter.notifyDataSetChanged();
            listFeeds.setSelection(0);
            currentCategory = category;
            preLast = 0;
        }

        if (skip == 0)
            viewFlipper.setDisplayedChild(VIEW_DATA);
    }

    private void setFullDataFeedsBase(ModelFeeds dataFeeds) {
        // для offline
//        isFullDate = false;
//        if (dataFeeds == null) {
//            return;
//        }
//
//        for (int i = 0; i < dataFeeds.size(); i++) {
//
//            String title = dataFeeds.get(i).getTitle();
//            String id = dataFeeds.get(i).getIdFeed();
//            String idtype = dataFeeds.get(i).getType();
//
//            String image;
//            if (dataFeeds.get(i).getArticleCover() != null) {
//                image = dataFeeds.get(i).getArticleCover().getFilename();
//            } else {
//                image = dataFeeds.get(i).getAddressCover().getFilename();
//            }
//            ArrayList<String> categories = new ArrayList<>();
//            for (int j = 0; j < dataFeeds.get(i).getCategories().size(); j++) {
//                categories.add(dataFeeds.get(i).getCategories().get(j).getIdCategory());
//            }
//
//            DBFeeds base = new DBFeeds(title, id, idtype, image, categories);
//            base.save();
//        }
//
//        SharedPref.setIsDataDownloads(true, getActivity());
//        Toast.makeText(getActivity(), "данные сохранены", Toast.LENGTH_SHORT).show();
    }

    // категории
    //
    //
    private void getCategoryFeedsFromServer() {
        getSpiceManager().execute(new ReqCategory(getActivity()), Constants.GET_CATEGORY_CACHE,
                Constants.GET_CATEGORY_TIME, new GetCategoryFeedsRequestListener());
    }

    private void setCategoryFeedsInterface(ModelCategory dataCategory) {
        if (dataCategory == null) {
            return;
        }

        for (int i = 0; i < dataCategory.size(); i++) {
            categoryList.add(dataCategory.get(i).getName());
        }
        adapterSpinner.notifyDataSetChanged();
        this.dataCategory = dataCategory;
    }

    private SpiceManager getSpiceManager() {
        MainActivity activity = (MainActivity) getActivity();
        return activity.getSpiceManager();
    }

    private class GetDataFeedsRequestListener implements RequestListener<ModelFeeds> {
        @Override
        public void onRequestFailure(SpiceException e) {
            if (getActivity() == null)
                return;
            isFullDate = false;
            viewFlipper.setDisplayedChild(VIEW_ERROR);
            progressBarFooter.setVisibility(View.GONE);
        }

        @Override
        public void onRequestSuccess(ModelFeeds dataFeeds) {
            if (getActivity() == null)
                return;

            if (isFullDate) {
                setFullDataFeedsBase(dataFeeds);
            } else {
                setDataFeedsInterface(dataFeeds);
            }
        }
    }

    private class GetCategoryFeedsRequestListener implements RequestListener<ModelCategory> {
        @Override
        public void onRequestFailure(SpiceException e) {
            if (getActivity() == null)
                return;
        }

        @Override
        public void onRequestSuccess(ModelCategory dataCategory) {
            if (getActivity() == null)
                return;
            setCategoryFeedsInterface(dataCategory);
        }
    }
}