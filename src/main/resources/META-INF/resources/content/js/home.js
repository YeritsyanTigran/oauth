var urlParams = new URLSearchParams(window.location.search);

var code = urlParams.get("code");
var tokenId = localStorage.getItem("tokenId");
if(code !=null){
    console.log("Started");
    localStorage.removeItem("tokenId");
    $.ajax({
        url:"/api/v1/code",
        method:"POST",
        data:{
            code:code
        },
        success:function(response){
            console.log(response);
            localStorage.setItem("tokenId",response);
            location.replace("/home");
        },
        error:function(){
            location.replace("/home");
        }
    });
}

var div = document.createElement("div");
div["id"] = "profileInfo";
var profileSub = document.createElement("H1");
profileSub["id"] = "profileSub";


if(tokenId!=null){
    $.ajax({
        url:"/api/v1/tokenId",
        method:"POST",
        data:{
            tokenId:tokenId
        },
        success:function(response){
            var appendedSub = document.getElementById("profileSub");
            if(appendedSub == null) {
                profileSub.innerText = "Your Sub Is: " + response.sub;
                div.appendChild(profileSub);
                document.body.appendChild(div);
            }else{
                appendedSub.innerText = "Your Sub Is: " +  response.sub;
            }
        },
        error:function(error){
            if(error.status == 403){
                refresh(tokenId);
            }
        }
    })


    function refresh(tokenId){
        $.ajax({
            url:"/api/v1/code/refresh",
            method:"POST",
            data:{
                tokenId:tokenId,
            },
            success:function(response){
                localStorage.setItem("tokenId",response.tokenId);
                var appended = document.getElementById("profileInfo");
                if(appended == null){
                    profileSub.innerText = "Your Sub Is: " +  response.sub;
                    div.appendChild(profileSub);
                    document.body.appendChild(div);
                }else{
                    var appendedSub = document.getElementById("profileSub");
                    appendedSub.innerText = response.sub;
                }

            },
            error:function(error){
              alert("Couldn't retrieve refresh token.Clearing the local storage");
              localStorage.removeItem("tokenId");
            }
        });
    }


}