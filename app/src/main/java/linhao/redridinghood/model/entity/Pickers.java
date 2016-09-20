package linhao.redridinghood.model.entity;

import java.io.Serializable;

/**
 * 
 * @author zengtao 2015年5月20日下午7:18:14
 *
 */
public class Pickers implements Serializable {

	private static final long serialVersionUID = 1L;

	private String showConetnt;
	private String showId;
    private int pageId;
	public String getShowConetnt() {
		return showConetnt;
	}

	public String getShowId() {
		return showId;
	}

	public Pickers(String showConetnt, String showId,int pageId) {
		super();
		this.showConetnt = showConetnt;
		this.showId = showId;
		this.pageId=pageId;
	}

	public Pickers() {
		super();
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public void setShowConetnt(String showConetnt) {
		this.showConetnt = showConetnt;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}
}
