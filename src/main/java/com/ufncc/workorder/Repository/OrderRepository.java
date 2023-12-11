package com.ufncc.workorder.Repository;

import com.ufncc.workorder.Model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository <Order, Integer> {

}
