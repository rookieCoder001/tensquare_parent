package cn.itcast.test.jpa.testjpa.service;

import cn.itcast.test.jpa.testjpa.bean.Label;
import cn.itcast.test.jpa.testjpa.dao.LabelDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelRepositoryService {
    @Autowired
    private LabelDao labelDao;

    public Label insert(Label label) {
//     新增
        return labelDao.save(label);
    }

    public List<Label> getList() {
//     查询所有
        return labelDao.findAll();
    }

    public List<Label> findByRecommend(String recommend) {
//    根据条件查询
        return labelDao.findAllByRecommend(recommend);
    }

    public List<Label> findByState(String state) {
        return labelDao.findAllByState(state);
    }

    public Label findById(Long labelId) {
//    根据id 查询
        return labelDao.findById(labelId).get();
    }

    public Label update(Label label) {
//    修改  save 即可做新增 也可做修改  是根据id进行判断 如果通过id查询数据库没有数据 则进行新增 反之
        return labelDao.save(label);
    }

    public void delete(Long labelId) {
//  删除
        labelDao.deleteById(labelId);
    }

    public List<Label> pageSearch(Label label) {
//  不带条件的分页
        return labelDao.findAll(new Specification<Label>() {
            //root: 根对象 也就是要把条件封装到那个对象
            //query: 封装的是查询的关键字  比如 group by ,order by 等
            //criteriaBuilder: 用来封装条件对象 如果直接返回null 表示不需要任何条件
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isBlank(label.getLabelname())) {
                    Predicate labelname = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(labelname);
                }
                if (!StringUtils.isBlank(label.getState())) {
                    Predicate state = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    list.add(state);
                }
                if (!StringUtils.isBlank(label.getRecommend())) {
                    Predicate recommend = criteriaBuilder.equal(root.get("recommend").as(String.class), label.getRecommend());
                    list.add(recommend);
                }
                //criteriaBuilder.and(predicates数组) 由于predicates的数组长度未定 因此上面先定义用集合接收  然后在转为数组
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return criteriaBuilder.and(predicates);//相当于 where loginname like '%java%' and state="1"
            }
        });
    }

    public Page<Label> page(Integer page, Integer size, Label label) {
        Pageable pageable = PageRequest.of(page - 1, size);
//  带条件的分页查询
        return labelDao.findAll(new Specification<Label>() {
            //root: 根对象 也就是要把条件封装到那个对象
            //query: 封装的是查询的关键字  比如 group by ,order by 等
            //criteriaBuilder: 用来封装条件对象 如果直接返回null 表示不需要任何条件
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isBlank(label.getLabelname())) {
                    Predicate labelname = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(labelname);
                }
                if (!StringUtils.isBlank(label.getState())) {
                    Predicate state = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    list.add(state);
                }
                if (!StringUtils.isBlank(label.getRecommend())) {
                    Predicate recommend = criteriaBuilder.equal(root.get("recommend").as(String.class), label.getRecommend());
                    list.add(recommend);
                }
                //criteriaBuilder.and(predicates数组) 由于predicates的数组长度未定 因此上面先定义用集合接收  然后在转为数组
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return criteriaBuilder.and(predicates);//相当于 where loginname like '%java%' and state="1"
            }
        }, pageable);
    }
}
