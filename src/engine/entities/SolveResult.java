package engine.entities;

public class SolveResult {
    private boolean success;
    private String feedback;

    public SolveResult(boolean success, String feedback){
        this.feedback = feedback;
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public boolean getSuccess(){
        return success;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
