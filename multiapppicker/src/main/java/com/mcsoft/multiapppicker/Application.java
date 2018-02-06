package com.mcsoft.multiapppicker;


import android.graphics.drawable.Drawable;

public class Application implements Comparable<Application> {

    private Drawable applicationImage;
    private String applicationPackageName;
    private String applicationTitle;
    private boolean isSelected;
    private long id;

    Application(Drawable applicationImage, String applicationPackageName, String applicationTitle, long id) {
        this.id = id;
        this.applicationImage = applicationImage;
        this.applicationPackageName = applicationPackageName;
        this.applicationTitle = applicationTitle;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Drawable getApplicationImage() {
        return applicationImage;
    }

    public void setApplicationImage(Drawable applicationImage) {
        this.applicationImage = applicationImage;
    }

    public String getApplicationPackageName() {
        return applicationPackageName;
    }

    public void setApplicationPackageName(String applicationPackageName) {
        this.applicationPackageName = applicationPackageName;
    }

    public String getApplicationTitle() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle = applicationTitle;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int compareTo(Application other) {
        if(applicationPackageName != null && other.applicationPackageName != null)
            return applicationPackageName.compareTo(other.applicationPackageName);
        else return -1;
    }

    @Override
    public int hashCode () {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Application application = (Application) o;
        return id == application.id;
    }
}
