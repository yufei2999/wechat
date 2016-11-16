package com.yufei.servlet;

import com.yufei.service.impl.KuwoMusicServiceImpl;
import com.yufei.utils.DataTypeUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pc on 2016-11-15.
 */
public class MusicServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(MusicServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String rt = request.getParameter("rt");
        if ("l".equals(rt)) {
            // list
            String keyword = request.getParameter("keyword");
            logger.info("keyword:" + keyword);
//            keyword = new String(keyword.getBytes(DataTypeUtils.ENCODING_ISO_8859_1), DataTypeUtils.ENCODING_UTF8);
            logger.info("keyword utf8:" + keyword);
            request.setAttribute("list", new KuwoMusicServiceImpl().getMusicList(keyword));
            request.getRequestDispatcher("list.jsp").forward(request, response);
        } else {
            // detail
            String url = request.getParameter("url");
            String songName = request.getParameter("songName");
            String artistName = request.getParameter("artistName");
//            songName = new String(songName.getBytes(DataTypeUtils.ENCODING_ISO_8859_1), DataTypeUtils.ENCODING_UTF8);
//            artistName = new String(artistName.getBytes(DataTypeUtils.ENCODING_ISO_8859_1), DataTypeUtils.ENCODING_UTF8);

            request.setAttribute("url", url);
            request.setAttribute("songName", songName);
            request.setAttribute("artistName", artistName);

            request.getRequestDispatcher("detail.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
