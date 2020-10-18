package madrsty.demo.adminmad.EventBus;

public class PassMassageClick {

    private String massage;


    public PassMassageClick(String massage) {
        this.massage = massage;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
