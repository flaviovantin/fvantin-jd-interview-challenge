package br.com.fvantin.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PeopleListDTO implements Serializable {

    private static final long serialVersionUID = -8286371144871074199L;

    private Integer count;
    private String next;
    private String previous;
    private List<PeopleDTO> results;

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

    public List<PeopleDTO> getResults() {
        return results;
    }

    public void setResults(List<PeopleDTO> results) {
        this.results = results;
    }
}
