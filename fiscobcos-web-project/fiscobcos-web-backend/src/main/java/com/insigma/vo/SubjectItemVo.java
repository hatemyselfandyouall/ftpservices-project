package com.insigma.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import star.vo.BaseVo;
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="jsonid")
public class SubjectItemVo extends BaseVo{


	private static final long serialVersionUID = 1L;

	/**
	 * 字段：id主键
	 *
	 * @haoxz11MyBatis
	 */
	private Long id;

	/**
	 * 字段：外键id
	 *
	 * @haoxz11MyBatis
	 */
	private Long outid;

	/**
	 * 字段：类别1专题，2专题详情,3板块
	 *
	 * @haoxz11MyBatis
	 */
	private Byte type;

	/**
	 * 字段：图片显示字段名
	 *
	 * @haoxz11MyBatis
	 */
	private String fieldname;

	/**
	 * 字段：图片地址
	 *
	 * @haoxz11MyBatis
	 */
	private String itempic;

	/**
	 * 字段：连接地址
	 *
	 * @haoxz11MyBatis
	 */
	private String itemhref;

	/**
	 * 字段：商品id
	 *
	 * @haoxz11MyBatis
	 */
	private Long itemid;

	/**
	 * 字段：商品名称
	 *
	 * @haoxz11MyBatis
	 */
	private String itemname;

	/**
	 * 字段：商品价格
	 *
	 * @haoxz11MyBatis
	 */
	private Long itemprice;

	/**
	 * 字段：月供
	 *
	 * @haoxz11MyBatis
	 */
	private Long monthpay;

	/**
	 * 字段：是否秒分期
	 *
	 * @haoxz11MyBatis
	 */
	private Byte issecond;

	/**
	 * 字段：结束时间
	 *
	 * @haoxz11MyBatis
	 */
	
	private Date endtime;

	/**
	 * 字段：剩余数量
	 *
	 * @haoxz11MyBatis
	 */
	private Long leftnum;

	/**
	 * 字段：标签图片
	 *
	 * @haoxz11MyBatis
	 */
	private String signpic;

	/**
	 * 字段：广告语
	 *
	 * @haoxz11MyBatis
	 */
	private String ad;

	/**
	 * 字段：销量
	 *
	 * @haoxz11MyBatis
	 */
	private Integer salescount;

	/**
	 * 字段：排序
	 *
	 * @haoxz11MyBatis
	 */
	private Integer orderby;

	/**
	 * 字段：创建人
	 *
	 * @haoxz11MyBatis
	 */
	private String creater;

	/**
	 * 字段：修改人
	 *
	 * @haoxz11MyBatis
	 */
	private String updater;

	/**
	 * 字段:app_subject_item.create_time
	 *
	 * @haoxz11MyBatis
	 */
	private Date createTime;

	/**
	 * 字段：修改时间
	 *
	 * @haoxz11MyBatis
	 */
	private Date modifyTime;

	/**
	 * 字段：说明
	 *
	 * @haoxz11MyBatis
	 */
	private String itemtext;

	/**
	 * 读取：id主键
	 *
	 * @return app_subject_item.id
	 *
	 * @haoxz11MyBatis
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：id主键
	 *
	 * @param id app_subject_item.id
	 *
	 * @haoxz11MyBatis
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 读取：外键id
	 *
	 * @return app_subject_item.outid
	 *
	 * @haoxz11MyBatis
	 */
	public Long getOutid() {
		return outid;
	}

	/**
	 * 设置：外键id
	 *
	 * @param outid app_subject_item.outid
	 *
	 * @haoxz11MyBatis
	 */
	public void setOutid(Long outid) {
		this.outid = outid;
	}

	/**
	 * 读取：类别1专题，2专题详情,3板块
	 *
	 * @return app_subject_item.type
	 *
	 * @haoxz11MyBatis
	 */
	public Byte getType() {
		return type;
	}

	/**
	 * 设置：类别1专题，2专题详情,3板块
	 *
	 * @param type app_subject_item.type
	 *
	 * @haoxz11MyBatis
	 */
	public void setType(Byte type) {
		this.type = type;
	}

	/**
	 * 读取：图片显示字段名
	 *
	 * @return app_subject_item.fieldname
	 *
	 * @haoxz11MyBatis
	 */
	public String getFieldname() {
		return fieldname;
	}

	/**
	 * 设置：图片显示字段名
	 *
	 * @param fieldname app_subject_item.fieldname
	 *
	 * @haoxz11MyBatis
	 */
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	/**
	 * 读取：图片地址
	 *
	 * @return app_subject_item.itempic
	 *
	 * @haoxz11MyBatis
	 */
	public String getItempic() {
		return itempic;
	}

	/**
	 * 设置：图片地址
	 *
	 * @param itempic app_subject_item.itempic
	 *
	 * @haoxz11MyBatis
	 */
	public void setItempic(String itempic) {
		this.itempic = itempic;
	}

	/**
	 * 读取：连接地址
	 *
	 * @return app_subject_item.itemhref
	 *
	 * @haoxz11MyBatis
	 */
	public String getItemhref() {
		return itemhref;
	}

	/**
	 * 设置：连接地址
	 *
	 * @param itemhref app_subject_item.itemhref
	 *
	 * @haoxz11MyBatis
	 */
	public void setItemhref(String itemhref) {
		this.itemhref = itemhref;
	}

	/**
	 * 读取：商品id
	 *
	 * @return app_subject_item.itemid
	 *
	 * @haoxz11MyBatis
	 */
	public Long getItemid() {
		return itemid;
	}

	/**
	 * 设置：商品id
	 *
	 * @param itemid app_subject_item.itemid
	 *
	 * @haoxz11MyBatis
	 */
	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	/**
	 * 读取：商品名称
	 *
	 * @return app_subject_item.itemname
	 *
	 * @haoxz11MyBatis
	 */
	public String getItemname() {
		return itemname;
	}

	/**
	 * 设置：商品名称
	 *
	 * @param itemname app_subject_item.itemname
	 *
	 * @haoxz11MyBatis
	 */
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	/**
	 * 读取：商品价格
	 *
	 * @return app_subject_item.itemprice
	 *
	 * @haoxz11MyBatis
	 */
	public Long getItemprice() {
		return itemprice;
	}

	/**
	 * 设置：商品价格
	 *
	 * @param itemprice app_subject_item.itemprice
	 *
	 * @haoxz11MyBatis
	 */
	public void setItemprice(Long itemprice) {
		this.itemprice = itemprice;
	}

	/**
	 * 读取：月供
	 *
	 * @return app_subject_item.monthpay
	 *
	 * @haoxz11MyBatis
	 */
	public Long getMonthpay() {
		return monthpay;
	}

	/**
	 * 设置：月供
	 *
	 * @param monthpay app_subject_item.monthpay
	 *
	 * @haoxz11MyBatis
	 */
	public void setMonthpay(Long monthpay) {
		this.monthpay = monthpay;
	}

	/**
	 * 读取：是否秒分期
	 *
	 * @return app_subject_item.issecond
	 *
	 * @haoxz11MyBatis
	 */
	public Byte getIssecond() {
		return issecond;
	}

	/**
	 * 设置：是否秒分期
	 *
	 * @param issecond app_subject_item.issecond
	 *
	 * @haoxz11MyBatis
	 */
	public void setIssecond(Byte issecond) {
		this.issecond = issecond;
	}

	/**
	 * 读取：结束时间
	 *
	 * @return app_subject_item.endtime
	 *
	 * @haoxz11MyBatis
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")  
	public Date getEndtime() {
		return endtime;
	}

	/**
	 * 设置：结束时间
	 *
	 * @param endtime app_subject_item.endtime
	 *
	 * @haoxz11MyBatis
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+08:00")
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	/**
	 * 读取：剩余数量
	 *
	 * @return app_subject_item.leftnum
	 *
	 * @haoxz11MyBatis
	 */
	public Long getLeftnum() {
		return leftnum;
	}

	/**
	 * 设置：剩余数量
	 *
	 * @param leftnum app_subject_item.leftnum
	 *
	 * @haoxz11MyBatis
	 */
	public void setLeftnum(Long leftnum) {
		this.leftnum = leftnum;
	}

	/**
	 * 读取：标签图片
	 *
	 * @return app_subject_item.signpic
	 *
	 * @haoxz11MyBatis
	 */
	public String getSignpic() {
		return signpic;
	}

	/**
	 * 设置：标签图片
	 *
	 * @param signpic app_subject_item.signpic
	 *
	 * @haoxz11MyBatis
	 */
	public void setSignpic(String signpic) {
		this.signpic = signpic;
	}

	/**
	 * 读取：广告语
	 *
	 * @return app_subject_item.ad
	 *
	 * @haoxz11MyBatis
	 */
	public String getAd() {
		return ad;
	}

	/**
	 * 设置：广告语
	 *
	 * @param ad app_subject_item.ad
	 *
	 * @haoxz11MyBatis
	 */
	public void setAd(String ad) {
		this.ad = ad;
	}

	/**
	 * 读取：销量
	 *
	 * @return app_subject_item.salescount
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getSalescount() {
		return salescount;
	}

	/**
	 * 设置：销量
	 *
	 * @param salescount app_subject_item.salescount
	 *
	 * @haoxz11MyBatis
	 */
	public void setSalescount(Integer salescount) {
		this.salescount = salescount;
	}

	/**
	 * 读取：排序
	 *
	 * @return app_subject_item.orderby
	 *
	 * @haoxz11MyBatis
	 */
	public Integer getOrderby() {
		return orderby;
	}

	/**
	 * 设置：排序
	 *
	 * @param orderby app_subject_item.orderby
	 *
	 * @haoxz11MyBatis
	 */
	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	/**
	 * 读取：创建人
	 *
	 * @return app_subject_item.creater
	 *
	 * @haoxz11MyBatis
	 */
	public String getCreater() {
		return creater;
	}

	/**
	 * 设置：创建人
	 *
	 * @param creater app_subject_item.creater
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreater(String creater) {
		this.creater = creater;
	}

	/**
	 * 读取：修改人
	 *
	 * @return app_subject_item.updater
	 *
	 * @haoxz11MyBatis
	 */
	public String getUpdater() {
		return updater;
	}

	/**
	 * 设置：修改人
	 *
	 * @param updater app_subject_item.updater
	 *
	 * @haoxz11MyBatis
	 */
	public void setUpdater(String updater) {
		this.updater = updater;
	}

	/**
	 * 读取：app_subject_item.create_time
	 *
	 * @return app_subject_item.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：app_subject_item.create_time
	 *
	 * @param createTime app_subject_item.create_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取：修改时间
	 *
	 * @return app_subject_item.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置：修改时间
	 *
	 * @param modifyTime app_subject_item.modify_time
	 *
	 * @haoxz11MyBatis
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * 读取：说明
	 *
	 * @return app_subject_item.itemtext
	 *
	 * @haoxz11MyBatis
	 */
	public String getItemtext() {
		return itemtext;
	}

	/**
	 * 设置：说明
	 *
	 * @param itemtext app_subject_item.itemtext
	 *
	 * @haoxz11MyBatis
	 */
	public void setItemtext(String itemtext) {
		this.itemtext = itemtext;
	}

}
