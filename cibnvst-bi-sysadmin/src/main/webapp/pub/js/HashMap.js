/**
 * js hashMap的实现
 * @return
 */
function HashMap() {
	// 定义hashMap的长度
	var length = 0;
	// 创建一个对象
	var obj = new Object();

	/**
	 * 方法：判断Map是否为空
	 */
	this.isEmpty = function() {
		return length == 0;
	};

	/**
	 * 方法：判断对象中是否包含给定Key
	 */
	this.containsKey = function(key) {
		return (key in obj);
	};

	/**
	 * 方法：判断对象中是否包含给定的Value
	 */
	this.containsValue = function(value) {
		for ( var key in obj) {
			if (obj[key] == value) {
				return true;
			}
		}
		return false;
	};

	/**
	 * 方法：向map中添加数据
	 */
	this.put = function(key, value) {
		if (!this.containsKey(key)) {
			length++;
		}
		obj[key] = value;
	};

	/**
	 * 方法：根据给定的Key获得Value
	 */
	this.get = function(key) {
		return this.containsKey(key) ? obj[key] : null;
	};

	/**
	 * 方法：根据给定的Key删除一个值
	 */
	this.remove = function(key) {
		if (this.containsKey(key) && (delete obj[key])) {
			length--;
		}
	};

	/**
	 * 方法：获得Map中的所有Value
	 */
	this.values = function() {
		var _values = new Array();
		for ( var key in obj) {
			_values.push(obj[key]);
		}
		return _values;
	};

	/**
	 * 方法：获得Map中的所有Key
	 */
	this.keySet = function() {
		var _keys = new Array();
		for ( var key in obj) {
			_keys.push(key);
		}
		return _keys;
	};

	/**
	 * 方法：获得Map的长度
	 */
	this.size = function() {
		return length;
	};

	/**
	 * 方法：清空Map
	 */
	this.clear = function() {
		length = 0;
		obj = new Object();
	};
}
