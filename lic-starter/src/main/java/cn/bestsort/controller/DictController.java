package cn.bestsort.controller;

import java.util.List;

import cn.bestsort.model.entity.Dict;
import cn.bestsort.service.DictService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@RestController
@RequestMapping("/dicts")
public class DictController {

    @Autowired
    private DictService dictService;
    @PreAuthorize("hasAuthority('dict:add')")
    @PostMapping
    @ApiOperation(value = "保存")
    public Dict save(@RequestBody Dict dict) {
        Dict d = dictService.findByTypeAndKey(dict.getType(), dict.getK());
        if (d != null) {
            throw new IllegalArgumentException("类型和key已存在");
        }
        dictService.save(dict);

        return dict;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取")
    public Dict get(@PathVariable Long id) {
        return dictService.getById(id);
    }

    @PreAuthorize("hasAuthority('dict:add')")
    @PutMapping
    @ApiOperation(value = "修改")
    public Dict update(@RequestBody Dict dict) {
        dictService.update(dict, dict.getId());
        return dict;
    }

    @PreAuthorize("hasAuthority('dict:query')")
    @GetMapping(params = { "start", "length" })
    @ApiOperation(value = "列表")
    public Page<Dict> list(Pageable request) {
        return dictService.listAll(request);
    }

    @PreAuthorize("hasAuthority('dict:del')")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public void delete(@PathVariable Long id) {
        dictService.removeById(id);
    }

    @GetMapping(params = "type")
    public List<Dict> listByType(String type) {
        return dictService.findAllByKey(type);
    }
}
