package my.com.avira.fragments;

/**
 * Created by randiwaranugraha on 7/1/15.
 */
public abstract class BaseMainFragment extends BaseFragment {

    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}