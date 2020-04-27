package com.kiki_cpg.development.repository.Custom.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.kiki_cpg.development.entity.ViewerPackages;
import com.kiki_cpg.development.repository.Custom.ViewerPackagesRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

//@Repository
public class ViewerPackagesRepositoryImpl implements ViewerPackagesRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<ViewerPackages> getViewerPackages(int viewerId) {
        System.out.println("v_id " +viewerId);
        try{
            Query query=entityManager.createNativeQuery("select vp.* from viewer_packages vp join packages p where vp.PackageID = p.PackageID and vp.viewerId= :viewerId and vp.status=1 and p.Status = 1",ViewerPackages.class);
            query.setParameter("viewerId",viewerId);

            return query.getResultList();

        }catch (Exception e){

            e.getMessage();
        }
//        FROM Packages where packageId= :packageId and activityStartDate <= :todayDate and activityEndDate >= :todayDate and status=1
       return null;
    }

    @Override
    public boolean updateViewerPackage(int viewerId) {
        boolean responseStatus = false;

        try {

            entityManager.getTransaction().begin();

            Query query = entityManager
                    .createNativeQuery("update viewer_packages set status = 0, modifiedOn = :modifiedOn"+
                            " where viewerId = :viewerId");

            query.setParameter("modifiedOn", new Date());
            query.setParameter("viewerId", viewerId);

            int result = query.executeUpdate();
            entityManager.getTransaction().commit();
            if (result > 0) {
                responseStatus = true;
            }

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            responseStatus = false;

        } finally {
//            entityManager.close();
            return responseStatus;
        }
    }
}
