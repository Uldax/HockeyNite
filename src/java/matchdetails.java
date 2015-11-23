/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataObject.Bet;
import dataObject.BetRespond;
import dataObject.ListMatchName;
import dataObject.Match;
import dataObject.Team;
import test.Communication;
import org.json.simple.JSONObject;
import com.google.gson.*;

/**
 *
 * @author JUASP-G73-Android
 */
public class matchdetails extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Integer choix = 0;
        if(request.getParameter("idMatch") != null)
        {
            choix = Integer.parseInt(request.getParameter("idMatch")); 
            
        }
     
        //Initialisation     
        InetAddress aHost = null;
    	int serveurPort = 6780;
    	int clientPort = 6779;
    	Communication commObject = Communication.getInstance();
    	
        try {
                aHost = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
                e.printStackTrace();
        }
 		
 		//Set server port and host
    	commObject.setServeur(aHost, serveurPort, clientPort);
       
        Match Match = null;    	
    	
        Match = Communication.getInstance().getMatchDetail(choix);
       
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        Gson gson = new Gson();
 
        
        // Affichage des infomations        
        /*JSONObject obj = new JSONObject();
        
        obj.put("events", Match.getMatchEvent());
        obj.put("periode", Match.getPeriode());
        obj.put("time", Match.getTime());
        obj.put("winner", Match.getWinner());        
        out.print(obj.toJSONString());
        //obj.writeJSONString(out);*/
        out.print(gson.toJson(Match));
        out.flush();
        
        /* response.setContentType("text/html;charset=UTF-8");
       
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet testServlet</title>");            
            out.println("</head>");
            out.println("<body>");
          
            out.println("<h1>Servlet testServle2t at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    


}
