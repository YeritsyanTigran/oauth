var urlParams = new URLSearchParams(window.location.search);

var code = urlParams.get("code");
var id = localStorage.getItem("id");
if(code !=null){
    console.log("Started");
    localStorage.removeItem("id");
    $.ajax({
        url:"/api/v1/code",
        method:"POST",
        data:{
            code:code
        },
        success:function(response){
            console.log(response);
            localStorage.setItem("id",response);
            location.replace("/home");
        },
        error:function(){
            location.replace("/home");
        }
    });
}

var div = document.createElement("div");
div["id"] = "profileInfo";
var profileName = document.createElement("H1");
profileName["id"] = "profileName";


if(id!=null){
    $.ajax({
        url:"/api/v1/user",
        method:"GET",
        data:{
            id:id
        },
        success:function(response){
            var appendedName = document.getElementById("profileName");
            console.log(response);
            if(appendedName == null) {
                profileName.innerText = "Your Name Is: " + response;
                div.appendChild(profileName);
                document.body.appendChild(div);
            }else{
                appendedName.innerText = "Your Name Is: " +  response;
            }
        },
        error:function(error) {
            localStorage.removeItem("id");
            console.log("Error occurred");
        }
    });


}