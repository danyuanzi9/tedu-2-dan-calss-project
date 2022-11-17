package com.tedu.bbs.controller;

import com.tedu.bbs.entity.User;
import com.tedu.bbs.util.DBUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.sql.*;

@Controller
public class UserController {
//    public UserController(){
//        try(Connection connection = DBUtil.getConnection()) {
//            String sql = "ALTER TABLE userinfo CONVERT TO CHARSET UTF8";
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.executeUpdate();
//            System.out.println("数据库字符集设置成功");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }



    @RequestMapping("/articleUsername")
    public void articleUsername(HttpServletRequest request,HttpServletResponse response){
        //@@##
        System.out.println("开始处理用户的所有文章");

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        //@@##
        System.out.println(title+","+content);

        try {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();

            pw.println("<!DOCTYPE html>");
            pw.println("<html lang=\"en\">");
            pw.println("<head>");
            pw.println("    <meta charset=\"UTF-8\">");
            pw.println("    <title>"+title+"</title>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h3><a href=\"index.html\">回到主页</a></h3><br>");
            pw.println("<center>");
            pw.println("    <table border=\"1\">");
            pw.println("        <tr>");
            pw.println("            <td>标题</td>");
            pw.println("            <td>作者</td>");
            pw.println("        </tr>");
            pw.println("</center></body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/articleList")
    public void articleList(HttpServletRequest request,HttpServletResponse response){
        //@@##
        System.out.println("开始处理文章列表");

        String uid = request.getParameter("userId");
        String username = request.getParameter("userName");

        //@@##
        System.out.println(uid+","+username);

        try(Connection connection = DBUtil.getConnection()) {
            String sql = "SELECT title,content FROM article WHERE u_id=? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,uid);
            ResultSet rs = ps.executeQuery();
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();

            pw.println("<!DOCTYPE html>");
            pw.println("<html lang=\"en\">");
            pw.println("<head>");
            pw.println("    <meta charset=\"UTF-8\">");
            pw.println("    <title>"+username+"的博客</title>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h3><a href=\"index.html\">回到主页</a></h3><br>");
            pw.println("<center>");
            pw.println("    <h2>"+username+"的博客</h2>");
            pw.println("    <table border=\"1\">");
            pw.println("        <tr>");
            pw.println("            <td>标题</td>");
            pw.println("            <td>作者</td>");
            pw.println("        </tr>");
            while (rs.next()){
                String title = rs.getString("title");
                String content = rs.getString("content");
                pw.println("        <tr>");
                pw.println("            <td><a href='/articleUsername?title="+title+"&content="+content+"'>"+title+"</a></td>");
                pw.println("            <td>"+username+"</td>");
                pw.println("        </tr>");
            }
            pw.println("</table></center></body></html>");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @RequestMapping("/userList")
    public void userList(HttpServletRequest request,HttpServletResponse response){
        try(Connection connection = DBUtil.getConnection()){
            String sql = "SELECT id,username,password,nickname,age FROM userinfo";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            response.setContentType("text/html;charset=utf-8");
            PrintWriter pw = response.getWriter();

            pw.println("<!DOCTYPE html>");
            pw.println("<html lang=\"en\">");
            pw.println("<head>");
            pw.println("    <meta charset=\"UTF-8\">");
            pw.println("    <title>用户列表</title>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h3><a href=\"index.html\">回到主页</a></h3><br>");
            pw.println("<center>");
            pw.println("    <h2>FBI WARNING</h2>");
            pw.println("    <h3>明星榜单</h3>");
            pw.println("    <table border=\"1\">");
            pw.println("        <tr>");
            pw.println("            <td>id</td>");
            pw.println("            <td>用户名</td>");
            pw.println("            <td>密码</td>");
            pw.println("            <td>昵称</td>");
            pw.println("            <td>年龄</td>");
            pw.println("        </tr>");
            while (rs.next()){
                int id = rs.getInt("id");
                String username = rs.getString("username");
                pw.println("        <tr>");
                pw.println("            <td>"+id+"</td>");
                pw.println("            <td><a href='/articleList?userId="+id+"'>"+username+"</a></td>");
                pw.println("            <td>"+rs.getString("password")+"</td>");
                pw.println("            <td>"+rs.getString("nickname")+"</td>");
                pw.println("            <td>"+rs.getInt("age")+"</td>");
                pw.println("        </tr>");
            }
            pw.println("</table></center></body></html>");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/regUser")
    public void reg(String username,String password,String nickname,int age, HttpServletResponse response){
//        另一种方法
//    public void reg(User user, HttpServletResponse response){
//        //@@&&
//        String username = user.getUsername();
//        String password = user.getPassword();
//        String nickname = user.getNickname();
//        int age = user.getAge();
        System.out.println("username:"+username+"\npassword:"+password+"\nnickname:"+nickname+"\nage:"+age);

        //必要验证，避免空表单信息
        if(username==null || username.isEmpty() || password==null || password.isEmpty() || nickname==null || nickname.isEmpty()){
            try {
                //重定向到信息输入异常提示界面。注册失败，注册信息输入有误
                response.sendRedirect("/reg_info_error.html");
                return;
            } catch (IOException e) {
                System.out.println("重定向异常UserController-reg");
            }
        }

        //往数据库中注册信息

        //验证用户是否为老用户
        try(
                //利用try()特效自动运行完归还一条数据库连接池
                //建立一条数据库连接
                Connection connection = DBUtil.getConnection()
        ) {
            //预编译语句
            String sql = "SELECT 1 FROM userinfo WHERE username=?";
            //通过连接对象Connection获取执行SQL的执行对象Statement。Statement对象用于向数据库执行SQL语句
            PreparedStatement ps = connection.prepareStatement(sql);
            //设置username字段。
            ps.setString(1,username);
            //得到 ResultSet 结果集
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                //用户名已被注册
                response.sendRedirect("/reg_have_to_login.html");
                return;
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //前面验证过后不是老用户，可以开始注册
        try (
                Connection connection = DBUtil.getConnection()
        ){
            String sql = "INSERT INTO userinfo(username,password,nickname,age)" +
                    "VALUES (username=? , password=? , nickname=? , age=? )";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,nickname);
            ps.setInt(4,age);
            int num = ps.executeUpdate();
            if(num>0){
                response.sendRedirect("/reg_success.html");
            }else {
                response.sendRedirect("/reg_info_error.html");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/loginUser")
    public void login(String username,String password,HttpServletResponse response){

        if(username==null || username.isEmpty() || password==null || password.isEmpty()){
            try {
                response.sendRedirect("login_info_error.html");
                return;
            } catch (IOException e) {
                System.out.println("重定向异常Controller-login");
            }
        }

        try(Connection connection = DBUtil.getConnection()){
            String sql = "SELECT id,username,password,nickname,age " +
                    "FROM userinfo WHERE username=? AND password=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                response.sendRedirect("/login_success.html");
            }else {
                response.sendRedirect("/login_fail.html");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void pw(PrintWriter pw,String line){
        String[] data = line.split("\\n");
        for (String s : data) {
            pw.println(s);
        }
    }

}
