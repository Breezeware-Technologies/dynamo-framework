package net.breezeware.dynamo.config.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringPath;

import net.breezeware.dynamo.config.entity.AppProperty;
import net.breezeware.dynamo.config.entity.QAppProperty;

@Repository
public interface AppPropertyRepository extends JpaRepository<AppProperty, Long>, QuerydslPredicateExecutor<AppProperty>,
        QuerydslBinderCustomizer<QAppProperty> {

    List<AppProperty> findByApplication(String application);

    List<AppProperty> findByApplicationAndProfile(String application, String profile);

    List<AppProperty> findByApplicationAndProfileAndLabel(String application, String profile, String label);

    AppProperty findByApplicationAndProfileAndLabelAndKey(String application, String profile, String label, String key);

    default void customize(QuerydslBindings bindings, QAppProperty prop) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
