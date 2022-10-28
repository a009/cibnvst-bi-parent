package com.vst.defend.communal.sharding;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;

/**
 * 根据单个sharding key取月份，对表分片算法
 * 
 * @author Kevin
 * @date 2017-12-20
 *
 */
public class MonthSingleKeyTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<Integer> {
	
	/**
	 * sql == 规则，返回满足条件的表名
	 * 
	 * SELECT * FROM vst_movie_play WHERE vst_mp_date = 20171125
	 * 		└── SELECT * FROM vst_movie_play_201711 WHERE vst_mp_date = 20171125
	 * 
	 * SELECT * FROM vst_movie_play WHERE vst_mp_date = 20171201
	 * 		└── SELECT * FROM vst_movie_play_201712 WHERE vst_mp_date = 20171201
	 * 
	 * @param tableNames
	 *            切分的所有表名
	 * @param shardingValue
	 *            等号右边的值
	 * 
	 * @return 满足查询条件的表名
	 */
	@Override
	public String doEqualSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
		System.out.println("doEqualSharding");
		
		// 取年月（20171125）
		String yearAndMonth = shardingValue.getValue().toString().substring(0, 6);
		// 计算出的表名
		String calcTableName = shardingValue.getLogicTableName() + "_" + yearAndMonth;

		for (String tableName : tableNames) {
			if (tableName.equals(calcTableName)) {
				System.out.println(tableName);
				return tableName;
			}
		}
		
		System.out.println(shardingValue.getLogicTableName());
		return shardingValue.getLogicTableName();
	}

	/**
	 * sql between 规则，返回满足条件的表名
	 * 
	 * SELECT * FROM vst_movie_play WHERE vst_mp_date BETWEEN 20171125 AND 20171201
	 *      ├── SELECT * FROM vst_movie_play_201711 WHERE vst_mp_date BETWEEN 20171125 AND 20171201
	 *      └── SELECT * FROM vst_movie_play_201712 WHERE vst_mp_date BETWEEN 20171125 AND 20171201
	 * 
	 * @param tableNames 
	 * 				切分的所有表名
	 * @param shardingValue 
	 * 				BETWEEN的参数值
	 * 
	 * @return 满足查询条件的表名
	 */
	@Override
	public Collection<String> doBetweenSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
		System.out.println("doBetweenSharding");
		
		// 符合条件的表名
		Collection<String> result = new LinkedHashSet<String>();
		// BETWEEN的参数值
		Range<Integer> range = (Range<Integer>) shardingValue.getValueRange();
		
		// 遍历BETWEEN的参数
		for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
			// 取年月（20171125）
			String yearAndMonth = i.toString().substring(0, 6);
			// 计算出的表名
			String calcTableName = shardingValue.getLogicTableName() + "_" + yearAndMonth;
			
			if (tableNames.contains(calcTableName)) {
				result.add(calcTableName);
			} else {
				result.add(shardingValue.getLogicTableName());
			}
		}
		
		System.out.println(result);
		return result;
	}

	/**
	 * sql in 规则，返回满足条件的表名
	 * 
	 * SELECT * FROM vst_movie_play WHERE vst_mp_date IN (20171101, 20171102, 20171203)
	 *      ├── SELECT * FROM vst_movie_play_201711 WHERE vst_mp_date IN (20171101, 20171102, 20171203)
	 *      └── SELECT * FROM vst_movie_play_201712 WHERE vst_mp_date IN (20171101, 20171102, 20171203)
	 * 
	 * SELECT * FROM vst_movie_play WHERE vst_mp_date IN (20171101, 20171102, 20171103)
	 * 		└── SELECT * FROM vst_movie_play_201711 WHERE vst_mp_date IN (20171101, 20171102, 20171103)
	 * 
	 * SELECT * FROM vst_movie_play WHERE vst_mp_date IN (20171201, 20171202, 20171203)
	 * 		└── SELECT * FROM vst_movie_play_201712 WHERE vst_mp_date IN (20171201, 20171202, 20171203)
	 * 
	 * @param tableNames 
	 * 				切分的所有表名
	 * @param shardingValue 
	 * 				IN的参数值
	 * 
	 * @return 满足查询条件的表名
	 */
	@Override
	public Collection<String> doInSharding(Collection<String> tableNames, ShardingValue<Integer> shardingValue) {
		System.out.println("doInSharding");
		
		// 符合条件的表名
		Collection<String> result = new LinkedHashSet<String>();

		// 遍历IN中的参数
		for (Integer value : shardingValue.getValues()) {
			// 取年月（20171125）
			String yearAndMonth = value.toString().substring(0, 6);
			// 计算出的表名
			String calcTableName = shardingValue.getLogicTableName() + "_" + yearAndMonth;

			if (tableNames.contains(calcTableName)) {
				result.add(calcTableName);
			} else {
				result.add(shardingValue.getLogicTableName());
			}
		}

		System.out.println(result);
		return result;
	}

}