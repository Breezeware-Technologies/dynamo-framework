package net.breezeware.dynamo.organization.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringPath;

import net.breezeware.dynamo.organization.entity.Group;
import net.breezeware.dynamo.organization.entity.QGroup;

@Repository
public interface GroupRepository
        extends JpaRepository<Group, Long>, QuerydslPredicateExecutor<Group>, QuerydslBinderCustomizer<QGroup> {

    List<Group> findByNameIgnoreCaseAndOrganizationId(String name, long organizationId);

    List<Group> findByOrganizationId(long organizationId);

    default public void customize(QuerydslBindings bindings, QGroup Group) {

        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
        // bindings.excluding(Group.name);
    }

    List<Group> findByIdIn(List<Long> ids);

    Group findById(long groupId);
}