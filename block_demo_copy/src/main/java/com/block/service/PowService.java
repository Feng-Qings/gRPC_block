package com.block.service;



import com.block.gRPC.BlockClient;
import com.block.model.Block;
import com.block.model.Transaction;
import com.block.utils.BlockCache;
import com.block.utils.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 共识机制
 * 采用POW即工作量证明实现共识
 *
 */
@Service
@RequiredArgsConstructor
public class PowService {


	private final BlockCache blockCache;
	private final BlockService blockService;
	private final BlockClient blockClient;

	@Value("${grpc.server.port}")
	private String port;

	
	/**
	 * 通过“挖矿”进行工作量证明，实现节点间的共识
	 *
	 */
	public Block mine(){
		
		// 封装业务数据集合，记录区块产生的节点信息，临时硬编码实现
		List<Transaction> tsaList = new ArrayList<Transaction>();
		Transaction tsa1 = new Transaction();
		tsa1.setId(1);
		tsa1.setBusinessInfo(("这是地址为："+ CommonUtil.getLocalIp() + ":" + port + "的节点挖矿生成的区块").getBytes());
		tsaList.add(tsa1);
		Transaction tsa2 = new Transaction();
		tsa2.setId(2);
		tsa2.setBusinessInfo(("区块链高度为："+(blockCache.getLatestBlock().getIndex()+1)).getBytes());
		tsaList.add(tsa2);
		
		// 定义每次哈希函数的结果 
		String newBlockHash = "";
		int nonce = 0;
		long start = System.currentTimeMillis();
		System.out.println("开始挖矿");
		while (true) {
			// 计算新区块hash值
			newBlockHash = blockService.calculateHash(blockCache.getLatestBlock().getHash(), tsaList, nonce);
			// 校验hash值
			if (blockService.isValidHash(newBlockHash)) {
				System.out.println("挖矿完成，正确的hash值：" + newBlockHash);
				System.out.println("挖矿耗费时间：" + (System.currentTimeMillis() - start) + "ms");
				break;
			}
			nonce++;
		}
		// 创建新的区块
		Block block = blockService.createNewBlock(nonce, blockCache.getLatestBlock().getHash(), newBlockHash, tsaList);
		blockClient.broadcast(block);
//		p2PService.broadcast(JSON.toJSONString(msg));
		
		return block;
	}
	
}
