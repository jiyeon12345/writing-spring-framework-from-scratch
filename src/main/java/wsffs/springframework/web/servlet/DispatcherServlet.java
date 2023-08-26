package wsffs.springframework.web.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import wsffs.springframework.streotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Component
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        final String response = "<html>\n" +
                "  <head>\n" +
                "    <title>An Example Page</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <p>Hello World, this is a very simple HTML document.</p>\n" +
                "  </body>\n" +
                "</html>";
        resp.setContentType("text/html;charset=utf-8");
        resp.setContentLength(response.getBytes(StandardCharsets.UTF_8).length);
        final OutputStream outputStream = resp.getOutputStream();
        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
    }
}
