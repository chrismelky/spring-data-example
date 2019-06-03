package com.softalanta.dataplay.repository;


import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SearchSpecification<T> implements Specification<T> {

    private SearchCriteria searchCriteria;

    public SearchSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {

         switch (searchCriteria.getOperation()){
             case ":like:":
                 if(root.get(searchCriteria.getKey()).getJavaType() == String.class) {
                     return builder.like(root.<String>get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%");
                 }
                 else{
                     return builder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
                 }
             case ":in:":
// TODO implement in
                 return root.get(searchCriteria.getKey()).in();
             case ":ge:":
                 return builder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
             case ":gt:":
                   return builder.greaterThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
             case ":le:":
                 return builder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
             case ":lt:":
                 return builder.lessThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
               default:
                   return null;
//TODO Implement other criteria
         }

    }
}
