package madrsty.demo.adminmad.Model;

public class StudentModel {
    String f_name,l_name,s_year,mail,password,phone,s_image,s_id;
    int s_point;

    public StudentModel() {
    }

    public StudentModel(String f_name, String l_name, String s_year, String mail, String password, String phone, String s_image, String s_id, int s_point) {
        this.f_name = f_name;
        this.l_name = l_name;
        this.s_year = s_year;
        this.mail = mail;
        this.password = password;
        this.phone = phone;
        this.s_image = s_image;
        this.s_id = s_id;
        this.s_point = s_point;
    }

    public int getS_point() {
        return s_point;
    }

    public void setS_point(int s_point) {
        this.s_point = s_point;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getS_year() {
        return s_year;
    }

    public void setS_year(String s_year) {
        this.s_year = s_year;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getS_image() {
        return s_image;
    }

    public void setS_image(String s_image) {
        this.s_image = s_image;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }
}
