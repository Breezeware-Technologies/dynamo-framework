package net.breezeware.dynamo.apps.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.breezeware.dynamo.apps.entity.AppFeature;

@Repository
public interface AppFeatureRepository extends JpaRepository<AppFeature, Long> {
    AppFeature findByNameIgnoreCase(String name);

    @Modifying
    @Query("DELETE FROM AppFeature af WHERE af.appId = null OR af.name = null")
    void deleteOrphanedAppFeatures();
}