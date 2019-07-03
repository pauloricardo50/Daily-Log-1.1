/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dailylog.Atividade;
import dailylog.Aviso;
import dailylog.Permissao;
import dailylog.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jardielma
 */
public class Servlet_listaUsuarios extends HttpServlet {

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
            String id_usuario_logado=""; 
            String nome_usuario_logado="";
            HttpSession session = request.getSession(false);

            if(session!=null){
                //verifico se tem permissão de acesso
                id_usuario_logado = session.getAttribute("sessao_id_usuario").toString();
                nome_usuario_logado = session.getAttribute("sessao_nome_usuario").toString();
            }
            else{
                request.getRequestDispatcher("./servlet_login").include(request, response);  
            }
            
            Usuario usuario = new Usuario();
            usuario.setId(Integer.parseInt(id_usuario_logado));
            usuario.buscar();
            //Verifico a permissão do usuário para essa página
            if(verificarPermissao(usuario)==false){
                response.sendRedirect("./servlet_errorPermissao");
            }
            String tabela_avisos = verificarAvisos(usuario);
            String tabela = listaUsuarios(tabela_avisos);
            
            //Montando o HTML
            String html ="";
            Atividade atividade = new Atividade();
            atividade.carregarHtmlTop(nome_usuario_logado);
            atividade.carregarHtmlFooter();
            html = atividade.getHtmlTop();
            html += tabela;
            //html += tabela_avisos;
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
    
    public String listaUsuarios(String mensagem){
        
        
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
                    "                                    <h2>Usuários</h2>\n" +
                    "                                    <span style=\"float:right;\"><h2><a href=\"./servlet_usuario\" class=\"button\">Criar novo usuario</a></h2>\n</span>"
                        + "<div class=\"clearfix\"></div>\n" +
                    "                                </div>\n" +
                    "                                <div class=\"x_content\">\n" ;
                retorno += mensagem; 
        retorno += "<span style=\"float:center;\"><h2>Lista de Usuarios do Sistema</h2>\n</span>"
                + "<table class=\"table table-hover\">\n"
                + "<tr>\n" +
                "    <th>ID</th>\n" +
                "    <th>NOME</th>\n" +
                "    <th>MATRICULA</th>\n" +
                "    <th>ATIVIDADES</th>\n" +
                "    <th>Opção</th>\n" +
                "  </tr>";
        Usuario user = new Usuario();
        ArrayList<Usuario> lista;
        lista = user.listar();
        for(Usuario linha:lista){
            retorno+="<tr>\n"
                    + "<td>"+linha.getId()+"</td>\n"
                    + "<td>"+linha.getNome()+"</td>\n"
                    + "<td>"+linha.getMatricula()+"</td>\n"
                    + "<td><a href=\"./servlet_listaAtividades?id_user="+linha.getId()+"\" >Atividades</a></td>\n"
                    + "<td><a href=\"./servlet_usuario?id_user="+linha.getId()+"\" >Editar</a></td>\n"
                    + "</tr>";
        }
        retorno += "                                        </tbody>\n" +
                    "                                    </table>\n" +
                    "\n" +
                    "                                </div>\n" +
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
    
    public boolean verificarPermissao(Usuario usuario){
        //Método que verifica se o usuário tem permissão nessa página
        //Lista Usuarios é permitido para PERFIL: Cadastro e Administrador Geral
        
        ArrayList<Permissao> lista;
        lista = usuario.getPerfil().getPemissoes();
        for(Permissao linha:lista){
            if(linha.getDescricao().equals("Total")){
                //out.println("Total");
                return true;
            }
            if(linha.getDescricao().equals("Registro de Usuarios")){
                
                //out.println("Reg.Usu");
                return true;
            }
        }        
        return false;
    }
    
    public String verificarAvisos(Usuario usuario_logado){
        
        
        String retorno = "<span style=\"float:center;\"><h2>Mensagens de Aviso importantes</h2>\n</span>"
                + "<table class=\"table table-hover\">\n"
                + "<tr>\n" +
                "    <th>ID</th>\n" +
                "    <th>Mensagem de Avisos</th>\n" +
                "  </tr>";
        Aviso aviso = new Aviso();
        aviso.setId_User(usuario_logado.getId());
        ArrayList<Aviso> lista;
        lista = aviso.listar();
        
        if(lista.size() == 0){
        return "";
        }
        
        for(Aviso linha:lista){
            retorno+="<tr>\n"
                    + "<td>"+linha.getId()+"</td>\n"
                    + "<td>"+linha.getMensagem()+"</td>\n"
                    + "</tr>";
            linha.setFlag_ativo("H");
            linha.salvar();
        }
        retorno += " </table>\n";
        //out.println(retorno);
        return retorno;
        
    }

}
