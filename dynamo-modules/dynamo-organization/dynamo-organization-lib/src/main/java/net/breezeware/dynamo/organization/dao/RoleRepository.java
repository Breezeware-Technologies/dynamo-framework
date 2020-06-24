package net.breezeware.dynamo.organization.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringPath;

import net.breezeware.dynamo.organization.entity.QRole;
import net.breezeware.dynamo.organization.entity.Role;

@Repository
public interface RoleRepository
		extends JpaRepository<Role, Long>, QuerydslPredicateExecutor<Role>, QuerydslBinderCustomizer<QRole> {
	List<Role> findByNameIgnoreCaseAndOrganizationId(String name, long organizationId);

	List<Role> findByIdIn(List<Long> ids);

	List<Role> findByOrganizationId(long organizationId);

	Role findById(long roleId);

	default public void customize(QuerydslBindings bindings, QRole Role) {

		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}
}