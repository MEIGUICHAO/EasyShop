

//搜索页面同款链接
function findSameStyle(){
    var similars = document.getElementsByClassName("similars");
    localMethod.JI_LOG(similars.length);
    for(var i=0;i<similars.length;i++){
        var btn = similars[i].getElementsByTagName("a");
        if(btn[0].href.length>0){
            localMethod.JI_LOG(btn[0].href);
        }
    }

}