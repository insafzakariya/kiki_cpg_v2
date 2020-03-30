package com.kiki_cpg.development.repository.Custom;



import java.util.Date;
import java.util.List;

import com.kiki_cpg.development.entity.Ideabiz;

public interface IdeabizRepositoryCustom {

    List<Ideabiz> findByViwerId(Integer viwer_id);

    List<Ideabiz> findBySubscribe(int i, Date date);

    List<Ideabiz> getTestViewer(String s);

    List<Ideabiz> getByViwer_id(int viwerId);
}
