package Entity;

public class Song {
    private int id;
    private String title;
    private String singer;
    private String icon;
    private boolean islike;

    public boolean isLike() {
        return islike;
    }

    public void setLike(boolean islike) {
        this.islike = islike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getSinger(){
        return singer;
    }

    public void setSinger(String singer){
        this.singer = singer;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }
}
