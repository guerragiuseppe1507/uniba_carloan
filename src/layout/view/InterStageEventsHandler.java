package layout.view;

import java.util.HashMap;

import layout.model.entities.CarloanEntity;

public class InterStageEventsHandler {
	
	private final static InterStageEventsHandler instance = new InterStageEventsHandler();
	private InterStageCallBackListener callerClass;
	public HashMap<String,CarloanEntity> params = new HashMap<String,CarloanEntity>();
	public HashMap<String,String> options = new HashMap<String,String>();
	
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
