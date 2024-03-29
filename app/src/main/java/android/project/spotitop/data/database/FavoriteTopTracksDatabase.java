package android.project.spotitop.data.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TrackEntity.class}, version = 1, exportSchema = false)
public abstract class FavoriteTopTracksDatabase extends RoomDatabase {
    public abstract FavoriteTopTracksDAO topSongsDAO();
}