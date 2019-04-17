

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


function getAliTao(){
    localMethod.JI_LOG("getAliTao!!!!!!");
//    var element = document.getElementsByClassName("sw-layout-1190");
    var element = document.getElementsByClassName("imgofferresult-mainBlock");
    localMethod.JI_LOG(element.length);
    localMethod.JI_LOG(element[0].value);
    var pagingNum = document.getElementsByClassName("fui-paging-num")[0];
    var tagElement = document.getElementsByClassName("left-tag");
    var saleElement = document.getElementsByClassName("sm-offer-trade sw-dpl-offer-trade sm-offer-tradeBt");
    var hrefElement = document.getElementsByClassName("s-widget-offershopwindowtitle sm-offer-title sw-dpl-offer-title sm-widget-offershopwindowtitle-onerow");
    localMethod.JI_LOG("pagingNum:"+pagingNum.innerText);
    localMethod.setPagingNum(pagingNum.innerText);

    var urls = "";
    for(var i=0;i<tagElement.length;i++){
        if(saleElement.length<i+1){

            var emTag = saleElement[i].getElementsByTagName("em");
            if(emTag.length>0){
                em = emTag[0].innerText;
            }
            localMethod.JI_LOG("tagElement:"+i);
            localMethod.JI_LOG("tagElement[i].innerText:"+tagElement[i].innerText);
            localMethod.JI_LOG("em:"+em);
            if(tagElement[i].innerText.indexOf("一件代发") != -1&& em > 50){
                var aTag = hrefElement[i].getElementsByTagName("a");
                if(aTag.length>0){
                    hrefUrl= aTag[0].getAttribute('href');
                    localMethod.JI_LOG("placeHolder:0");
                    if(urls.length==0){
                        urls = hrefUrl;
                    } else {
                        urls = urls + "\n" + hrefUrl;
                    }
                    localMethod.JI_LOG("em:"+i);
                }
    //            localMethod.JI_LOG("tagElement:"+tagElement[i].innerText);
    //            localMethod.JI_LOG("hrefUrl:"+hrefUrl);
            }
        }
      localMethod.JI_LOG("placeHolder:next");
//        localMethod.JI_LOG("hrefStr:"+hrefStr);
//        localMethod.JI_LOG("hrefUrl:"+hrefUrl.length);
    }
    localMethod.JI_LOG("placeHolder:1");
    if(urls.length==0){
        urls = "placeHolder";
    }
    localMethod.JI_LOG("placeHolder:2");
    localMethod.getJsonData(urls+"");

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

    var needslide = true;
    for(var i=0;i<mHtml.length;i++){
//        localMethod.JI_LOG("innerText!!!!!!"+mHtml[i].innerText);
        if(mHtml[i].innerText.indexOf("g_page_config") != -1 ){
            needslide = false;
            localMethod.getJsonData("script!!!!!!"+mHtml[i].innerText);
        }
    }
    if(needslide){
        localMethod.slideTouch();
    }

}



function findElementsByClassName(className){
    localMethod.JI_LOG("!!!!!!");
    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    localMethod.JI_LOG(className+":"+element[0].value);
//    element[0].click();
}


function clickElementsByClassName(className){
    localMethod.JI_LOG("!!!!!!");
    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    if(element.length>0){
        localMethod.JI_LOG(className+":"+element[0].value);
        element[0].click();
        localMethod.afterClick();
    }
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
