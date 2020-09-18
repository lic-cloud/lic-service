package cn.bestsort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.bestsort.model.entity.Dict;
import cn.bestsort.service.DictService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 10:27
 */
@SpringBootTest(classes = Main.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class Test {
    @Autowired
    DictService dictService;
    @org.junit.Test
    public void writeDict() {
        List<Dict> lst = dictService.listAll();
        String jsonVal = JSON.toJSONString(lst);
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("json.json"));
            out.write(jsonVal);
            out.close();
            System.out.println("文件创建成功！");
        } catch (IOException e) { }
    }
}
