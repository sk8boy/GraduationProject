package com.edu.cuit.competition_management_system.action.student;

import com.edu.cuit.competition_management_system.dao.userdao.FileDao;
import com.edu.cuit.competition_management_system.entity.FileUpload;
import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.json.LayuiTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("UserFile")
public class UserFileAction {
    @Autowired
    FileDao fileDao;
    @RequestMapping("myfile")
    public String myfile(){
        return "user/file/myfile";
    }
    @RequestMapping("toFileAdd")
    public String toFileAdd(){
        return "user/file/file_add";
    }
    @RequestMapping("teamfile")
    public String teamfile(){
        return "user/file/teamfile";
    }

    /**
     * 用户查看个人文件
     * @param page
     * @param limit
     * @param session
     * @return
     */
    @RequestMapping("pageMyFile")
    @ResponseBody
    public LayuiTable pageMyFile(String page,String limit,HttpSession session){
        Users users = (Users)session.getAttribute("loginUser");
        LayuiTable layuiTable = new LayuiTable();
        Pageable pager = PageRequest.of(Integer.parseInt(page)-1,Integer.parseInt(limit));
        Page<FileUpload> pagerlist = fileDao.findAllByUserid(users.getId(),pager);
        List<FileUpload> usersList = pagerlist.getContent();
        layuiTable.setCode(0);
        layuiTable.setMsg("ok");
        layuiTable.setCount(pagerlist.getTotalElements());
        layuiTable.setData(usersList);
        return layuiTable;
    }
    /**
     * 用户查看组内文件
     * @param page
     * @param limit
     * @param session
     * @return
     */
    @RequestMapping("pageTeamFile")
    @ResponseBody
    public LayuiTable pageTeamFile(String page,String limit,HttpSession session){
        Users users = (Users)session.getAttribute("loginUser");
        LayuiTable layuiTable = new LayuiTable();
        Pageable pager = PageRequest.of(Integer.parseInt(page)-1,Integer.parseInt(limit));
        if(users.getTeamid()!=null) {
            Page<FileUpload> pagerlist = fileDao.findAllByTeamidAndFilelimit(users.getTeamid(), 1, pager);
            List<FileUpload> usersList = pagerlist.getContent();
            layuiTable.setCode(0);
            layuiTable.setMsg("ok");
            layuiTable.setCount(pagerlist.getTotalElements());
            layuiTable.setData(usersList);
        }else {
            layuiTable.setCode(0);
            layuiTable.setMsg("没有小组");
        }
        return layuiTable;
    }

}
