package sg.edu.nus.iss.ssa.model;

/**
 * @author Amarjeet B Singh
 * @param <T>
 *
 */
public class Member extends User{
	
	protected String fileName = "members.dat";
	protected String className = "sg.edu.nus.iss.ssa.model.Member";
	protected String[] properties = {"memberName","memberId","loyaltyPoints"};
	protected String mapKey = "memberId";


	private String memberId;
	
	private String memberName;
	
	private long loyaltyPoints;
	
	public Member(){
		this.isMember = true;
	}
	
	public Member(String memberName, String memberNumber, long loyaltyPoints){
		super();
		this.memberName=memberName;
		this.memberId = memberNumber;
		this.loyaltyPoints = loyaltyPoints;
		this.isMember = true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("member name: " ).append(memberName).append(" member number  : ").append(memberId).append(" loyaltyPoints : ").append(loyaltyPoints);
		return sb.toString();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String[] getProperties() {
		return properties;
	}

	public void setProperties(String[] properties) {
		this.properties = properties;
	}

	public String getMapKey() {
		return mapKey;
	}
	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public long getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(long loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}
	
	/**
	 * returns one dimensioanl array to display in table
	 * @return
	 */
    public String[] getMemeberArray() {
    	String[] memberDetail = new String[3];
    	memberDetail[0] = memberName;
    	memberDetail[1] = memberId;
    	memberDetail[2] = String.valueOf(loyaltyPoints);	
    	return memberDetail;
    	
    }	
}
