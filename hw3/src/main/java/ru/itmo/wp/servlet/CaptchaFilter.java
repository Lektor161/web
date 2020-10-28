package ru.itmo.wp.servlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Random;

import ru.itmo.wp.util.ImageUtils;


public class CaptchaFilter extends HttpFilter {

    private String getCaptchaForm(int num) {

        byte[] img = ImageUtils.toPng(Integer.toString(num));
        return "<html lang=\"en\">\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <title>Captcha</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <img src='data:image/png;base64," + Base64.getEncoder().encodeToString(img) + "'>" +
                "        <div class=\"captcha-enter-form\" style=\"\">\n" +
                "            <form method=\"post\">\n" +
                "                <label for=\"captcha-enter-form__user\">Enter the Number:</label>\n" +
                "                <input name=\"CaptchaText\" id=\"captcha-enter-form__user\">\n" +
                "            </form>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>";
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute("CaptchaPassed") != null &&
                session.getAttribute("CaptchaPassed").equals("true")) {
            chain.doFilter(request, response);
            return;
        }
        if (request.getMethod().equals("GET")) {
                response.setContentType("text/html");
                int gen = new Random().nextInt(899) + 100;
                session.setAttribute("CaptchaGen", Integer.toString(gen));
                String form = getCaptchaForm(gen);
                response.getWriter().print(form);
                response.getWriter().flush();
        } else {
            String sessionCaptchaGen = (String) session.getAttribute("CaptchaGen");
            String requestCaptchaText = request.getParameter("CaptchaText");
            if (sessionCaptchaGen != null && sessionCaptchaGen.equals(requestCaptchaText)) {
                session.setAttribute("CaptchaPassed", "true");
            }
            response.sendRedirect(request.getRequestURI());
        }
    }
}
