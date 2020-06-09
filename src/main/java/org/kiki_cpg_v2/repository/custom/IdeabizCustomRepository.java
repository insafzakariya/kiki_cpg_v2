package org.kiki_cpg_v2.repository.custom;

import java.util.List;

import org.kiki_cpg_v2.entity.custom.IdeabizViewerCusrtomEntity;

public interface IdeabizCustomRepository {
	
	List<IdeabizViewerCusrtomEntity> getIdeabizViewerCusrtomEntityExpireBeforeToday();

}
