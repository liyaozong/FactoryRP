package cn.tech.yozo.factoryrp.page;

public class SimplePage implements Paginable {

	public static final int DEF_COUNT = 10;

	protected int totalCount = 0;
	protected int itemsPerPage = DEF_COUNT;
	protected int currentPage = 1;
	protected int totalPage = 1;
	protected int pageStyle = 1;
	protected String pageURL = "";

	public SimplePage() {
	}

	public SimplePage(int pageNo, int pageSize, int totalCount) {
		if (totalCount <= 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}
		if (pageSize <= 0) {
			this.itemsPerPage = DEF_COUNT;
		} else {
			this.itemsPerPage = pageSize;
		}
		if (pageNo <= 0) {
			this.currentPage = 1;
		} else {
			this.currentPage = pageNo;
		}
		if ((this.currentPage - 1) * this.itemsPerPage >= totalCount) {
			if (this.totalCount / this.itemsPerPage == 0) {
				this.currentPage = 1;
			} else {
				this.currentPage = this.totalCount / this.itemsPerPage;
			}
		}
	}

	/**
	 * 调整分页参数，使合理化
	 */
	public void adjustPage() {
		if (totalCount <= 0) {
			totalCount = 0;
		}
		if (itemsPerPage <= 0) {
			itemsPerPage = DEF_COUNT;
		}
		if (currentPage <= 0) {
			currentPage = 1;
		}
		if ((currentPage - 1) * itemsPerPage >= totalCount) {
			if (totalCount / itemsPerPage == 0) {
				currentPage = 1;
			} else {
				currentPage = totalCount / itemsPerPage;
			}
		}
	}

	@Override
	public int getCurrentPage() {
		return currentPage;
	}

	@Override
	public int getItemsPerPage() {
		return itemsPerPage;
	}

	@Override
	public int getTotalCount() {
		return totalCount;
	}

	@Override
	public int getTotalPage() {
		int totalPage = totalCount / itemsPerPage;
		if (totalCount % itemsPerPage != 0 || totalPage == 0) {
			totalPage++;
		}
		return totalPage;
	}

	@Override
	public boolean isFirstPage() {
		return currentPage <= 1;
	}

	@Override
	public boolean isLastPage() {
		return currentPage >= getTotalPage();
	}

	@Override
	public int getNextPage() {
		if (isLastPage()) {
			return currentPage;
		} else {
			return currentPage + 1;
		}
	}

	@Override
	public int getPrePage() {
		if (isFirstPage()) {
			return currentPage;
		} else {
			return currentPage - 1;
		}
	}

	@Override
	public int getPageStyle() {
		return pageStyle;
	}

	@Override
	public String getPageURL() {
		return pageURL;
	}

	public void setPageStyle(int pageStyle) {
		this.pageStyle = pageStyle;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setItemsPerPage(int pageSize) {
		this.itemsPerPage = pageSize;
	}

	public void setCurrentPage(int pageNo) {
		this.currentPage = pageNo;
	}

	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}
