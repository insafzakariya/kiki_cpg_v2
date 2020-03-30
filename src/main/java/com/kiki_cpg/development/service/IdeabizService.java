package com.kiki_cpg.development.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.Ideabiz;
import com.kiki_cpg.development.repository.IdeabizRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IdeabizService {

    @Autowired
    IdeabizRepository ideabizRepository;

    public void unsubscribe(int viwerId) {
        List<Ideabiz> ideabizOptional = ideabizRepository.getByViwer_id(viwerId);
        Ideabiz ideabiz = ideabizOptional.get(0);
        ideabiz.setSubscribe(0);
        ideabizRepository.save(ideabiz);
    }
}
