package layout.view;

public class InterStageEventsHandler {
	
	private final static InterStageEventsHandler instance = new InterStageEventsHandler();
	private InterStageCallBackListener callerClass;
	
    public static InterStageEventsHandler getInstance() {
        return instance;
    }
    
    public void setCaller(InterStageCallBackListener c){
    	callerClass = c;
    }
    
    public InterStageCallBackListener getCaller(){
    	return callerClass;
    }

}
