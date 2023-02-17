console.log("js working")

const toggleSidebar=()=>{
    if($(".sidebar").is(":visible")){
        //true (close sidebar)

        $(".sidebar").css("display","none");
        $(".content").css("margin-left","0%");
    }else{
        //false (open sidebar)
        $(".sidebar").css("display","block");
        $(".content").css("margin-left","20%");
    }
};