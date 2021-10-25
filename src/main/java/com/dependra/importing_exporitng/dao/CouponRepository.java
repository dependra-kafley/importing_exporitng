package com.dependra.importing_exporitng.dao;

import com.dependra.importing_exporitng.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Integer> {
}
