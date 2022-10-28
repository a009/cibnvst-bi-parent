package com.vst.common.pojo;

/**
 * 全球购点播订单
 * @author lhp
 * @date 2017-11-28 下午04:37:44
 * @version
 */
public class VstShoppingPlayOrder {
	/**
	 * 主键，自增长
	 */
	private Long vst_pk_id;
	
	/**
	 * 订单日期
	 */
	private Long vst_shopping_play_order_date;
	
	/**
	 * 商品渠道
	 */
	private String vst_shopping_play_order_channel;
	
	/**
	 * 供应商类型
	 */
	private String vst_shopping_play_order_supplier_type;
	
	/**
	 * 商品ID
	 */
	private String vst_shopping_play_order_good_id;
	
	/**
	 * 商品名称
	 */
	private String vst_shopping_play_order_good_name;
	
	/**
	 * 商品价格
	 */
	private Double vst_shopping_play_order_good_price;
	
	/**
	 * 购买数量
	 */
	private Integer vst_shopping_play_order_qty;
	
	/**
	 * 销售金额
	 */
	private Double vst_shopping_play_order_sale_price;
	
	/**
	 * 省份
	 */
	private String vst_shopping_play_order_province;
	
	/**
	 * 收件地址
	 */
	private String vst_shopping_play_order_address;
	
	/**
	 * 收件人
	 */
	private String vst_shopping_play_order_recipient;
	
	/**
	 * 联系电话
	 */
	private String vst_shopping_play_order_phone;
	
	/**
	 * 新增时间
	 */
	private Long vst_shopping_play_order_addtime;
	
	/**
	 * 修改时间
	 */
	private Long vst_shopping_play_order_uptime;
	
	/**
	 * 操作人
	 */
	private String vst_shopping_play_order_operator;

	public Long getVst_pk_id() {
		return vst_pk_id;
	}

	public void setVst_pk_id(Long vstPkId) {
		vst_pk_id = vstPkId;
	}

	public Long getVst_shopping_play_order_date() {
		return vst_shopping_play_order_date;
	}

	public void setVst_shopping_play_order_date(Long vstShoppingPlayOrderDate) {
		vst_shopping_play_order_date = vstShoppingPlayOrderDate;
	}

	public String getVst_shopping_play_order_channel() {
		return vst_shopping_play_order_channel;
	}

	public void setVst_shopping_play_order_channel(
			String vstShoppingPlayOrderChannel) {
		vst_shopping_play_order_channel = vstShoppingPlayOrderChannel;
	}

	public String getVst_shopping_play_order_supplier_type() {
		return vst_shopping_play_order_supplier_type;
	}

	public void setVst_shopping_play_order_supplier_type(
			String vstShoppingPlayOrderSupplierType) {
		vst_shopping_play_order_supplier_type = vstShoppingPlayOrderSupplierType;
	}

	public String getVst_shopping_play_order_good_id() {
		return vst_shopping_play_order_good_id;
	}

	public void setVst_shopping_play_order_good_id(String vstShoppingPlayOrderGoodId) {
		vst_shopping_play_order_good_id = vstShoppingPlayOrderGoodId;
	}

	public String getVst_shopping_play_order_good_name() {
		return vst_shopping_play_order_good_name;
	}

	public void setVst_shopping_play_order_good_name(
			String vstShoppingPlayOrderGoodName) {
		vst_shopping_play_order_good_name = vstShoppingPlayOrderGoodName;
	}

	public Double getVst_shopping_play_order_good_price() {
		return vst_shopping_play_order_good_price;
	}

	public void setVst_shopping_play_order_good_price(
			Double vstShoppingPlayOrderGoodPrice) {
		vst_shopping_play_order_good_price = vstShoppingPlayOrderGoodPrice;
	}

	public Integer getVst_shopping_play_order_qty() {
		return vst_shopping_play_order_qty;
	}

	public void setVst_shopping_play_order_qty(Integer vstShoppingPlayOrderQty) {
		vst_shopping_play_order_qty = vstShoppingPlayOrderQty;
	}

	public Double getVst_shopping_play_order_sale_price() {
		return vst_shopping_play_order_sale_price;
	}

	public void setVst_shopping_play_order_sale_price(
			Double vstShoppingPlayOrderSalePrice) {
		vst_shopping_play_order_sale_price = vstShoppingPlayOrderSalePrice;
	}

	public String getVst_shopping_play_order_province() {
		return vst_shopping_play_order_province;
	}

	public void setVst_shopping_play_order_province(
			String vstShoppingPlayOrderProvince) {
		vst_shopping_play_order_province = vstShoppingPlayOrderProvince;
	}

	public String getVst_shopping_play_order_address() {
		return vst_shopping_play_order_address;
	}

	public void setVst_shopping_play_order_address(
			String vstShoppingPlayOrderAddress) {
		vst_shopping_play_order_address = vstShoppingPlayOrderAddress;
	}

	public String getVst_shopping_play_order_recipient() {
		return vst_shopping_play_order_recipient;
	}

	public void setVst_shopping_play_order_recipient(
			String vstShoppingPlayOrderRecipient) {
		vst_shopping_play_order_recipient = vstShoppingPlayOrderRecipient;
	}

	public String getVst_shopping_play_order_phone() {
		return vst_shopping_play_order_phone;
	}

	public void setVst_shopping_play_order_phone(String vstShoppingPlayOrderPhone) {
		vst_shopping_play_order_phone = vstShoppingPlayOrderPhone;
	}

	public Long getVst_shopping_play_order_addtime() {
		return vst_shopping_play_order_addtime;
	}

	public void setVst_shopping_play_order_addtime(Long vstShoppingPlayOrderAddtime) {
		vst_shopping_play_order_addtime = vstShoppingPlayOrderAddtime;
	}

	public Long getVst_shopping_play_order_uptime() {
		return vst_shopping_play_order_uptime;
	}

	public void setVst_shopping_play_order_uptime(Long vstShoppingPlayOrderUptime) {
		vst_shopping_play_order_uptime = vstShoppingPlayOrderUptime;
	}

	public String getVst_shopping_play_order_operator() {
		return vst_shopping_play_order_operator;
	}

	public void setVst_shopping_play_order_operator(
			String vstShoppingPlayOrderOperator) {
		vst_shopping_play_order_operator = vstShoppingPlayOrderOperator;
	}

	@Override
	public String toString() {
		return "VstShoppingPlayOrder [vst_pk_id=" + vst_pk_id
				+ ", vst_shopping_play_order_address="
				+ vst_shopping_play_order_address
				+ ", vst_shopping_play_order_addtime="
				+ vst_shopping_play_order_addtime
				+ ", vst_shopping_play_order_channel="
				+ vst_shopping_play_order_channel
				+ ", vst_shopping_play_order_date="
				+ vst_shopping_play_order_date
				+ ", vst_shopping_play_order_good_id="
				+ vst_shopping_play_order_good_id
				+ ", vst_shopping_play_order_good_name="
				+ vst_shopping_play_order_good_name
				+ ", vst_shopping_play_order_good_price="
				+ vst_shopping_play_order_good_price
				+ ", vst_shopping_play_order_operator="
				+ vst_shopping_play_order_operator
				+ ", vst_shopping_play_order_phone="
				+ vst_shopping_play_order_phone
				+ ", vst_shopping_play_order_province="
				+ vst_shopping_play_order_province
				+ ", vst_shopping_play_order_qty="
				+ vst_shopping_play_order_qty
				+ ", vst_shopping_play_order_recipient="
				+ vst_shopping_play_order_recipient
				+ ", vst_shopping_play_order_sale_price="
				+ vst_shopping_play_order_sale_price
				+ ", vst_shopping_play_order_supplier_type="
				+ vst_shopping_play_order_supplier_type
				+ ", vst_shopping_play_order_uptime="
				+ vst_shopping_play_order_uptime + "]";
	}
}
