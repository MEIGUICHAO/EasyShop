

//搜索页面同款链接
function findSameStyle(productname){
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