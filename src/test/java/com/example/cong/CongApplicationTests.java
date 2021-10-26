package com.example.cong;

import com.example.cong.bean.CongVO;
import com.example.cong.service.TestService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import sun.reflect.generics.tree.Tree;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class CongApplicationTests {
    @Autowired
    private TestService testService;

    @Test
    void contextLoads() {
    }

    @Test
    void getAllTest() throws Exception {
        Calendar a = Calendar.getInstance();
        Calendar b = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("yy-MM-dd");

        a.setTime(format.parse(format.format(new Date())));
        a.add(Calendar.DATE,9);
        a.setTime(format.parse(format.format(new Date())));
        
        System.out.println(b.compareTo(a));
        TreeMap<Integer, String> treeMap = new TreeMap<>();
    }
    @Test
    void testClass() throws Exception {
        List list = new LinkedList<>();
        CongVO cong = new CongVO();
        cong.setId("1");
        cong.setName("cong1");
        list.add(cong);

        cong = new CongVO();
        cong.setParentId("1");
        cong.setId("2");
        cong.setName("congg2");
        list.add(cong);

        List list1 = buildTree(list,"getParentI1d","getId","setChilds");
        System.out.println(list);

    }

    /**
     * 构建树形结构
     * 约束（参数VO必须有树心结构属性：父节点id{ParentId},对象id{id},子节点集合{childs}）
     * 数量庞大的不建议使用
     * 使用前请测试构建树形结构用时后再酌情使用
     * @param objectList
     * @return
     * @throws Exception
     */
    public List<Object> buildTree(List<Object> objectList,String getParentId,String getId,String setChilds) throws Exception {
        if(CollectionUtils.isEmpty(objectList)){
            return Lists.newArrayList();
        }
        //检查方法参数是否存在
        try {
            objectList.get(0).getClass().getMethod(getId);
            objectList.get(0).getClass().getMethod(setChilds,List.class);
            objectList.get(0).getClass().getMethod(getParentId);
        }catch (NoSuchMethodException ex){
            Exception exception = new Exception("VO对象中找不到方法，请检查参数："+ex.getMessage());
            throw exception;
        }

        //找出父节点  父id为空的为父节点
        List<Object> parentNodeList = objectList.stream().filter(e -> {
            try {
                return e.getClass().getMethod(getParentId).invoke(e) == null || "".equals((String)e.getClass().getMethod(getParentId).invoke(e));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList());

        if(CollectionUtils.isEmpty(parentNodeList)){
            return Lists.newArrayList();
        }
        //
        buildTree(parentNodeList,objectList, getParentId, getId, setChilds);
        return parentNodeList;
    }

    //循环每个父节点
    private void buildTree(List<Object> parentNodeList, List<Object> ObjectList,String getParentId,String getId,String setChilds) throws Exception {
        for (Object object:parentNodeList) {
            this.buildTree(object,ObjectList, getParentId, getId, setChilds);
        }
    }
    /**
     * 为每个父节点插入字节点
     * @param node 父节点
     * @param ObjectList 全量集合
     * @throws Exception
     */
    private void buildTree(Object node, List<Object> ObjectList,String getParentId,String getId,String setChilds) throws Exception {
        //获取子节点集合
        List<Object> children = getChildren(node, ObjectList, getParentId, getId, setChilds);
        if (!CollectionUtils.isEmpty(children)) {
            //插入子节点
            node.getClass().getMethod(setChilds,List.class).invoke(node,children);
            //递归找为每个父节点找字节点
            this.buildTree(children,ObjectList, getParentId, getId, setChilds);
        }
    }
    /**
     * 根据父节点id找出改节点的所有子节点
     * @param node 父节点
     * @param ObjectList 全量集合
     * @return
     * @throws Exception
     */
    private List<Object> getChildren(Object node, List<Object> ObjectList,String getParentId,String getId,String setChilds) throws  Exception {
        String id = (String)node.getClass().getMethod(getId).invoke(node);
        return ObjectList.stream().filter(e -> {
            try {
                return id.equals((String)e.getClass().getMethod(getParentId).invoke(e));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList());
    }
}
