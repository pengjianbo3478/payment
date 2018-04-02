package com.gl365.payment.mapper;
import org.springframework.stereotype.Repository;
import com.gl365.payment.model.PayNotify;
@Repository
public interface PayNotifyMapper {
	int insert(PayNotify record);

	PayNotify queryByPayNotify(PayNotify record);
}