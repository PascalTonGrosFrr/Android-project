package android.project.spotitop.presentation.topsongsdisplay.research.adapter;

import java.util.List;

public class TrackViewItem {
    private String trackId;
    private String trackName;
    private List<String> artists;
    private String albumName;
    private String trackDuration;
    private List<String> albumImgagesUrls;

    public String getTrackDuration() {
        return trackDuration;
    }

    public void setTrackDuration(String trackDuration) {
        this.trackDuration = trackDuration;
    }



    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public List<String> getArtists() {
        return artists;
    }

    public String getArtistsToString() {
        String res = "";
        for (String artist : artists) {
            res += artist + " - ";
        }
        return res.substring(0, res.length()-3);
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<String> getAlbumImgagesUrls() {
        return albumImgagesUrls;
    }

    public String getAnAlbumImgageUrl() {
        return albumImgagesUrls.get(0);
    }

    public void setAlbumImgagesUrls(List<String> albumImgagesUrls) {
        this.albumImgagesUrls = albumImgagesUrls;
    }
}
