package com.vst.common.pojo;

/**
 * 全球购直播订单
 * @author lhp
 * @date 2017-11-28 下午04:42:11
 * @version
 */
public class VstShoppingLiveOrder {
	/**
	 * 主键，自增长
	 */
	private Long vst_pk_id;
	
	/**
	 * 订单日期
	 */
	private Long vst_shopping_live_order_date;
	
	/**
	 * 商品渠道
	 */
	private String vst_shopping_live_order_channel;
	
	/**
	 * 订单号
	 */
	private String vst_shopping_live_order_number;
	
	/**
	 * 商品编号
	 */
	private String vst_shopping_live_order_goods_number;
	
	/**
	 * 商品分类
	 */
	private String vst_shopping_live_order_goods_category;
	
	/**
	 * 商品名称
	 */
	private String vst_shopping_live_order_goods_name;
	
	/**
	 * 商品价格
	 */
	private Double vst_shopping_live_order_goods_price;
	
	/**
	 * 购买数量
	 */
	private Integer vst_shopping_live_order_qty;
	
	/**
	 * 销售金额
	 */
	private Double vst_shopping_live_order_sale_price;
	
	/**
	 * 省份
	 */
	private String vst_shopping_live_order_province;
	
	/**
	 * 收件地址
	 */
	private String vst_shopping_live_order_address;
	
	/**
	 * 收件人
	 */
	private String vst_shopping_live_order_recipient;
	
	/**
	 * 联系电话
	 */
	private String vst_shopping_live_order_phone;
	
	/**
	 * 商品来源
	 */
	private String vst_shopping_live_order_source;
	
	/**
	 * 对账状态
	 */
	private String vst_shopping_live_order_balance_status;
	
	/**
	 * 物流状态
	 */
	private String vst_shopping_live_order_logistics_status;
	
	/**
	 * 创建时间
	 */
	private Long vst_shopping_live_order_create_time;
	
	/**
	 * 对账时间
	 */
	private Long vst_shopping_live_order_balance_time;
	
	/**
	 * 新增时间
	 */
	private Long vst_shopping_live_order_addtime;
	
	/**
	 * 修改时间
	 */
	private Long vst_shopping_live_order_uptime;
	
	/**
	 * 操作人
	 */
	private String vst_shopping_live_order_operator;

	public Long getVst_pk_id() {
		return vst_pk_id;
	}

	public void setVst_pk_id(Long vstPkId) {
		vst_pk_id = vstPkId;
	}

	public Long getVst_shopping_live_order_date() {
		return vst_shopping_live_order_date;
	}

	public void setVst_shopping_live_order_date(Long vstShoppingLiveOrderDate) {
		vst_shopping_live_order_date = vstShoppingLiveOrderDate;
	}

	public String getVst_shopping_live_order_channel() {
		return vst_shopping_live_order_channel;
	}

	public void setVst_shopping_live_order_channel(
			String vstShoppingLiveOrderChannel) {
		vst_shopping_live_order_channel = vstShoppingLiveOrderChannel;
	}

	public String getVst_shopping_live_order_number() {
		return vst_shopping_live_order_number;
	}

	public void setVst_shopping_live_order_number(String vstShoppingLiveOrderNumber) {
		vst_shopping_live_order_number = vstShoppingLiveOrderNumber;
	}

	public String getVst_shopping_live_order_goods_number() {
		return vst_shopping_live_order_goods_number;
	}

	public void setVst_shopping_live_order_goods_number(
			String vstShoppingLiveOrderGoodsNumber) {
		vst_shopping_live_order_goods_number = vstShoppingLiveOrderGoodsNumber;
	}

	public String getVst_shopping_live_order_goods_category() {
		return vst_shopping_live_order_goods_category;
	}

	public void setVst_shopping_live_order_goods_category(
			String vstShoppingLiveOrderGoodsCategory) {
		vst_shopping_live_order_goods_category = vstShoppingLiveOrderGoodsCategory;
	}

	public String getVst_shopping_live_order_goods_name() {
		return vst_shopping_live_order_goods_name;
	}

	public void setVst_shopping_live_order_goods_name(
			String vstShoppingLiveOrderGoodsName) {
		vst_shopping_live_order_goods_name = vstShoppingLiveOrderGoodsName;
	}

	public Double getVst_shopping_live_order_goods_price() {
		return vst_shopping_live_order_goods_price;
	}

	public void setVst_shopping_live_order_goods_price(
			Double vstShoppingLiveOrderGoodsPrice) {
		vst_shopping_live_order_goods_price = vstShoppingLiveOrderGoodsPrice;
	}

	public Integer getVst_shopping_live_order_qty() {
		return vst_shopping_live_order_qty;
	}

	public void setVst_shopping_live_order_qty(Integer vstShoppingLiveOrderQty) {
		vst_shopping_live_order_qty = vstShoppingLiveOrderQty;
	}

	public Double getVst_shopping_live_order_sale_price() {
		return vst_shopping_live_order_sale_price;
	}

	public void setVst_shopping_live_order_sale_price(
			Double vstShoppingLiveOrderSalePrice) {
		vst_shopping_live_order_sale_price = vstShoppingLiveOrderSalePrice;
	}

	public String getVst_shopping_live_order_province() {
		return vst_shopping_live_order_province;
	}

	public void setVst_shopping_live_order_province(
			String vstShoppingLiveOrderProvince) {
		vst_shopping_live_order_province = vstShoppingLiveOrderProvince;
	}

	public String getVst_shopping_live_order_address() {
		return vst_shopping_live_order_address;
	}

	public void setVst_shopping_live_order_address(
			String vstShoppingLiveOrderAddress) {
		vst_shopping_live_order_address = vstShoppingLiveOrderAddress;
	}

	public String getVst_shopping_live_order_recipient() {
		return vst_shopping_live_order_recipient;
	}

	public void setVst_shopping_live_order_recipient(
			String vstShoppingLiveOrderRecipient) {
		vst_shopping_live_order_recipient = vstShoppingLiveOrderRecipient;
	}

	public String getVst_shopping_live_order_phone() {
		return vst_shopping_live_order_phone;
	}

	public void setVst_shopping_live_order_phone(String vstShoppingLiveOrderPhone) {
		vst_shopping_live_order_phone = vstShoppingLiveOrderPhone;
	}

	public String getVst_shopping_live_order_source() {
		return vst_shopping_live_order_source;
	}

	public void setVst_shopping_live_order_source(String vstShoppingLiveOrderSource) {
		vst_shopping_live_order_source = vstShoppingLiveOrderSource;
	}

	public String getVst_shopping_live_order_balance_status() {
		return vst_shopping_live_order_balance_status;
	}

	public void setVst_shopping_live_order_balance_status(
			String vstShoppingLiveOrderBalanceStatus) {
		vst_shopping_live_order_balance_status = vstShoppingLiveOrderBalanceStatus;
	}

	public String getVst_shopping_live_order_logistics_status() {
		return vst_shopping_live_order_logistics_status;
	}

	public void setVst_shopping_live_order_logistics_status(
			String vstShoppingLiveOrderLogisticsStatus) {
		vst_shopping_live_order_logistics_status = vstShoppingLiveOrderLogisticsStatus;
	}

	public Long getVst_shopping_live_order_create_time() {
		return vst_shopping_live_order_create_time;
	}

	public void setVst_shopping_live_order_create_time(
			Long vstShoppingLiveOrderCreateTime) {
		vst_shopping_live_order_create_time = vstShoppingLiveOrderCreateTime;
	}

	public Long getVst_shopping_live_order_balance_time() {
		return vst_shopping_live_order_balance_time;
	}

	public void setVst_shopping_live_order_balance_time(
			Long vstShoppingLiveOrderBalanceTime) {
		vst_shopping_live_order_balance_time = vstShoppingLiveOrderBalanceTime;
	}

	public Long getVst_shopping_live_order_addtime() {
		return vst_shopping_live_order_addtime;
	}

	public void setVst_shopping_live_order_addtime(Long vstShoppingLiveOrderAddtime) {
		vst_shopping_live_order_addtime = vstShoppingLiveOrderAddtime;
	}

	public Long getVst_shopping_live_order_uptime() {
		return vst_shopping_live_order_uptime;
	}

	public void setVst_shopping_live_order_uptime(Long vstShoppingLiveOrderUptime) {
		vst_shopping_live_order_uptime = vstShoppingLiveOrderUptime;
	}

	public String getVst_shopping_live_order_operator() {
		return vst_shopping_live_order_operator;
	}

	public void setVst_shopping_live_order_operator(
			String vstShoppingLiveOrderOperator) {
		vst_shopping_live_order_operator = vstShoppingLiveOrderOperator;
	}

	@Override
	public String toString() {
		return "VstShoppingLiveOrder [vst_pk_id=" + vst_pk_id
				+ ", vst_shopping_live_order_address="
				+ vst_shopping_live_order_address
				+ ", vst_shopping_live_order_addtime="
				+ vst_shopping_live_order_addtime
				+ ", vst_shopping_live_order_balance_status="
				+ vst_shopping_live_order_balance_status
				+ ", vst_shopping_live_order_balance_time="
				+ vst_shopping_live_order_balance_time
				+ ", vst_shopping_live_order_channel="
				+ vst_shopping_live_order_channel
				+ ", vst_shopping_live_order_create_time="
				+ vst_shopping_live_order_create_time
				+ ", vst_shopping_live_order_date="
				+ vst_shopping_live_order_date
				+ ", vst_shopping_live_order_goods_category="
				+ vst_shopping_live_order_goods_category
				+ ", vst_shopping_live_order_goods_name="
				+ vst_shopping_live_order_goods_name
				+ ", vst_shopping_live_order_goods_number="
				+ vst_shopping_live_order_goods_number
				+ ", vst_shopping_live_order_goods_price="
				+ vst_shopping_live_order_goods_price
				+ ", vst_shopping_live_order_logistics_status="
				+ vst_shopping_live_order_logistics_status
				+ ", vst_shopping_live_order_number="
				+ vst_shopping_live_order_number
				+ ", vst_shopping_live_order_operator="
				+ vst_shopping_live_order_operator
				+ ", vst_shopping_live_order_phone="
				+ vst_shopping_live_order_phone
				+ ", vst_shopping_live_order_province="
				+ vst_shopping_live_order_province
				+ ", vst_shopping_live_order_qty="
				+ vst_shopping_live_order_qty
				+ ", vst_shopping_live_order_recipient="
				+ vst_shopping_live_order_recipient
				+ ", vst_shopping_live_order_sale_price="
				+ vst_shopping_live_order_sale_price
				+ ", vst_shopping_live_order_source="
				+ vst_shopping_live_order_source
				+ ", vst_shopping_live_order_uptime="
				+ vst_shopping_live_order_uptime + "]";
	}
}
