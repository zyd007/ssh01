package org.qhd.zy.ssh.model;

import java.util.List;

public class Pager<T> {
	private int pagerOffest;
	private int pagerSize;
	private long totalRecord;
	private List<T> datas;
	public int getPagerOffest() {
		return pagerOffest;
	}
	public void setPagerOffest(int pagerOffest) {
		this.pagerOffest = pagerOffest;
	}
	public int getPagerSize() {
		return pagerSize;
	}
	public void setPagerSize(int pagerSize) {
		this.pagerSize = pagerSize;
	}
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	
}
