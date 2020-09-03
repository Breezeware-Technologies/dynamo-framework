package net.breezeware.dynamo.apps.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringPath;

import net.breezeware.dynamo.apps.entity.DynamoApp;
import net.breezeware.dynamo.apps.entity.QDynamoApp;

@Repository
public interface DynamoAppRepository extends JpaRepository<DynamoApp, Long>, QuerydslPredicateExecutor<DynamoApp>,
        QuerydslBinderCustomizer<QDynamoApp> {

    DynamoApp findByNameIgnoreCase(String name);

    public default void customize(QuerydslBindings bindings, QDynamoApp app) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}