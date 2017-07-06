package com.articulate.nlp.lucene;

import java.util.HashMap;

/**
 * Created by apease on 6/29/17.
 */
public class SearchResult {
    public String id = "";
    public String query = "";
    public HashMap<String,String> answers = new HashMap<>(); // file id, short answer

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(id + "\n");
        sb.append(query + "\n");
        for (String k : answers.keySet()) {
            sb.append(k + "\n");
            sb.append(answers.get(k) + "\n");
        }
        return sb.toString();
    }
}
