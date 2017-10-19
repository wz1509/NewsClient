package me.threebears.news.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created time 2017/10/18.
 *
 * @author threeBears
 */

public class HttpResult<T> implements Serializable{

    private boolean error;

    private List<T> results;

    public boolean isSuccess() {
        return !isError();
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
