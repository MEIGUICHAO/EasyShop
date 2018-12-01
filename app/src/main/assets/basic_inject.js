

//搜索页面同款链接
function findSameStyle(productname){
    var similars = document.getElementsByClassName("similars");
    localMethod.JI_LOG(similars.length);
    var array=new Array()

    for(var i=0;i<similars.length;i++){
        var btn = similars[i].getElementsByTagName("a");
        if(btn[0].href.length>0){
            array[array.length] = btn[0].href;
        }
    }
    localMethod.insertSameStyleUrls(productname,array);

}