package android.project.spotitop.data.api.serialization;

import com.google.gson.annotations.SerializedName;

/**
 * Represents an artist of a track
 */
public class Artist {
    //Artist name
    @SerializedName("name")
    String artistName;

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
