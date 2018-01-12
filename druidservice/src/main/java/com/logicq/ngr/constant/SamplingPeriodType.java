package com.logicq.ngr.constant;

public enum SamplingPeriodType {

	
	Minutes("Minutes"), Day("Day"), Hour("Hour"), Week("Week"), Month("Month"), Year("Year");
	
	 private final String text;

	    /**
	     * @param text
	     */
	    private SamplingPeriodType(final String text) {
	        this.text = text;
	    }

	    /* (non-Javadoc)
	     * @see java.lang.Enum#toString()
	     */
	    @Override
	    public String toString() {
	        return text;
	    }
	    
	    public static String fromString(String samplingPeriod) {    	
	    	
	        for (SamplingPeriodType period : SamplingPeriodType.values()) {
	        	if(samplingPeriod.endsWith(period.text)){
	        		String samplPeriod = samplingPeriod.split(" ")[1];
		          if (period.text.equalsIgnoreCase(samplPeriod)) {
		            return period.text;
		          }
	        	}
	        }
	        return null;
	      }
}
