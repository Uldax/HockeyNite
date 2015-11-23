/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import dataObject.Bet;
import dataObject.Match;
import dataObject.Team;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import test.Communication;
import test.Protocole;

/**
 *
 * @author JUASP-G73-Android
 */
public class makeabet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet makeabet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet makeabet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
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
        Match oMatch = null;
    	Integer choix = 0;
        if(request.getParameter("idMatch") != null)
        {
            choix = Integer.parseInt(request.getParameter("idMatch")); 
            
        }
       
    	//Récupère l'objet match
        oMatch = Communication.getInstance().getMatchDetail(choix);
            
       //Récupération des informations relatives aux équipes
        Team domicile = oMatch.getDomicile();
        Team visiteur = oMatch.getExterieur();                
         
        String equipe = "";
        if(request.getParameter("equipe") != null)
        {
            equipe = request.getParameter("equipe"); 
            
        }
        
    	
       
    	//Validation du nom d'équipe recu
        
        if(equipe.equals(domicile.getName()) || equipe.equals(visiteur.getName())){
            	            
            //On récupère le montant
             Float montant = null;
            if(request.getParameter("montant") != null)
            {
                montant = Float.parseFloat(request.getParameter("montant")); 

            }
                       
            //Construction de l'objet de bet
            DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd-HH-mm-ss");
            Date date = new Date();                        
            Random rand = new Random();

            int  n = rand.nextInt(50) + 1;           
            Bet b = new Bet(dateFormat.format(date) + "-" + n,choix, equipe, montant);
                        
            try {
                int betServerPort = 1248;            
                int result = Protocole.sendTCP(b, betServerPort);                   
                
                out.print(result);      
             } catch (Exception e) {
                
             }
                        
        }
	             
    		
    
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
        processRequest(request, response);
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
