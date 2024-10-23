function getTheme(){

    let localTheme=localStorage.getItem("theme");

    if(localTheme==null){
        return "light";
    }else{
        return localTheme;
    }
}

function setTheme(theme){
    localStorage.setItem("theme", theme);
}

function changeTheme(){

    const button=document.getElementById("mode");
    const text=document.getElementById("textChange");

    //adding at the start
    document.querySelector("html").classList.add(startTheme);
    if(startTheme=="dark"){
        text.textContent="Light";
    }
    if(startTheme=="light"){
        text.textContent="Dark";
    }

    button.addEventListener("click",(e)=>{

        const oldTheme=startTheme;

        if(startTheme==="light"){
            startTheme="dark";
            text.textContent="Light";
        }else{
            startTheme="light";
            text.textContent="Dark";
        }

    setTheme(startTheme);

    document.querySelector("html").classList.remove(oldTheme);
    document.querySelector("html").classList.add(startTheme);

    });
}

let startTheme=getTheme();
document.addEventListener("DOMContentLoaded", changeTheme());