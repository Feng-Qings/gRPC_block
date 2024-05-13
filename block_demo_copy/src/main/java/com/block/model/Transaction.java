package com.block.model;

import com.google.protobuf.ByteString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 业务数据模型
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

	/**
	 * 唯一标识
	 */
	private int id;
	/**
	 * 业务数据
	 */
	private byte[] businessInfo;

}
