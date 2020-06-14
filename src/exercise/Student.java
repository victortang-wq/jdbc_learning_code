package exercise;
//type,IDcard,examCard,studentName,location,grade
public class Student {
	private int flowID;
	private int type;
	private String IDcard;
	private String examCard;
	private String name;
	private String location;
	private int grade;
	public Student() {
		super();
	}
	public Student(int flowID, int type, String iDcard, String examCard, String name, String location, int grade) {
		super();
		this.flowID = flowID;
		this.type = type;
		IDcard = iDcard;
		this.examCard = examCard;
		this.name = name;
		this.location = location;
		this.grade = grade;
	}
	public int getFlowID() {
		return flowID;
	}
	public void setFlowID(int flowID) {
		this.flowID = flowID;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getIDcard() {
		return IDcard;
	}
	public void setIDcard(String iDcard) {
		IDcard = iDcard;
	}
	public String getExamCard() {
		return examCard;
	}
	public void setExamCard(String examCard) {
		this.examCard = examCard;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "Student [flowID=" + flowID + ", type=" + type + ", IDcard=" + IDcard + ", examCard=" + examCard
				+ ", name=" + name + ", location=" + location + ", grade=" + grade + "]";
	}
	
}
