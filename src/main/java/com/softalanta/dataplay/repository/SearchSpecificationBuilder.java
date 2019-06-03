package com.softalanta.dataplay.repository;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SearchSpecificationBuilder<T> {

    private List<SearchCriteria> params;
    private final Pattern pattern = Pattern.compile("(\\w+?)(<|>|:eq:|:in:|:ge:|:gt:|:le:|:lt:|:like:)(\\w+?),");

    public SearchSpecificationBuilder(String search){
        params = new ArrayList<>();
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()){
            this.with(matcher.group(1),matcher.group(2), matcher.group(3));
        }
    }

    private SearchSpecificationBuilder<T> with(String key, String operation, Object value){
        params.add(new SearchCriteria(key,operation,value));
        return this;
    }

    public Specification<T> build(){
        if(params.size() == 0){
            return null;
        }

        List<Specification<T>> spec = params.stream()
                .map(SearchSpecification<T>::new)
                .collect(Collectors.toList());

        Specification<T> result = spec.get(0);

        for(int i = 1; i < params.size(); i++){
            result = Specification.where(result).and(spec.get(i));
        }
        return result;
    }
}
