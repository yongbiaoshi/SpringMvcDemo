<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>Home</title>
<script type="text/javascript" src='<c:url value="/resources/js/jquery-1.12.0.min.js"></c:url>'></script>
<script type="text/javascript">
  $(function(){
    var url = '<c:url value="/excel/area/11"></c:url>';
    $.ajax({
      url: url,
      dataType: "xml",
      success: function(data){
          $("#output-xml").text($($(data).children()[0])[0].outerHTML);
      }
    });
    $.ajax({
        url: url,
        dataType: "json",
        success: function(data){
            console.log(data);
            $("#output-json").text(JSON.stringify(data));
        }
      });
  });
</script>
</head>
<body>
  <h1>Hello world!</h1>

  <P>The time on the server is ${serverTime}.</P>
  <h4>Xml Request</h4>
  <p id="output-xml"></p>
  <h4>Json Request</h4>
  <p id="output-json"></p>
</body>
</html>