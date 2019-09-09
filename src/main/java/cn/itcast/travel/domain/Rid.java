package cn.itcast.travel.domain;

public class Rid {
    private Integer rid;
    private String date;
    private Integer uid;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Rid{" +
                "rid=" + rid +
                ", date='" + date + '\'' +
                ", uid=" + uid +
                '}';
    }
}
