package com.kiki_cpg.development.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiki_cpg.development.entity.Ideabiz;
import com.kiki_cpg.development.repository.IdeabizRepository;
import com.kiki_cpg.development.service.IdeabizService;

import javax.transaction.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class IdeabizServiceImpl implements IdeabizService {

	@Autowired
	IdeabizRepository ideabizRepository;

	@Override
	public void unsubscribe(int viwerId) {
		List<Ideabiz> ideabizOptional = ideabizRepository.getByViwer_id(viwerId);
		Ideabiz ideabiz = ideabizOptional.get(0);
		ideabiz.setSubscribe(0);
		ideabizRepository.save(ideabiz);
	}

	@Override
	public void addIdabiz_subscribe(Integer viewerId, String mobile_no, Integer days) {
		// TODO Auto-generated method stub
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		
		Ideabiz ideabiz=new Ideabiz();
		ideabiz.setMobile(mobile_no);
		ideabiz.setViwer_id(viewerId);
		ideabiz.setCreatedDate(ts);
		ideabiz.setSubscribe(1);
		ideabiz.setSubscribed_days(days);
		ideabizRepository.save(ideabiz);
	}

}
