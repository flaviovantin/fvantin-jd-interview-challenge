package br.com.fvantin.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpeciesListDTO implements Serializable {

    private static final long serialVersionUID = 5041618600381433099L;

    private Integer count;
    private String next;
    private String previous;
    private List<SpeciesDTO> results;

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

    public List<SpeciesDTO> getResults() {
        return results;
    }

    public void setResults(List<SpeciesDTO> results) {
        this.results = results;
    }
}
