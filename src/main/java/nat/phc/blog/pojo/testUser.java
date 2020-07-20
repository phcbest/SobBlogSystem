package nat.phc.blog.pojo;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 20:04 2020/7/20
 */
public class testUser {
    private String name;
    private String password;

    public testUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "testUser{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
