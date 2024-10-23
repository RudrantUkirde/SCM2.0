console.log("admin user");

document.querySelector("#image_file_input").addEventListener('change',function(e){

    let file=e.target.files[0];
    let reader=new FileReader();

    reader.onload=function(){
        document.getElementById("upload_image_preview").src=reader.result;
    };

    reader.readAsDataURL(file);
})
