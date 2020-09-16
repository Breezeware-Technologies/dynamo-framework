package net.breezeware.dynamo.organization.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringPath;

import net.breezeware.dynamo.organization.entity.QUser;
import net.breezeware.dynamo.organization.entity.User;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {
    List<User> findByEmailIgnoreCase(String email);

    User findByUserUniqueId(String uniqueUserId);

    /*
     * This method uses JPARepository's Query Property expressions. Using this we
     * can specify the embedded properties present in the current object's property.
     * http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#
     * repositories.query-methods.query-property-expressions In this case it is
     * "Organization" and then "Id"
     */
    List<User> findByOrganizationId(long organizationId);

    List<User> findByOrganizationIdAndUserRoleMapRoleNameIn(long organizationId, List<String> roles);

    @Query("SELECT password FROM User u WHERE u.email = ?1")
    String findPasswordByEmail(String email);

    @Query("SELECT password FROM User u WHERE u.id = ?1")
    String findPasswordByUserId(long userId);

    default void customize(QuerydslBindings bindings, QUser user) {

        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
    }

}