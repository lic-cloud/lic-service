package cn.bestsort.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cn.bestsort.model.entity.Notice;
import cn.bestsort.model.entity.Notice.Status;
import cn.bestsort.model.entity.NoticeRead;
import cn.bestsort.model.entity.User;
import cn.bestsort.model.vo.DataTable;
import cn.bestsort.model.vo.NoticeVO;
import cn.bestsort.service.NoticeReadService;
import cn.bestsort.service.NoticeService;
import cn.bestsort.service.UserService;
import cn.bestsort.util.DataTableUtil;
import cn.bestsort.util.UserUtil;
import io.swagger.annotations.Api;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Api(tags = "公告")
@RestController
@RequestMapping("/notices")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeReadService noticeReadService;
    @Autowired
    private UserService userService;
    @PostMapping
    @ApiOperation(value = "保存公告")
    @PreAuthorize("hasAuthority('notice:add')")
    public Notice saveNotice(@RequestBody Notice notice) {
        return noticeService.save(notice);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取公告")
    @PreAuthorize("hasAuthority('notice:query')")
    public Notice get(@PathVariable Long id) {
        return noticeService.getById(id);
    }

    @GetMapping(params = "id")
    public NoticeVO readNotice(Long id) {
        NoticeVO vo = new NoticeVO();

        Notice notice = noticeService.getById(id);
        if (notice.getStatus() == Status.DRAFT) {
            return vo;
        }
        //TODO uk exception
        vo.setNotice(notice);
        //TODO defined NPE
        noticeReadService.save(
            NoticeRead.of(UserUtil.getLoginUser().getId(), notice.getId()));

        Set<Long> userIds = noticeReadService.findAllByNoticeId(notice.getId())
            .stream().map(NoticeRead::getUserId).collect(Collectors.toSet());
        vo.setUsers(userService.listAllByIds(userIds));
        return vo;
    }


    @PutMapping
    @ApiOperation(value = "修改公告")
    @PreAuthorize("hasAuthority('notice:add')")
    public Notice updateNotice(@RequestBody Notice notice) {
        Notice no = noticeService.getById(notice.getId());
        if (no.getStatus() == Status.PUBLISH) {
            throw new IllegalArgumentException("发布状态的不能修改");
        }
        noticeService.update(notice, notice.getId());

        return notice;
    }

    @GetMapping
    @ApiOperation(value = "公告管理列表")
    @PreAuthorize("hasAuthority('notice:query')")
    public DataTable<Notice> listNotice(@RequestParam int draw,
                                        @RequestParam int start,
                                        @RequestParam int length) {
        Page<Notice> page = noticeService.listAll(DataTableUtil.toPageable(start, length));
        return DataTable.build(page, draw, start);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除公告")
    @PreAuthorize("hasAuthority('notice:del')")
    public void delete(@PathVariable Long id) {
        noticeService.removeById(id);
    }

    @ApiOperation(value = "未读公告数")
    @GetMapping("/count-unread")
    public Integer countUnread() {
        User user = UserUtil.getLoginUser();
        List<Long> noticeList = noticeService.listAll().stream().map(Notice::getId)
            .collect(Collectors.toList());
        // TODO NPE
        return noticeReadService.findAllByUserId(user.getId())
            .stream()
            .filter(i -> noticeList.contains(i.getUserId()))
            .map(NoticeRead::getUserId)
            .collect(Collectors.toSet())
            .size();
    }
}
