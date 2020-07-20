package nat.phc.blog.pojo;

/**
 * @Author: PengHaiChen
 * @Description:
 * @Date: Create in 19:46 2020/7/20
 */
public class testPojo {
    private String name;
    private int nausea;
    private String house;
    private String address;

    public testPojo(String name, int nausea, String house, String address) {
        this.name = name;
        this.nausea = nausea;
        this.house = house;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNausea() {
        return nausea;
    }

    public void setNausea(int nausea) {
        this.nausea = nausea;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
