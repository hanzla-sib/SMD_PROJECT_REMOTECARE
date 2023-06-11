package com.waqasahmad.remotecare;

//Model class for steps recommendation
public class Steps_Model_doctor_side_formula {
    String recommended_steps, remaining_steps, check_status;

    public Steps_Model_doctor_side_formula(String recommended_steps, String remaining_steps, String check_status) {
        this.recommended_steps = recommended_steps;
        this.remaining_steps = remaining_steps;
        this.check_status = check_status;
    }

    public String getRecommended_steps() {
        return recommended_steps;
    }

    public void setRecommended_steps(String recommended_steps) {
        this.recommended_steps = recommended_steps;
    }

    public String getRemaining_steps() {
        return remaining_steps;
    }

    public void setRemaining_steps(String remaining_steps) {
        this.remaining_steps = remaining_steps;
    }

    public String getCheck_status() {
        return check_status;
    }

    public void setCheck_status(String check_status) {
        this.check_status = check_status;
    }
}
