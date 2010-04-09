<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<% response.setHeader("loginPage", "./login.jsp"); %>


<form name="fmLogin" method="POST" action="j_spring_security_check"> 
 <table border="0" cellpadding="1" cellspacing="0" style="width:340px"> 
 <tbody>

 <tr>
     <td>Uzytkownik: czesio, pass: test</td>
 </tr>
 
 <tr>

 <td align="right" nowrap="true">Username:&nbsp;</td>
 <td><input id="userName" type="text" name="j_username" value="" size="20" maxlength="100" class="control"></td>

 <td>&nbsp;</td>
 </tr>
 <tr>
 <td align="right" nowrap="true">Password:&nbsp;</td> 
 <td><input id="password" type="password" name="j_password" value="" size="20" maxlength="100" class="control"></td> 
 <td>&nbsp;</td> 
 </tr> 
         <tr>
                 <td>
                         <input id="submit" type="submit" value="OK"/>
                 </td>
         </tr>
 </tbody> 
 </table> 
 </form> 