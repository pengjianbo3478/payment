/**
 * Project Name:payment
 * File Name:GenerateUtils.java
 * Package Name:com.gl365.payment.util
 * Date:2017年4月25日下午6:34:52
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.gl365.payment.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import com.gl365.payment.model.PayMain;

/**
 * ClassName:GenerateUtils <br/>
 * Date: 2017年4月25日 下午6:34:52 <br/>
 * 
 * @author duanxz
 * @version
 * @see
 */
public class GenerateUtils {

	/*public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
//		printMybatisInsertValues(sb, new PayDetail());
		printUpdatePayMain(sb);
		System.out.println(sb.toString());
		System.out.println();
	}*/

	static void printUpdatePayMain(StringBuffer sb){
		String str = "pay_id = #{record.payId,jdbcType=VARCHAR},  request_id = #{record.requestId,jdbcType=VARCHAR},"
				+ "     request_date = #{record.requestDate,jdbcType=DATE},"
				+ "      organ_code = #{record.organCode,jdbcType=VARCHAR},"
				+ "      organ_merchant_no = #{record.organMerchantNo,jdbcType=VARCHAR},"
				+ "      merchant_no = #{record.merchantNo,jdbcType=VARCHAR},"
				+ "      merchant_name = #{record.merchantName,jdbcType=VARCHAR},"
				+ "      terminal = #{record.terminal,jdbcType=VARCHAR},"
				+ "      operator = #{record.operator,jdbcType=VARCHAR},"
				+ "      merchant_agent_no = #{record.merchantAgentNo,jdbcType=VARCHAR},"
				+ "      user_agent_type = #{record.userAgentType,jdbcType=CHAR},"
				+ "      user_agent_no = #{record.userAgentNo,jdbcType=VARCHAR},"
				+ "      area_agent_no = #{record.areaAgentNo,jdbcType=VARCHAR},"
				+ "      marketer_id = #{record.marketerId,jdbcType=VARCHAR},"
				+ "      province = #{record.province,jdbcType=SMALLINT},"
				+ "      city = #{record.city,jdbcType=SMALLINT},"
				+ "      district = #{record.district,jdbcType=SMALLINT},"
				+ "      trans_type = #{record.transType,jdbcType=VARCHAR},"
				+ "      scene = #{record.scene,jdbcType=VARCHAR},"
				+ "      merchant_order_title = #{record.merchantOrderTitle,jdbcType=VARCHAR},"
				+ "      merchent_order_desc = #{record.merchentOrderDesc,jdbcType=VARCHAR},"
				+ "      merchant_order_no = #{record.merchantOrderNo,jdbcType=VARCHAR},"
				+ "      user_id = #{record.userId,jdbcType=VARCHAR},"
				+ "      user_name = #{record.userName,jdbcType=VARCHAR},"
				+ "      card_index = #{record.cardIndex,jdbcType=VARCHAR},"
				+ "      total_amount = #{record.totalAmount,jdbcType=DECIMAL},"
				+ "      no_benefit_amount = #{record.noBenefitAmount,jdbcType=DECIMAL},"
				+ "      pay_fee = #{record.payFee,jdbcType=DECIMAL},"
				+ "      cash_amount = #{record.cashAmount,jdbcType=DECIMAL},"
				+ "      bean_amount = #{record.beanAmount,jdbcType=DECIMAL},"
				+ "      coin_amount = #{record.coinAmount,jdbcType=DECIMAL},"
				+ "      pay_fee_rate = #{record.payFeeRate,jdbcType=DECIMAL},"
				+ "      comm_type = #{record.commType,jdbcType=VARCHAR},"
				+ "      comm_rate = #{record.commRate,jdbcType=DECIMAL},"
				+ "      comm_amount = #{record.commAmount,jdbcType=DECIMAL},"
				+ "      marcket_fee = #{record.marcketFee,jdbcType=DECIMAL},      "
				+ "		 gift_rate = #{record.giftRate,jdbcType=DECIMAL},"
				+ "      gift_amount = #{record.giftAmount,jdbcType=DECIMAL},"
				+ "      gift_point = #{record.giftPoint,jdbcType=DECIMAL},"
				+ "      merchant_settle_amount = #{record.merchantSettleAmount,jdbcType=DECIMAL},"
				+ "      pay_time = #{record.payTime,jdbcType=TIMESTAMP},"
				+ "      pay_status = #{record.payStatus,jdbcType=VARCHAR},"
				+ "      pay_desc = #{record.payDesc,jdbcType=VARCHAR},"
				+ "      create_time = #{record.createTime,jdbcType=TIMESTAMP},"
				+ "      create_by = #{record.createBy,jdbcType=VARCHAR},"
				+ "      modify_time = #{record.modifyTime,jdbcType=TIMESTAMP},"
				+ "      modify_by = #{record.modifyBy,jdbcType=VARCHAR}";
			printMybatisUpdateValues(sb, parseDbColumnName(str), new PayMain());
	}
	public static void printMybatisInsertValues(StringBuffer sb, Object obj) {
		// 获取对象obj的所有属性域
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			// 对于每个属性，获取属性名
			String varName = field.getName();
			if("serialVersionUID".equalsIgnoreCase(varName)) {
				continue;
			}
			sb.append("#{" + varName + "},");
			sb.append("\n");
		}
	}
	
	public static void printMybatisUpdateValues(StringBuffer sb, HashMap<String, String> columns, Object obj) {
		// 获取对象obj的所有属性域
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			// 对于每个属性，获取属性名
			String varName = field.getName();
			if("serialVersionUID".equalsIgnoreCase(varName)) {
				continue;
			}
			String column = columns.get(varName.toLowerCase());
			sb.append(column + " = #{" + varName + "},");
			sb.append("\n");
		}
	}

	static HashMap<String, String> parseDbColumnName(String ex) {
		HashMap<String, String> map = new HashMap<String, String>();
		String[] args = ex.split(",");
		for(String arg : args) {
			//list.add((arg.split("=")[0]).trim());
			String temp = arg.split("=")[0].trim();
			map.put(temp.replace("_", ""), temp);
		}
		return map;
	}
}
