<%-- 
    Document   : index
    Created on : 2012-11-12, 23:11:33
    Author     : lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/style.css" type="text/css"/>
        <script type="text/javascript">
            function show_books(){
                
                var book_name=document.getElementById('book_name').value;
              
                var author_name=document.getElementById('author_name').value;
                var isbn_number=document.getElementById('isbn_number').value;
                var filter_type=document.getElementById('filter_type').value;
                if(book_name==""){
                    alert("Please enter the name of the book you are looking for");
                }
                else{
                if (window.XMLHttpRequest)
                {// code for IE7+, Firefox, Chrome, Opera, Safari
                       xmlhttp=new XMLHttpRequest();
                }
                else
                {// code for IE6, IE5
                       xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                       xmlhttp.onreadystatechange=function()
                       {
                          if (xmlhttp.readyState==4 && xmlhttp.status==200)
                          {
                             document.getElementById("content").innerHTML=xmlhttp.responseText;
                          }
                       }
                      xmlhttp.open("POST","submit",true);
                      xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
                      xmlhttp.send("book_name="+book_name+"&author_name="+author_name+"&isbn_number="+isbn_number+"&filter_type="+filter_type);
               }
            }
        </script>
    </head>
    <body>
        <div id="container" name="container" align="center">
        <div id="header">
            <img src="pic/header.jpg" alt="img_header"/>
        </div>
        <div id="leftside">
            <ul id="navigation">
                <li>
                    <a href="http://books.google.com/">Google Book</a>
                </li>
                <li>
                    <a href="http://library.pitt.edu/">Library System of Upitt</a>
                </li>
                <li>
                    <a href="https://play.google.com/store/books/collection/topselling_free?hl=en">Top Free Books</a>
                </li>
                
            </ul>
                
        </div>
            <div id="rightside">
                <div id="bar">
                    <h3>Find a book through Google Books API:</h3>
                      <label>The name of the book: </label>
                      <input type="text" name="book_name" id="book_name" maxlength="50"/><br>
                      <label>The author of the book: </label>
                      <input type="text" name="author" maxlength="50" id="author_name"/><br>
                      <label>ISBN number if you know: </label>
                      <input type="text" name="isbn" maxlength="50" id="isbn_number"/><br>
                      <label>Filter the result by the type of book:</label>
                      <select size="1" name="filter_type" id="filter_type">
                          <option value="partial">partial</option>
                          <option value="full">full</option>
                          <option value="free-ebooks">free-ebooks</option>
                          <option value="paid-ebooks">paid-ebooks</option>
                          <option value="ebooks">ebooks</option>
                      </select><br>
                      <input type="button" name="submit" value="SUBMIT" style="cursor:pointer" onclick="show_books()" />
                
                </div>
                <div id="content">
                </div>
            </div>
        </div>
    </body>
</html>
