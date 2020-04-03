package com.kiki_cpg.development.service;

import com.kiki_cpg.development.entity.Ideabiz;

public interface IdeabizService {

	public void unsubscribe(int viwerId);

	public void addIdabiz_subscribe(Integer viewerId, String mobile_no, Integer days);

	Ideabiz findOneByViwerIdAndSubscribe(Integer viewerID, int i);
}
