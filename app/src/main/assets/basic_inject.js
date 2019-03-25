

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


//小二滑动
function slide(){
    localMethod.JI_LOG("slide!!!!!!");
    var slideBtn = document.getElementById("nc_1_n1z");
    localMethod.JI_LOG("slideBtn!!!!!!");

    var pos = slideBtn.getBoundingClientRect();
    localMethod.JI_LOG("pos!!!!!!");
    var position = "top:"+pos.top +
      "left:"+pos.left +
      "bottom:"+pos.bottom +
      "right:"+pos.right +
      "width:"+pos.width +
      "height:"+pos.height;
      localMethod.JI_LOG(position);
      localMethod.slideTouch(pos.left,pos.width,pos.bottom);
}


function test(){
    localMethod.JI_LOG("!!!!!!test");
}


function login(){
    var accounts = document.getElementById("TPL_username_1");
    var psws = document.getElementById("TPL_password_1");
    accounts.value ="大王派我来巡山23333:鬼鬼";
    psws.value ="m12345678";
}


function getDocument(){
    var mHtml = document.getElementsByTagName('script');

    for(var i=0;i<mHtml.length;i++){
        localMethod.JI_LOG("innerText!!!!!!"+mHtml[i].innerText);
        if(mHtml[i].innerText.indexOf("g_page_config") != -1 ){
            localMethod.JI_LOG("script!!!!!!"+mHtml[i].innerText);
        }
//        if($.trim(a)){
//
//        }
    }

}



function findElementsByClassName(className){
    localMethod.JI_LOG("!!!!!!");
    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    localMethod.JI_LOG(className+":"+element[0].value);
    element[0].click();
}



function foreachTable(tableName,page){

    setTimeout(function(){

    var pageNo = document.getElementsByClassName("ui-page-no ");
    localMethod.JI_LOG("pageNo:"+pageNo[pageNo.length-2].innerText+"");
    var element = document.getElementsByClassName(tableName);
    localMethod.JI_LOG(tableName+":"+element.length);
    var tbody = element[0].getElementsByTagName("tbody");
    localMethod.JI_LOG("tbody"+":"+tbody.length);
    var tr = tbody[0].getElementsByTagName("tr");
    localMethod.JI_LOG("!!!!!!");
    for(var j=0;j<tr.length;j++){
       var td = tr[j].getElementsByTagName("td");
//       if(td[1]>200&&accDiv(td[2],td[1])<3){
//       }
            localMethod.JI_LOG("关键词:"+td[0].innerText+"，搜索指数："+td[1].innerText+"，在线宝贝："+td[2].innerText);
    }
        localMethod.JI_LOG("setTimeout!!!!!!");
        if(page<pageNo[pageNo.length-2].innerText){
            localMethod.JI_LOG("setTimeout2!!!!!!");
            pageNo[pageNo.length-1].click();
            foreachTable(tableName,page+1);
        }
    },3000);

}



//乘法
function accMul(arg1,arg2){
 try{
    var m=0,s1=arg1.toString(),s2=arg2.toString();
    try{
        m+=s1.split(".")[1].length
        }catch(e){
        }
    try{
        m+=s2.split(".")[1].length
        }catch(e){
        }
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
 }catch(e){
    return 0;
 }
}

//除法
 function accDiv(arg1,arg2){
 try{
    var t1=0,t2=0,r1,r2;
    try{
        t1=arg1.toString().split(".")[1].length
        }catch(e){}
    try{
        t2=arg2.toString().split(".")[1].length
        }catch(e){}
    with(Math){
        r1=Number(arg1.toString().replace(".",""));
        r2=Number(arg2.toString().replace(".",""));
        return (r1/r2)*pow(10,t2-t1);
    }
 }catch(e){
    return 0;
 }
}
