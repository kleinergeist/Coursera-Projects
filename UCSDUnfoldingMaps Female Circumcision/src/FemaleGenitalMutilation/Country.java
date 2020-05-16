package FemaleGenitalMutilation;

public class Country {
	private String name;
	private String code3;
	private int pOverall;
	private int pUrban;
	private int pRural;

	private int pPoorest;
	private int pSecond;
	private int pMiddle;
	private int pFourth;
	private int pRichest;
	
	Country(String name, String code3, int prevalenceOverall,int prevalenceUrban,int prevalenceRural,
			int prevalencePoorest,int prevalenceSecond,int prevalenceMiddle,int prevalenceFourth,int prevalenceRichest){
		this.name=name;
		this.code3=code3;
		
		this.pOverall = prevalenceOverall;
		
		this.pUrban = prevalenceUrban;
		this.pRural = prevalenceRural;
		
		this.pPoorest = prevalencePoorest;
		this.pSecond = prevalenceSecond;
		this.pMiddle = prevalenceMiddle;
		this.pFourth = prevalenceFourth;
		this.pRichest = prevalenceRichest;
	}

	public String getName() {
		return name;
	}

	public String getCode3() {
		return code3;
	}

	public int getpOverall() {
		return pOverall;
	}

	public int getpUrban() {
		return pUrban;
	}

	public int getpRural() {
		return pRural;
	}

	public int getpPoorest() {
		return pPoorest;
	}

	public int getpSecond() {
		return pSecond;
	}

	public int getpMiddle() {
		return pMiddle;
	}

	public int getpFourth() {
		return pFourth;
	}

	public int getpRichest() {
		return pRichest;
	}


}
