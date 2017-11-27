<%@ page
   language="java"
   import="com.articulate.sigma.*,java.text.ParseException,java.net.URLConnection,javax.servlet.ServletContext,javax.servlet.http.HttpServletRequest, java.net.URL,com.oreilly.servlet.multipart.MultipartParser,com.oreilly.servlet.multipart.Part,com.oreilly.servlet.multipart.ParamPart,com.oreilly.servlet.multipart.FilePart,java.util.*,java.io.*"
   pageEncoding="UTF-8"
   contentType="text/html;charset=UTF-8"
%>
<!DOCTYPE html
   PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "https://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<html xmlns="https://www.w3.org/1999/xhtml" lang="en-US" xml:lang="en-US">
<%

/** This code is copyright Teknowledge (c) 2003, Articulate Software (c) 2003-2017,
    Infosys (c) 2017-present.

    This software is released under the GNU Public License
    <http://www.gnu.org/copyleft/gpl.html>.

    Please cite the following article in any publication with references:

    Pease A., and Benzmüller C. (2013). Sigma: An Integrated Development Environment
    for Logical Theories. AI Communications 26, pp79-97.  See also
    http://github.com/ontologyportal
*/
ServletContext siblingContext = request.getSession().getServletContext().getContext("/sigma");
String username = (String) siblingContext.getAttribute("user");
String role = (String) siblingContext.getAttribute("role");
String URLString = request.getRequestURL().toString();
String pageString = URLString.substring(URLString.lastIndexOf("/") + 1);
KBmanager mgr = KBmanager.getMgr();
System.out.println("PreludeNLP.jsp: username:role  " + username + " : " + role);

String hostname = KBmanager.getMgr().getPref("hostname");
if (hostname == null)
    hostname = "localhost";
String port = KBmanager.getMgr().getPref("port");
if (port == null)
    port = "8080";

if (StringUtil.emptyString(role)) { // role is [guest | user | admin]
    role = "guest";
}

if (!KBmanager.initialized) {
    KBmanager.getMgr().initializeOnce();
    System.out.println("Prelude.jsp: initializing.  Redirecting to init.jsp.");
    response.sendRedirect(HTMLformatter.createHrefStart() + "/sigma/init.jsp");
    return;
}

if (!role.equalsIgnoreCase("admin")) {
    mgr.setError("You are not authorized to visit " + pageString);
    response.sendRedirect(HTMLformatter.createHrefStart() + "/sigma/login.html");
    return;
}

%>