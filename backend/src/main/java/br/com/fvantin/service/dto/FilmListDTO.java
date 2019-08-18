package br.com.fvantin.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilmListDTO implements Serializable {

    private static final long serialVersionUID = 3516869621108191884L;

    private Integer count;
    private String next;
    private String previous;
    private List<FilmDTO> results;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<FilmDTO> getResults() {
        return results;
    }

    public void setResults(List<FilmDTO> results) {
        this.results = results;
    }
}
