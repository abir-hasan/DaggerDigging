package me.abir.daggerdigging.models;

/**
 * Created by Abir on 28-Dec-17.
 */

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "result")
public class Result {


    /*@PrimaryKey(autoGenerate = true)
    private int pkid;

    public int getPkid() {
        return pkid;
    }

    public void setPkid(int pkid) {
        this.pkid = pkid;
    }*/

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    private int pageNo;

    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("genre_ids")
    @Expose
    @Ignore
    private List<Integer> genreIds = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("origin_country")
    @Expose
    @Ignore
    private List<String> originCountry = null;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private Integer id;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getFirstAirDate() {
        return firstAirDate!=null? firstAirDate.substring(0, 4): "";
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public String toString() {
        return "Result{" +
                "originalName='" + originalName + '\'' +
                ", name='" + name + '\'' +
                ", originCountry=" + originCountry +
                ", voteCount=" + voteCount +
                ", firstAirDate='" + firstAirDate + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", id=" + id +
                ", voteAverage=" + voteAverage +
                ", posterPath='" + posterPath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result result = (Result) o;

        if (originalName != null ? !originalName.equals(result.originalName) : result.originalName != null)
            return false;
        if (genreIds != null ? !genreIds.equals(result.genreIds) : result.genreIds != null)
            return false;
        if (name != null ? !name.equals(result.name) : result.name != null) return false;
        if (popularity != null ? !popularity.equals(result.popularity) : result.popularity != null)
            return false;
        if (originCountry != null ? !originCountry.equals(result.originCountry) : result.originCountry != null)
            return false;
        if (voteCount != null ? !voteCount.equals(result.voteCount) : result.voteCount != null)
            return false;
        if (firstAirDate != null ? !firstAirDate.equals(result.firstAirDate) : result.firstAirDate != null)
            return false;
        if (backdropPath != null ? !backdropPath.equals(result.backdropPath) : result.backdropPath != null)
            return false;
        if (originalLanguage != null ? !originalLanguage.equals(result.originalLanguage) : result.originalLanguage != null)
            return false;
        if (id != null ? !id.equals(result.id) : result.id != null) return false;
        if (voteAverage != null ? !voteAverage.equals(result.voteAverage) : result.voteAverage != null)
            return false;
        if (overview != null ? !overview.equals(result.overview) : result.overview != null)
            return false;
        return posterPath != null ? posterPath.equals(result.posterPath) : result.posterPath == null;
    }
}
