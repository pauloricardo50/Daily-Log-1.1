/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceUsuario;

import Dominio.Atividade;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jardielma
 */
public class Servlet_logout extends HttpServlet {

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
        String id_usuario_logado="";
        String nome_usuario_logado="";
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
             HttpSession session = request.getSession(false);
            if(session!=null){
                //verifico se tem permissão de acesso
                id_usuario_logado = session.getAttribute("sessao_id_usuario").toString();
                nome_usuario_logado = session.getAttribute("sessao_nome_usuario").toString();
            }
            else{
                request.getRequestDispatcher("./servlet_login").include(request, response);  
            }
                    //busca informações do form
        String mensagem;
            mensagem = request.getParameter("mensagem_error");
            if(mensagem==null){
                mensagem = "";
            }
            String html ="";
            Atividade atividade = new Atividade();
            atividade.carregarHtmlTop(nome_usuario_logado);
            atividade.carregarHtmlFooter();
            html = atividade.getHtmlTop();
            html +=preparaHtmlLogin(mensagem);
            html += atividade.getHtmlFooter();
            out.println(html);
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
        processRequest(request, response);
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
        HttpSession session = request.getSession(false);
        session.invalidate();
        response.sendRedirect("./servlet_login?mensagem=Sessao Finalizada com sucesso");
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

    
        public String preparaHtmlLogin(String mensagem){
            
        
        String retorno = "";

                retorno = "<div class=\"right_col\" role=\"main\" style=\"min-height: 1775px;\">\n" +
                    "                <div class=\"row\">\n" +
                    "                    <div class=\"col-md-12 col-sm-12 col-xs-12\">\n" +
                    "                        <!-- Calendário -->\n" +
                    "                        <div class=\"row x_title\">\n" +
                    "                            <div class=\"col-md-6\">\n" +
                    "                                <h2>\n" +
                    "                                    <div id=\"data\"></div>\n" +
                    "                                </h2>\n" +
                    "                            </div>\n" +
                    "                            <div class=\"col-md-6\">\n" +
                    "                                <div id=\"reportrange\" class=\"pull-right\" style=\"background: #fff; cursor: pointer; padding: 5px 10px; border: 1px solid #ccc\">\n" +
                    "                                    <i class=\"glyphicon glyphicon-calendar fa fa-calendar\"></i>\n" +
                    "                                    <span></span> <b class=\"caret\"></b>\n" +
                    "                                </div>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                        <!-- /Calendário-->\n" +
                    "                        <!--conteudo-->\n" +
                    "                        <div class=\"col-md-8 col-sm-8 col-xs-12\">\n" +
                    "                            <div class=\"x_panel\">\n" +
                    "                                <div class=\"x_title\">\n" +
                    
                    "                                    <span style=\"float:center;\"><h2>Fazer Login</h2>\n</span>"
                        + "<div class=\"clearfix\"></div>\n" +
                    "                                </div>\n" +
                    "                                <div class=\"x_content\">\n" ;
                retorno +=
                "            <form action=\"servlet_logout\" method=\"POST\">\n" +
                "                <div class=\"row\">\n" +
                                        "                    <div class=\"col-xs-8\">\n" +
"                        <label>Deseja Fazer Logout do Sistema?</label>\n" +
                
                "                    </div>\n" +
                "                </div>\n" ;
                if(mensagem.length()>0){
                retorno +="                <div class=\"row\">\n" +
                "                    <div class=\"col-xs-6\">\n" +

                "<label>Mensagem</label>\n" +
                "                        <span style=\"float:center;\"><h2>"+mensagem+"</h2>\n</span>" +
                "                    </div>\n" +
                "                </div>\n" ;
                }
                retorno +=
                "                <div class=\"row\">\n" +
                "                    <div style=\"margin-top: 2%; margin-left: 42.7%\">\n" +
                        
                "                        <button type=\"submit\" class=\"btn btn-primary\">Logout</button>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </form>\n" ;
                
                retorno += "                                </div>\n" +
                    "                            </div>\n" +
                    "                        </div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "                <br>\n" +
                    "            </div>"
                + "        </div>\n" +
        "    </div>";
    return retorno;
    }
}
