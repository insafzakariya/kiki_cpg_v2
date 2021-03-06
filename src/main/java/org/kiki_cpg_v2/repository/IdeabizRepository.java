package org.kiki_cpg_v2.repository;

import org.kiki_cpg_v2.entity.IdeabizEntity;
import org.kiki_cpg_v2.repository.custom.IdeabizCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdeabizRepository extends JpaRepository<IdeabizEntity, Integer>, IdeabizCustomRepository {

	IdeabizEntity findOneByViwerIdAndSubscribe(Integer viewerID, Integer active);

}
