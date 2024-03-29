package android.project.spotitop.presentation.topsongsdisplay.favorite.fragment;

import android.os.Bundle;
import android.project.spotitop.data.di.FakeDependencyInjection;
import android.project.spotitop.presentation.topsongsdisplay.favorite.adapter.TrackFavoriteViewItem;
import android.project.spotitop.presentation.topsongsdisplay.favorite.adapter.FavoriteTrackActionInterface;
import android.project.spotitop.presentation.topsongsdisplay.favorite.adapter.FavoriteTrackAdapter;
import android.project.spotitop.presentation.viewmodel.DailyTopTracksViewModel;
import android.project.spotitop.presentation.viewmodel.Event;
import android.project.spotitop.presentation.viewmodel.TrackFavoriteViewModel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.project.spotitop.R;

import java.util.List;

public class FavoriteFragment extends Fragment implements FavoriteTrackActionInterface {
    public static final String TAB_NAME = "Favorites";
    private View rootView;
    private RecyclerView recyclerView;
    private FavoriteTrackAdapter favoriteTrackAdapter;
    private TrackFavoriteViewModel trackFavoriteViewModel;
    private DailyTopTracksViewModel dailyTopTracksViewModel;

    private FavoriteFragment() {
    }

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_favorite_tracks, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRecyclerView();
        registerViewModels();
    }

    private void registerViewModels() {
        trackFavoriteViewModel = new ViewModelProvider(requireActivity(), FakeDependencyInjection.getViewModelFactory()).get(TrackFavoriteViewModel.class);
        dailyTopTracksViewModel = new ViewModelProvider(requireActivity(), FakeDependencyInjection.getViewModelFactory()).get(DailyTopTracksViewModel.class);

        trackFavoriteViewModel.getFavoriteTracks().observe(getViewLifecycleOwner(), new Observer<List<TrackFavoriteViewItem>>() {
            @Override
            public void onChanged(List<TrackFavoriteViewItem> trackFavoriteViewItemList) {
                favoriteTrackAdapter.bindViewModels(trackFavoriteViewItemList);
            }
        });

        trackFavoriteViewModel.getTrackAddedEvent().observe(getViewLifecycleOwner(), new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> stringEvent) {
                //Do nothing
            }
        });

        trackFavoriteViewModel.getTrackDeletedEvent().observe(getViewLifecycleOwner(), new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> stringEvent) {
                //Do nothing
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view);
        favoriteTrackAdapter = new FavoriteTrackAdapter(this);
        recyclerView.setAdapter(favoriteTrackAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //bookFavoritePresenter.detachView();
    }



    @Override
    public void removeTrackFromFavorites(String trackId) {
        trackFavoriteViewModel.removeTrackFromFavorites(trackId);
    }


    public void onFavoriteButton(String trackId, boolean isFavorite) {
        TrackFavoriteViewItem trackFavoriteViewItem = getFavoriteTrackViewItem(trackId);
        //if (isFavorite) {
            dailyTopTracksViewModel.changeFavoriteState(trackId, isFavorite);
            //trackFavoriteViewModel.addTrackToFavorites(trackId);
        /*}
        else {
            trackViewItem.setFavorite(false);
            trackFavoriteViewModel.removeTrackFromFavorites(trackId);
        }*/
    }

    public TrackFavoriteViewItem getFavoriteTrackViewItem(String id) {
        for (TrackFavoriteViewItem trackFavoriteViewItem : favoriteTrackAdapter.getTrackFavoriteViewItemList()) {
            if (trackFavoriteViewItem.getTrackId().equals(id)) {
                return trackFavoriteViewItem;
            }
        }
        return null;
    }

}
