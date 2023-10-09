//package com.kakaotech.team14backend.Mypage;
//
//import com.kakaotech.team14backend.inner.post.model.Post;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet("/mypage")
//public class MyPageServlet extends HttpServlet {
//  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    HttpSession session = request.getSession();
//    String username = (String) session.getAttribute("username");
//
//    PostDAO postDAO = new PostDAO();
//    List<Post> userPosts = postDAO.getPostsByAuthor(username);
//
//    request.setAttribute("userPosts", userPosts);
//    RequestDispatcher dispatcher = request.getRequestDispatcher("mypage.jsp");
//    dispatcher.forward(request, response);
//  }
//}
