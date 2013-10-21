/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package du;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;


/**
 *
 * @author lenovo
 */
@WebServlet(name = "submit", urlPatterns = {"/submit"})
public class submit extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        StringBuffer output = new StringBuffer();
        try {
            String mykey="AIzaSyDfMDtpA724ATwfZX3eRLWQZOQlPfKtW60";
            String book_name=request.getParameter("book_name");
            String author_name=request.getParameter("author_name");
            String isbn_number=request.getParameter("isbn_number");
            String lccn=request.getParameter("lccn");
            String ocln=request.getParameter("ocln");
            String filter_type=request.getParameter("filter_type");
            String book_name_url=book_name.replace(' ', '+');
            String author_name_url=author_name.replace(' ', '+');
           
            
            String isbn_number_url=isbn_number.replace(' ', '+');
            String filter_type_url=filter_type.replace(' ', '+');
            String query="";
            if(author_name_url.isEmpty()==false && isbn_number_url.isEmpty()==false){
               
           
            query="https://www.googleapis.com/books/v1/volumes?q="+book_name_url+"+iauthor:"+author_name_url+"+isbn:"
                    +isbn_number_url+"&key="+mykey+"&prettyPrint=true&filter="+filter_type_url;
            }
            
            else if(author_name_url.isEmpty()==true && isbn_number_url.isEmpty()==false){
            query="https://www.googleapis.com/books/v1/volumes?q="+book_name_url+"+isbn:"
                    +isbn_number_url+"&key="+mykey+"&prettyPrint=true&filter="+filter_type_url;
            }
            else if(author_name_url.isEmpty()==false && isbn_number_url.isEmpty()==true){
             query="https://www.googleapis.com/books/v1/volumes?q="+book_name_url+"+iauthor:"
                    +author_name_url+"&key="+mykey+"&prettyPrint=true&filter="+filter_type_url; 
            }
            else{
             query="https://www.googleapis.com/books/v1/volumes?q="+book_name_url+"&key="+mykey+"&prettyPrint=true&filter="+filter_type_url;
            }
            
           out.println(query+"\n");
            URL url = new URL(query);
            
            // Get the response
          InputStream inst=url.openStream();
          InputStreamReader isr = new InputStreamReader(inst);
          BufferedReader rd = new BufferedReader(isr);
          
          String line = "";
          while ((line = rd.readLine()) != null) {
            output.append(line);
            }
         // out.println(output.toString());
          String jsonline=output.toString();
          System.out.println("before try");
          //out.println(jsonline);
                    try {
                        System.out.println("start");
                        JSONObject jsonObj=new JSONObject(jsonline);
                        int totalItems=jsonObj.getInt("totalItems");
                                   
                       System.out.println(totalItems);
                        out.println("<h3>Here is the result of your search</h3>");
                        //out.println(totalItems+" books have been found!");
                           String[] keyAttributes = new String[totalItems];
                            
                        
               
                           JSONArray jsonArray=jsonObj.getJSONArray("items");
                            int json_size=jsonArray.length();
                            for(int i=0;i<json_size;i++){
                               JSONObject jsonItem = jsonArray.getJSONObject(i);
                               JSONObject volumeInfo=jsonItem.getJSONObject("volumeInfo");
                               JSONObject imgLinks=volumeInfo.getJSONObject("imageLinks");
                               
                               out.println("<br />");
                               out.println("<table border='1'><tr><td class='headertd'> No. </td><td class='headertd'> Title </td><td class='headertd'> author </td>"
                                       + "<td class='headertd'> Published Date </td>");
                               out.println("<tr><td>["+(i+1)+"]</td><td>"+volumeInfo.getString("title")+"</td>");
                               JSONArray authorArray=volumeInfo.getJSONArray("authors");
                               int authors_size=authorArray.length();
                               out.println("<td>");
                               for(int j=0;j<authors_size;j++)
                               { out.println(authorArray.getString(j)+"\n");}
                               out.println("</td>");
                               out.println("<td>"+volumeInfo.getString("publishedDate")+"</td></tr>");
                               if(volumeInfo.has("description")){
                                   out.println("<tr><td colSpan=3>"+volumeInfo.getString("description")+"</td>");
                               }
                               else{
                                  out.println("<tr><td colSpan=3>There is no description</td>");
                               }
                               
                               out.println("<td style='width:128px'><img src='"+imgLinks.getString("thumbnail") +"'/>");
                               out.println("<p><a href='"+volumeInfo.getString("previewLink")+"' class='links'>Preview</a></p>");
                               out.println("</td></tr></table>");
                              // out.println("[" + i + "]=" + jsonItem.getString(""));
                               
                            }
                            
                    } catch (JSONException ex) {
                        Logger.getLogger(submit.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        
                    
                  
           
        } 
        catch(MalformedURLException mue){
          output.append("error in URL");
        }
        catch(IOException ie){
         output.append("error in IO");
        }
        finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
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
