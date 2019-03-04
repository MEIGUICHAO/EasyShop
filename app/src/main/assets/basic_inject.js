

//搜索页面同款链接
function findSameStyle(productname){
    localMethod.JI_LOG("!!!!!!");
    var similars = document.getElementsByClassName("similars");
    localMethod.JI_LOG(similars.length);
    var array=new Array()

    for(var i=0;i<similars.length;i++){
        var btn = similars[i].getElementsByTagName("a");
        if(btn[0].href.length>0){
            array[array.length] = btn[0].href;
            var pos = btn[0].getBoundingClientRect();
            var position = "top:"+pos.top +
              "left:"+pos.left +
              "bottom:"+pos.bottom +
              "right:"+pos.right +
              "width:"+pos.width +
              "height:"+pos.height;
              localMethod.JI_LOG(position);
              if(i==0){
                  localMethod.slideTouch(pos.left,pos.width,pos.bottom);
              }
        }
    }
    localMethod.insertSameStyleUrls(productname,array);

}


function jsCangkuGoNextPage(){
    localMethod.JI_LOG("jsCangkuGoNextPage");
    var selectors = document.getElementsByClassName("selector");
    var itemCates = document.getElementsByClassName("J_QRCode");
    localMethod.JI_LOG("selectors length:"+selectors.length);
    var cangkuCidIds = "";
    for(var j=0;j<selectors.length;j++){
        var itemids = selectors[j].getAttribute("itemids");
        var icat = itemCates[j].getAttribute("data-param").split("&cid=")[1].split("&title=")[0];
        if(cangkuCidIds==""){
            cangkuCidIds = itemids+"@@@"+icat;
        } else {
            cangkuCidIds = cangkuCidIds + "###" +itemids+"@@@"+icat;
        }
    }
    localMethod.cangkuList(cangkuCidIds+"");
//    localMethod.cangkuForeach();
//    if(selectors.length<20){
//        localMethod.cangkuForeach();
//    }


//    var nexts = document.getElementsByClassName("next-page");
//    if(nexts.length>0){
//        var as = nexts[0].getElementsByTagName("a");
//        localMethod.JI_LOG(as[0].innerText)
//        as[0].click();
//    } else {
//        localMethod.cangkuForeach();
//    }

}




function findElementsByClassName(className){
    localMethod.JI_LOG("!!!!!!");
    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    element[0].click();
}