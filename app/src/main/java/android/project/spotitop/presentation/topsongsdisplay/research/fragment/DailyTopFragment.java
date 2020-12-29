package android.project.spotitop.presentation.topsongsdisplay.research.fragment;

import android.os.Build;
import android.os.Bundle;
import android.project.spotitop.data.di.FakeDependencyInjection;
import android.project.spotitop.presentation.topsongsdisplay.research.adapter.TrackActionInterface;
import android.project.spotitop.presentation.topsongsdisplay.research.adapter.TrackAdapter;
import android.project.spotitop.presentation.topsongsdisplay.research.adapter.TrackViewItem;
import android.project.spotitop.presentation.viewmodel.DailyTopTracksViewModel;
import android.project.spotitop.presentation.viewmodel.TrackFavoriteViewModel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.project.spotitop.R;

import java.util.List;
import java.util.Timer;

public class DailyTopFragment extends Fragment implements TrackActionInterface {
    public static final String TAB_NAME = "Daily top tracks";
    private View rootView;
    private Spinner spinnerNbOfTracksView;
    private ImageButton imageButtonSearchView;
    private RecyclerView recyclerView;
    private TrackAdapter trackAdapter;
    private ProgressBar progressBar;
    private DailyTopTracksViewModel dailyTopTracksViewModel;
    private TrackFavoriteViewModel trackFavoriteViewModel;

    private DailyTopFragment() {
    }

    public static DailyTopFragment newInstance() {
        return new DailyTopFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_top_tracks_listing, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupResearchView();
        setupRecyclerView();
        progressBar = rootView.findViewById(R.id.progress_bar);

        registerViewModels();
    }

    private void registerViewModels() {
        dailyTopTracksViewModel = new ViewModelProvider(requireActivity(), FakeDependencyInjection.getViewModelFactory()).get(DailyTopTracksViewModel.class);
        trackFavoriteViewModel = new ViewModelProvider(requireActivity(), FakeDependencyInjection.getViewModelFactory()).get(TrackFavoriteViewModel.class);
        //System.out.println("FVVM is " + bookFavoriteViewModel);

        //todo : book**
        dailyTopTracksViewModel.getTracks().observe(getViewLifecycleOwner(), new Observer<List<TrackViewItem>>() {
            @Override
            public void onChanged(List<TrackViewItem> trackItemViewModelList) {
                trackAdapter.bindViewModels(trackItemViewModelList);
            }
        });

        dailyTopTracksViewModel.getIsDataLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isDataLoading) {
                progressBar.setVisibility(isDataLoading ? View.VISIBLE : View.GONE);
            }
        });
    }

    // todo : à faire
    private void setupResearchView() {
        imageButtonSearchView = rootView.findViewById(R.id.button_search_view);
        imageButtonSearchView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                dailyTopTracksViewModel.searchTopPlayist();
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view);
        trackAdapter = new TrackAdapter(this);
        recyclerView.setAdapter(trackAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }



    @Override
    public void onFavoriteButton(String trackId, boolean isFavorite) {
        //Handle add and deletion to favorites
        if (isFavorite) {
            trackFavoriteViewModel.addTrackToFavorites(trackId);
        }
        else {
            trackFavoriteViewModel.removeTrackFromFavorites(trackId);
        }
    }
}
