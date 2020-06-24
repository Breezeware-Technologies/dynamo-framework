package net.breezeware.dynamo.organization.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringPath;

import net.breezeware.dynamo.organization.entity.Organization;
import net.breezeware.dynamo.organization.entity.QOrganization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long>,
        QuerydslPredicateExecutor<Organization>, QuerydslBinderCustomizer<QOrganization> {
    List<Organization> findByNameIgnoreCase(String name);

    default public void customize(QuerydslBindings bindings, QOrganization organization) {

        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }

}