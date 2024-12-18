package member;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MemberDTO {
    private String id;
    private String password;
    private String name;
    private String email;
    private int userType; // user_type 추가

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;  // 평문 비밀번호를 그대로 설정
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "MemberDTO [id=" + id + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
