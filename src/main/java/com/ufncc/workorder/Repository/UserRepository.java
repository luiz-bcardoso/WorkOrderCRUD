package com.ufncc.workorder.Repository;

import com.ufncc.workorder.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <User, Integer>{
}
