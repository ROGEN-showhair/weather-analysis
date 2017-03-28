package com.jd.analysis.servlet;

import com.jd.analysis.data.DownloadData;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.protocol.proto.HdfsProtos;
import org.apache.hadoop.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;

/**
 * Created by xudi1 on 2017/3/14.
 */
public class DownloadServlet extends HttpServlet {
    private static final long serialVersionUID = -7542696652474738349L;
    private static String local_location = "E:\\Java-Project\\weather-analysis\\input\\test\\download";
    private static String download_location = "hdfs://192.168.217.130:9000/user/hadoop/upload/data";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String filename = request.getParameter("fileName");
        String filepath = request.getParameter("filePath");
        String localLocation = filepath+filename;
        DownloadData.Download(localLocation,download_location);
        //request.setAttribute("result","download success!");
        request.getRequestDispatcher("success.jsp").forward(request,response);
    }

}
