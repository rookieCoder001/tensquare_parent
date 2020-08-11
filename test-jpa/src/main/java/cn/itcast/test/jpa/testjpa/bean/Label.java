package cn.itcast.test.jpa.testjpa.bean;


//import lombok.Data;

import javax.persistence.*;
@Entity
@Table(name = "tb_label") //name = 表名
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false) // 与表字段一致 则可以不加
    private Long id;

    private String labelname;

    private String state;

    private Long count;

    private String recommend;

    private Long fans;

    public Label(Long id, String labelname, String state, Long count, String recommend, Long fans) {
        this.id = id;
        this.labelname = labelname;
        this.state = state;
        this.count = count;
        this.recommend = recommend;
        this.fans = fans;
    }

    public Label() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id == null ? null : 0L;
    }

    public String getLabelname() {
        return labelname;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname == null ? null : labelname.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend == null ? null : recommend.trim();
    }

    public Long getFans() {
        return fans;
    }

    public void setFans(Long fans) {
        this.fans = fans;
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", labelname='" + labelname + '\'' +
                ", state='" + state + '\'' +
                ", count=" + count +
                ", recommend='" + recommend + '\'' +
                ", fans=" + fans +
                '}';
    }
}