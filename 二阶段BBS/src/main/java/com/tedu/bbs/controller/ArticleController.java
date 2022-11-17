package com.tedu.bbs.controller;

import com.tedu.bbs.util.DBUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class ArticleController {

    @RequestMapping("/writeArticle")
    public void write(HttpServletRequest request, HttpServletResponse response){
        //1.获取表单信息
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String content = request.getParameter("content");
        //2.验证输入是否合法
        if(title==null || title.isEmpty() || author==null ||
        author.isEmpty() || content==null || content.isEmpty()){
            try {
                response.sendRedirect("/write_info_error.html");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //@@&&
        System.out.println("title:"+title+"\nauthor:"+author+"\ncontent:"+content);
        //3.比对用户
        try(Connection connection = DBUtil.getConnection()){
            String sql = "SELECT id FROM userinfo WHERE username=? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,author);
            ResultSet rs = ps.executeQuery();
            //判断有没有这个用户
            if(rs.next()){
                int id = rs.getInt(1);
                sql = "INSERT INTO article(title,content,u_id) VALUES (?,?,?)";
                ps = connection.prepareStatement(sql);
                ps.setString(1,title);
                ps.setString(2,content);
                ps.setInt(3,id);
                int num = ps.executeUpdate();
                if(num>0){
                    response.sendRedirect("write_success.html");
                }else {
                    response.sendRedirect("/write_info_error.html");
                }

            }else {
                response.sendRedirect("/write_hava_no_user.html");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
