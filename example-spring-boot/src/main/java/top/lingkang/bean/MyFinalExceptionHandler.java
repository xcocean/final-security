//package top.lingkang.bean;
//
//import org.springframework.stereotype.Component;
//import top.lingkang.error.FinalExceptionHandler;
//import top.lingkang.error.FinalNotLoginException;
//import top.lingkang.error.FinalPermissionException;
//import top.lingkang.error.FinalTokenException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @author lingkang
// * @date 2021/9/19 2:30
// * @description
// */
//@Component
//public class MyFinalExceptionHandler implements FinalExceptionHandler {
//    public void notLoginException(FinalNotLoginException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        response.setHeader("Content-type", "application/json; charset=utf-8");
//        response.getWriter().print("{\"code\":403,\"message\":\"" + e.getMessage() + "\"}");
//    }
//
//    public void tokenException(FinalTokenException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        response.setHeader("Content-type", "application/json; charset=utf-8");
//        response.getWriter().print("{\"code\":401,\"message\":\"" + e.getMessage() + "\"}");
//    }
//
//    public void permissionException(FinalPermissionException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        response.setHeader("Content-type", "application/json; charset=utf-8");
//        response.getWriter().print("{\"code\":401,\"message\":\"" + e.getMessage() + "\"}");
//    }
//}
