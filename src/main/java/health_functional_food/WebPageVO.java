package health_functional_food;

public class WebPageVO {
	private Integer no;
	private String itemName;
	private String companyName;
	private String content;
	private String regDate;
	
	public Integer getNo() {
		return no;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getRegDate() {
		return regDate;
	}
	
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
}
