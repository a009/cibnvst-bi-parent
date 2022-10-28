package com.vst.common.tools.number;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author weiwei
 * @date 2014-7-2 下午03:22:31
 * @description 工具类
 * @version
 */
public class ToolsRandom {

	/**
	 * 32个字符
	 */
	private static final char[] CHAR_32 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 10个纯数字字符
	 */
	private static final char[] NUMBER_10 = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9' };

	/**
	 * 26个纯字母字符
	 */
	private static final char[] CHAR_26 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * 构造器私有化
	 */
	private ToolsRandom() {
		
	}
	
	/**
	 * 随机获取一定位数的随机数,其中可能包含数字和字母
	 * 
	 * @param size
	 * @return
	 */
	public static String getRandom(int size) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < size; i++) {
			s.append(CHAR_32[((int) (Math.random() * 1000000.0D) % CHAR_32.length)]);
		}
		return s.toString();
	}

	/**
	 * 随机获取一定位数的纯数字随机数
	 * 
	 * @param size
	 * @return
	 */
	public static String getRandomNumber(int size) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < size; i++) {
			s.append(NUMBER_10[((int) (Math.random() * 1000000.0D) % NUMBER_10.length)]);
		}
		return s.toString();
	}

	/**
	 * 随机获取一定位数的纯字符随机数
	 * 
	 * @param size
	 * @return
	 */
	public static String getRandomChar(int size) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < size; i++) {
			s.append(CHAR_26[((int) (Math.random() * 1000000.0D) % CHAR_26.length)]);
		}
		return s.toString();
	}
	
	
	/**
	 * 在一定范围内获取一定数量的随机数
	 * @param range 范围
	 * @param count 数量
	 * @return
	 */
	public static Integer getRandomNumbers(int range){
		if(range <= 1){
			return 1;
		}
		// 返回结果
		Integer result = -1;
		// 最大循环次数
		int maxLoopCount = 2 << 28;
		
		for(int i = 0; i <= maxLoopCount; i++){
			int random = ((int) (Math.random() * 1000000.0D) % range);
			if(random == 0){
				continue;
			}
			result = random;
			break;
		}
		return result;
	}
	
	
	/**
	 * 在一定范围内获取一定数量的随机数
	 * @param range 范围
	 * @param count 数量
	 * @return
	 */
	public static Integer getRandomNumbersByFilter(int range, int filter){
		// 返回结果
		Integer result = -1;
		
		// 最大循环次数
		int maxLoopCount = 2 << 28;
		
		for(int i = 0; i <= maxLoopCount; i++){
			int random = ((int) (Math.random() * 1000000.0D) % range);
			if(random == 0 || filter == random){
				continue;
			}
			result = random;
			break;
		}
		return result;
	}
	
	/**
	 * 在一定范围内获取一定数量的随机数
	 * @param range 范围
	 * @param count 数量
	 * @return
	 */
	public static Set<Integer> getRandomNumbers(int range, int count){
		// 返回结果
		Set<Integer> result = new TreeSet<Integer>();
		// 最大循环次数
		int maxLoopCount = 2 << 28;
		for(int i = 0; i <= maxLoopCount; i++){
			int random = ((int) (Math.random() * 1000000.0D) % range);
			if(result.size() >= count){
				break;
			}
			
			if(random == 0){
				continue;
			}
			result.add(random);
		}
		return result;
	}
	
	/**
	 * 在一定范围内获取一定数量的随机数
	 * @param range 范围
	 * @param count 数量
	 * @param filter 过滤set
	 * @return
	 */
	public static Set<Integer> getRandomNumbers(int range, int count, Set<Integer> filter){
		// 返回结果
		Set<Integer> result = new TreeSet<Integer>();
		
		// 最大循环次数
		int maxLoopCount = 2 << 28;
		
		for(int i = 0; i <= maxLoopCount; i++){
			int random = ((int) (Math.random() * 1000000.0D) % range);
			if(result.size() >= count){
				break;
			}
			
			if(random == 0 || (filter != null && filter.contains(random))){
				continue;
			}
			result.add(random);
		}
		return result;
	}
	
	/**
	 * 在一个区间里获取随机数
	 * @param min 区间最小值
	 * @param max 区间最大值
	 * */
	public static int getRandomNumber(int min, int max){
		Random random = new Random();
	    int s = random.nextInt(max) % (max - min + 1) + min;
	    return s;
	}
	
	/**
	 * 从一个set集合随机获取一个数据
	 * @param numbers
	 * @return
	 */
	public static int getRandomNumber(Set<Integer> numbers){
		if(numbers != null){
			int size = numbers.size() + 1;
			int random = getRandomNumbers(size);
			int index = 0;
			for(Integer cid : numbers){
				index++;
				if(index == random){
					return cid;
				}
			}
		}
		return -1;
	}
}
