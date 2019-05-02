

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
//    var element = document.getElementsByClassName("sw-layout-1190");
    var element = document.getElementsByClassName("imgofferresult-mainBlock");
    localMethod.JI_LOG("getAliTao!!!!!!"+element.length);
    var pagingNum = document.getElementsByClassName("fui-paging-num")[0];
    var tagElement = document.getElementsByClassName("left-tag");
    var saleElement = document.getElementsByClassName("sm-offer-trade sw-dpl-offer-trade sm-offer-tradeBt");
    var hrefElement = document.getElementsByClassName("s-widget-offershopwindowtitle sm-offer-title sw-dpl-offer-title sm-widget-offershopwindowtitle-onerow");
    localMethod.setPagingNum(pagingNum.innerText);

    var urls = "";
    for(var i=0;i<saleElement.length;i++){

        var emTag = saleElement[i].getElementsByTagName("em");
        if(emTag.length>0){
            em = emTag[0].innerText;
        }
        if(tagElement[i].innerText.indexOf("一件代发") != -1&& em > 50){
            var aTag = hrefElement[i].getElementsByTagName("a");
            if(aTag.length>0){
                hrefUrl= aTag[0].getAttribute('href');
                if(urls.length==0){
                    urls = hrefUrl;
                } else {
                    urls = urls + "\n" + hrefUrl;
                }
            }
        }
    }
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


function login1688(){
    localMethod.JI_LOG("!!!!!!login1688");
    var html = document.getElementsByTagName("html");

    var loginBox = html[0].getElementsByTagName("body");
    localMethod.JI_LOG("loginBox:"+loginBox.length);
    var accounts = loginBox[0].getElementsByClassName("login-box no-longlogin module-static");

//    var accounts = loginBox[0].getElementById("TPL_username_1");
    localMethod.JI_LOG("accounts:"+accounts.length);
    var psws = loginBox[0].getElementById("TPL_password_1");
    localMethod.JI_LOG("psws:"+psws.length);

    accounts.value ="蘑菇海岸梅干菜";
    psws.value ="025684MGC770706";
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



function getSrcByClassName(){
    localMethod.JI_LOG("!!!!!!");
    var block = document.getElementsByClassName("block-mid-lis ");
    localMethod.JI_LOG("block:"+block.length);

//    var element = document.getElementsByClassName("lis-imgBox-img");
//    var titleElement = document.getElementsByClassName("mid-lis-name");
    var urls = "";
    var titles = "";
    for(var i=0;i<block.length;i++){
        var mDocument = block[i].getElementsByClassName("lis-imgBox-img");
        if(mDocument.length>0){
            var lead = block[i].getElementsByClassName("lis-lead not-dragging");
            if(lead.length<1){
                var element = block[i].getElementsByClassName("lis-imgBox-img");
                var titleElement = block[i].getElementsByClassName("mid-lis-name");
                var hrefUrl= element[0].getAttribute('src');
                hrefUrl = hrefUrl.replace("jpg_160x160","jpg_32x32");

                if(urls.length==0){
                    urls = hrefUrl;
                } else {
                    urls = urls + "\n" + hrefUrl;
                }

                var title= titleElement[0].getAttribute('title');
                if(titles.length==0){
                    titles = title;
                } else {
                    titles = titles + "\n" + title;
                }
            }
        }
    }
    localMethod.JI_LOG(urls);
    localMethod.JI_LOG(titles);
    localMethod.addPicSpaceResult(urls,titles);
    var nextArea = document.getElementsByClassName("itemList-bottom ");
    if(nextArea.length>0){
        localMethod.JI_LOG("nextArea:"+nextArea.length);
        var next = nextArea[0].getElementsByClassName("next");
        var nextDiasble = nextArea[0].getElementsByClassName("next disable1");
        if(next.length>0){
            next[0].click();
            if(nextDiasble.length<1){
                localMethod.next();
            }
        }
    }

}


function filterWorld(className){
    localMethod.JI_LOG("!!!!!!");
    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    var spans = element[0].getElementsByTagName("span")
    localMethod.JI_LOG("spans:"+spans.length);
    for(var i=0;i<spans.length;i++){
        localMethod.JI_LOG("!!!!!!"+i);
        spans[i].value="";
        spans[i].innerText="";
        localMethod.JI_LOG(className+":"+spans[i].value);
        localMethod.JI_LOG(className+":"+spans[i].innerText);
        localMethod.JI_LOG("end"+i);
    }
//    element[0].click();
}



function findMoblieImgLength(className){
    localMethod.JI_LOG("!!!!!!");
    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    var imgs = element[0].getElementsByTagName("img")
    localMethod.JI_LOG("imgs:"+imgs.length);
//    element[0].click();
}


function findElementsByClassName(className){
    localMethod.JI_LOG("!!!!!!");
    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    for(var i=0;i<element.length;j++){
        localMethod.JI_LOG(className+":"+element[i].value);
    }
//    element[0].click();
}

function findElementsById(className){
    localMethod.JI_LOG("!!!!!!");
    var element = document.getElementById(className);
    localMethod.JI_LOG(className+":"+element.length);

    localMethod.JI_LOG("!!!!!!");
    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    for(var i=0;i<element.length;j++){
        localMethod.JI_LOG(className+":"+element[i].value);
    }
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


function setInputValue(className,inputvalue){

    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    if(element.length>0){
        var tag = element[0].getElementsByTagName("input");
        localMethod.JI_LOG("tag:"+tag.length);
        if(tag.length>0){
            tag[0].value=inputvalue;
            tag[0].click();
        }
    }
    localMethod.showKeyboardB4Input();
}



function getSrcAttrByTagName(className,attr){

    var sku = document.getElementsByClassName("obj-sku");
    if(sku.length>0){
        var expand = sku[0].getElementsByClassName("obj-expand");
        if(expand.length>0){
            expand[0].click;
        }
    }

    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    var urls = "";
    var shopName = "";
    if(element.length>0){
        var tag = element[0].getElementsByTagName("img");
        localMethod.JI_LOG("tag:"+tag.length);
        for(var j=0;j<tag.length;j++){
            if(urls.length==0){
                urls = tag[j].getAttribute("src");
                shopName = tag[j].getAttribute(attr);
            } else {
                urls = urls + "###" + tag[j].getAttribute("src");
                shopName = shopName + "###" + tag[j].getAttribute(attr);

            }
            localMethod.JI_LOG(tag[j].getAttribute("src"));
            localMethod.JI_LOG(tag[j].getAttribute(attr));
        }
        localMethod.get1688details(urls,shopName);
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
