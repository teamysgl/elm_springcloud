package com.neusoft.po;

public class Point {

	private String pointId;
	private String userId;
	private String pointDate;
	private Integer pointNum;
	private String outDate;
	public Point()
	{
		
	}
	
	public Point(String pointId,String userId,String pointDate,Integer pointNum,String outDate)
	{
		this.pointId=pointId;
		this.userId=userId;
		this.pointDate=pointDate;
		this.pointNum=pointNum;
		this.outDate=outDate;
	}
	
	public String getPointId()
	{
		return this.pointId;
	}
	public String getUserId()
	{
		return this.userId;
	}
	public String getPointDate()
	{
		return this.pointDate;
	}
	public Integer getPointNum()
	{
		return this.pointNum;
	}
	
	public void setPointId(String pointId)
	{
		this.pointId=pointId;
	}
	public void setUserId(String userId)
	{
		this.userId=userId;
	}
	public void setPointDate(String pointDate)
	{
		this.pointDate=pointDate;
	}
	public void setPointNum(Integer pointNum)
	{
		this.pointNum=pointNum;
	}
	public String getOutDate() {
		return outDate;
	}
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}
}
