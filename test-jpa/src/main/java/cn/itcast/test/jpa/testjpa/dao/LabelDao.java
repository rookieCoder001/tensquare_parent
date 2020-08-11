package cn.itcast.test.jpa.testjpa.dao;

import cn.itcast.test.jpa.testjpa.bean.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LabelDao extends JpaRepository<Label, Long>, JpaSpecificationExecutor<Label> {
    List<Label> findAllByRecommend(String recommend);

    List<Label> findAllByState(String state);

    @Query(nativeQuery = true,value = "select * from tb_label")
    List<Label> chaxunsuoyou();

}