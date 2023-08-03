package com.it.httpServer1.servlet;

import java.io.OutputStream;
import java.io.PrintWriter;

public interface YcServletResponse {
    public void send();
    public OutputStream getOutputStream();
    public PrintWriter getWriter();
}
