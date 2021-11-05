package ru.kpfu.itis.servlets;

import ru.kpfu.itis.entities.Room;
import ru.kpfu.itis.services.RoomService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/room")
public class RoomServlet extends HttpServlet {

    private RoomService roomService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        roomService = (RoomService) servletContext.getAttribute("roomService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Room> rooms = roomService.findRooms();
        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("/WEB-INF/views/room.jsp").forward(request, response);
    }
}

