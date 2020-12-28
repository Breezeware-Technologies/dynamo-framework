package net.breezeware.dynamo.organization.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringPath;

import net.breezeware.dynamo.organization.entity.Address;
import net.breezeware.dynamo.organization.entity.QAddress;

@Repository
public interface AddressRepository
        extends JpaRepository<Address, Long>, QuerydslPredicateExecutor<Address>, QuerydslBinderCustomizer<QAddress> {

    default void customize(QuerydslBindings bindings, QAddress address) {
        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }

}
