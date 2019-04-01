package com.revolv3r.gplus.dao;

import com.revolv3r.gplus.domain.PhotoData;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<PhotoData, Integer>
{

}
