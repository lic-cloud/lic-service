package cn.bestsort.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cn.bestsort.model.entity.Notice;
import cn.bestsort.model.entity.Notice.Status;
import cn.bestsort.model.entity.NoticeRead;
import cn.bestsort.model.entity.User;
import cn.bestsort.model.vo.DataTable;
import cn.bestsort.model.vo.NoticeReadVO;
import cn.bestsort.model.vo.NoticeVO;
import cn.bestsort.repository.NoticeReadRepository;
import cn.bestsort.repository.NoticeRepository;
import cn.bestsort.repository.UserRepository;
import cn.bestsort.repository.impl.RepositoryEntity;
import cn.bestsort.service.NoticeReadService;
import cn.bestsort.service.NoticeService;
import cn.bestsort.service.UserService;
import cn.bestsort.util.UserUtil;
import cn.bestsort.util.page.PageTableHandler;
import cn.bestsort.util.page.PageTableRequest;
import cn.bestsort.util.page.PageTableResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
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
@Api("公告")
@RestController
@RequestMapping("/notices")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeReadService noticeReadService;
    @Autowired
    private NoticeRepository repository;
    @Autowired
    private NoticeReadRepository noticeReadRepository;
    @Autowired
    RepositoryEntity rep;
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
        return repository.findAllById(id);
    }


    @GetMapping(params = "id")
    public NoticeVO readNotice(Long id) {
        NoticeVO vo = new NoticeVO();
        Notice notice = repository.findAllById(id);
        if (notice == null || notice.getStatus() == Status.DRAFT) {
            return vo;
        }
        vo.setNotice(notice);
        Long aLong = UserUtil.getLoginUser().getId();
        List<NoticeRead> all = noticeReadRepository.findAllByUserIdAndNoticeId(aLong, notice.getId());
        if (all.isEmpty())
        {
            noticeReadService.save(NoticeRead.of(aLong, notice.getId()));
        }
        List<User> users = rep.listReadUsers(id);
        vo.setUsers(users);
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
    public PageTableResponse listNotice(PageTableRequest request) {
        return PageTableHandler.handlePage(request, noticeService);
    }

    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除公告")
    @PreAuthorize("hasAuthority('notice:del')")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
        noticeReadRepository.deleteAllByNoticeId(id);
    }


    @ApiOperation(value = "未读公告数")
    @GetMapping("/count-unread")
    public Integer countUnread() {
        User user = UserUtil.getLoginUser();
        assert user != null;
        int i = noticeReadRepository.countUnread(user.getId());
        return i;
    }


    @GetMapping("/published")
    @ApiOperation(value = "公告列表")
    public PageTableResponse listNoticeReadVO(PageTableRequest request) {
        request.getParams().put("userId", UserUtil.getLoginUser().getId());
        return PageTableHandler.handlePage(request, noticeReadService);
    }
}
