package com.kiki_cpg.development.repository.Custom.Impl;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.kiki_cpg.development.entity.Ideabiz;
import com.kiki_cpg.development.repository.Custom.IdeabizRepositoryCustom;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IdeabizRepositoryImpl implements IdeabizRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Ideabiz> findByViwerId(Integer viwer_id) {
        Query query = entityManager.createNativeQuery("SELECT * FROM ideabiz WHERE viwer_id=:viwer_id ", Ideabiz.class);
        query.setParameter("viwer_id", viwer_id);
        return query.getResultList();
    }

    @Override
    public List<Ideabiz> findBySubscribe(int subscribe, Date today) {
        String testday = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(today);
        List<Ideabiz> ideabizsResult=new ArrayList<>();
        Query query = entityManager.createNativeQuery("select * from  ideabiz where subscribe=:subscribe and (policy_expire_at<=:testday)", Ideabiz.class);
        query.setParameter("testday", testday);
        query.setParameter("subscribe", subscribe);
        List<Ideabiz> ideabizs = query.getResultList();
        boolean equal = false;
        for (int i = 0; i < ideabizs.size(); i++) {
            today.equals(ideabizs.get(i).getPolicy_expire_at());
            ideabizsResult.add(ideabizs.get(i));
            //System.out.println("Equal");
            equal = true;
        }
        if (equal == true) {
            return ideabizsResult;
        } else {
            return null;
        }
    }

    @Override
    public List<Ideabiz> getTestViewer(String num) {
        Query query = entityManager.createNativeQuery("select c.* from ideabiz  c where (c.mobile like :num ) and subscribe=1", Ideabiz.class);
        query.setParameter("num", "%" + num + "%");
        return query.getResultList();
    }

    @Override
    public List<Ideabiz> getByViwer_id(int viwerId) {
        Query query = entityManager.createNativeQuery("select c.* from ideabiz  c where c.viwer_id=:viwerId", Ideabiz.class);
        query.setParameter("viwerId", viwerId );
        return query.getResultList();
    }
}
