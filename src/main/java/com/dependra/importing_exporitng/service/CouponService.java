package com.dependra.importing_exporitng.service;

import com.dependra.importing_exporitng.dao.CouponRepository;
import com.dependra.importing_exporitng.model.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponService {

    @Autowired
    CouponRepository couponRepository;

    public void save(List<Coupon> coupon){
        couponRepository.saveAll(coupon);
    }
    public void saveOne(Coupon coupon){
        couponRepository.save(coupon);
    }
}
