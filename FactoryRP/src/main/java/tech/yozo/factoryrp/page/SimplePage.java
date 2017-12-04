package tech.yozo.factoryrp.page;

public class SimplePage implements Paginable {

	public static final int DEF_COUNT = 10;

	protected long totalCount = 0;
	protected int itemsPerPage = DEF_COUNT;
	protected int currentPage = 1;
	protected int totalPage = 1;

	public SimplePage() {
	}

	public SimplePage(int pageNo, int pageSize, long totalCount) {
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
				this.currentPage = (int)this.totalCount / this.itemsPerPage;
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
	public long getTotalCount() {
		return totalCount;
	}

	@Override
	public int getTotalPage() {
		int totalPage = (int)totalCount / itemsPerPage;
		if (totalCount % itemsPerPage != 0 || totalPage == 0) {
			totalPage++;
		}
		return totalPage;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public void setItemsPerPage(int pageSize) {
		this.itemsPerPage = pageSize;
	}

	public void setCurrentPage(int pageNo) {
		this.currentPage = pageNo;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}
