

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

function getAliPageCount(){
    var pagingList = document.getElementsByClassName("fui-paging-list");
    localMethod.JI_LOG("pagingList:"+pagingList.length);
    var page = pagingList[0].getElementsByTagName("a");
    localMethod.JI_LOG("page:"+pagingList.length);
    localMethod.JI_LOG("page:"+page.length);
//    localMethod.JI_LOG("page_value:"+page[page.length-2].value);
    localMethod.JI_LOG("page_innerText:"+page[page.length-2].innerText);
    localMethod.getJsonData(page[page.length-2].innerText);
}


function getPicSpacesSpecUrl(){
    var element = document.getElementsByClassName("table-td-width");
    localMethod.JI_LOG("!!!!!!"+element.length);
    if(element.length>0){
        var a = element[0].getElementsByTagName("a");
        localMethod.JI_LOG("a!!!!!!"+a.length);
        if(a.length>0){
            var href = a[0].getAttribute("href");
            localMethod.getJsonData(href);
        } else {
            localMethod.errorOccur();
        }
    } else {
        localMethod.errorOccur();
    }
}


function checkOfficPublishResult(){
//    content-header-icon
    var element = document.getElementsByClassName("content-header");
    localMethod.JI_LOG("checkOfficPublishResult_element:"+element.length);
    if(element.length>0){
        var text = element[0].innerText;
        localMethod.JI_LOG(text);
        if(text.length<1){
            localMethod.afterClick();
        } else {
            if(text.indexOf("很抱歉，铺货失败")!=-1){
                localMethod.errorOccur();
            } else {
                localMethod.afterClick();
            }
        }
    }
}

function getAliTao(){
//    var element = document.getElementsByClassName("sw-layout-1190");
    var element = document.getElementsByClassName("imgofferresult-mainBlock");
    localMethod.JI_LOG("getAliTao!!!!!!"+element.length);
    var pagingNum = document.getElementsByClassName("fui-paging-num");
    var tagElement = document.getElementsByClassName("left-tag");
    var saleElement = document.getElementsByClassName("sm-offer-trade sw-dpl-offer-trade sm-offer-tradeBt");
    var hrefElement = document.getElementsByClassName("s-widget-offershopwindowtitle sm-offer-title sw-dpl-offer-title sm-widget-offershopwindowtitle-onerow");

    if(pagingNum.length>0){
        localMethod.setPagingNum(pagingNum[0].innerText);
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
                    } else if(hrefUrl.indexOf("sk=consign") != -1&&hrefUrl.length>0){
                        urls = urls + "\n" + hrefUrl;
                    }
                }
            }
        }
        localMethod.getJsonData(urls+"");

    } else {
        localMethod.errorOccur();
    }

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
    accounts.value ="蘑菇海岸梅干菜";
//    psws.value ="m12345678";
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

//    accounts.value ="蘑菇海岸梅干菜";
    accounts.value ="花花草草kf:鬼鬼";
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



function getSrcByClassName(needJude){

    var mHtml = document.getElementsByTagName('script');

    var needslide = true;
    for(var i=0;i<mHtml.length;i++){
//        localMethod.JI_LOG("innerText!!!!!!"+mHtml[i].innerText);
        if(mHtml[i].innerText.indexOf("g_page_config") != -1 ){
            needslide = false;
            localMethod.getJsonData("script!!!!!!"+mHtml[i].innerText);
        }
    }
    if(!needslide||needJude.indexOf("false")!=-1){

        localMethod.JI_LOG("!!!!!!");
        var block = document.getElementsByClassName("block-mid-lis ");
        localMethod.JI_LOG("block:"+block.length);

    //    var element = document.getElementsByClassName("lis-imgBox-img");
    //    var titleElement = document.getElementsByClassName("mid-lis-name");
        if(block.length>0){
            var urls = "";
            var titles = "";
            for(var i=0;i<block.length;i++){
                var mDocument = block[i].getElementsByClassName("lis-imgBox-img");
                if(mDocument.length>0){
                    localMethod.JI_LOG(i+"mDocument:"+mDocument.length);
                    var lead = block[i].getElementsByClassName("lis-lead not-dragging");
                    if(lead.length<1){

                    }
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
                    } else {
                        localMethod.getJsonData("获取图片空间图片完成");
                    }
                } else {
                    localMethod.getJsonData("获取图片空间图片完成");
                }

            }
        } else {
            localMethod.errorOccur();
        }
    } else {
        localMethod.JI_LOG("小二来了!!!!!!");
    }

}


function selectAllPic(){
    localMethod.JI_LOG("!!!!!!");

    var block = document.getElementsByClassName("block-mid-lis ");
    if(block.length<2){
        localMethod.errorOccur();
    } else {
        var allChoose = document.getElementsByClassName("allChoose not-dragging");
        allChoose[0].click();
        localMethod.afterClick();
    }
//    for(var i=0;i<block.length;i++){
//        var mDocument = block[i].getElementsByClassName("lis-imgBox-img");
//        if(mDocument.length<1){
//    //        lis-img-sele doing-sele not-dragging lis-all-shift
//            var shift = block[i].getElementsByClassName("lis-img-sele doing-sele not-dragging lis-all-shift");
//            localMethod.JI_LOG("shift:"+shift.length);
//            var choose = shift[0].getElementsByClassName("Check iconfont");
//            localMethod.JI_LOG("choose:"+choose.length);
//            var hover = block[i].getElementsByClassName("mid-lis-img ");
//            localMethod.JI_LOG("hover:"+choose.length);
//            hover[0].click();
//            choose[0].click();
//            shift[0].click();
//            block[i].click();
//        }
//    }

}


function filterWorld(className){
    localMethod.JI_LOG("!!!!!!");
    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    var spans = element[0].getElementsByTagName("span")
    var divs = element[0].getElementsByTagName("div")
    var ps = element[0].getElementsByTagName("p")
    setTagValue(spans);
    setTagValue(divs);
    setTagValue(ps);
    localMethod.getJsonData("filterWorld");
//    element[0].click();
}


function setChildInputValueByClassName(parentName,parentPosition,position,inputvalue){
    var parentElement = document.getElementsByClassName(parentName);
    parentElement[parentPosition].focus();
    parentElement[parentPosition].click();

    localMethod.JI_LOG(parentName+":"+parentElement.length);
    var childElement = parentElement[parentPosition].getElementsByTagName("input");
    childElement[position].value = inputvalue;
    localMethod.showKeyboardB4Input();
}

function setTagValue(tags){
    localMethod.JI_LOG("tags:"+tags.length);
    for(var i=0;i<tags.length;i++){
        var imgs = tags[i].getElementsByTagName("img");
        if(imgs.length<1){
            localMethod.JI_LOG("!!!!!!"+i);
            tags[i].value="";
            tags[i].innerText="";
            localMethod.JI_LOG("value:"+tags[i].value);
            localMethod.JI_LOG("innerText:"+tags[i].innerText);
            localMethod.JI_LOG("end"+i);
            tags[i].click();
        } else {
            imgs[0].click();
        }
    }
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

function checkOfficeAvailable(){
    var tools = document.getElementsByClassName("tool-name");
    localMethod.JI_LOG("tools:"+tools.length)
    if(tools.length>0){
        localMethod.getJsonData("checkOfficeAvailable");
    } else {
        localMethod.errorOccur();
    }
}


function clickElementsByClassName(className){
    localMethod.JI_LOG("clickElementsByClassName!!!!!!");
    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    if(element.length>0){
        localMethod.JI_LOG("element[0].click()");
        element[0].click();
        localMethod.JI_LOG("localMethod.afterClick");
        localMethod.afterClick();
        localMethod.JI_LOG(className+":"+element[0].value);
    } else {
        localMethod.errorOccur();
    }
}

function clickElementsByClassNameWithoutAfterClick(className){
    localMethod.JI_LOG("clickElementsByClassName!!!!!!");
    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    if(element.length>0){
        localMethod.JI_LOG("element[0].click()");
        element[0].click();
        localMethod.JI_LOG("localMethod.afterClick");
//        localMethod.afterClick();
        localMethod.JI_LOG(className+":"+element[0].value);
    } else {
        localMethod.errorOccur();
    }
}

function advanceSearchClick(){
    var element = document.getElementsByClassName("next-radio-inner unpress");
    var label = document.getElementsByClassName("next-radio-label");
    localMethod.JI_LOG("next-radio-inner unpress:"+element.length);
    if(element.length>0){
        if(label.length>0){
            var text = label[0].innerText;
            if(text.indexOf("按宝贝名称搜索")==-1){
                localMethod.JI_LOG("element[0].click()");
                element[0].click();
                localMethod.JI_LOG("localMethod.afterClick");
            }
        }
//        localMethod.afterClick();
        localMethod.JI_LOG("label:"+label[0].value);
    } else {
        localMethod.errorOccur();
    }
}


function saveDraft(){
    var element = document.getElementsByClassName("next-btn next-btn-normal next-btn-large");
    localMethod.JI_LOG("className:"+element.length);
    if(element.length>0){
        element[1].click();
        localMethod.JI_LOG("localMethod.afterClick");
        setInputValue("next-input next-input-single next-input-medium draft-ipt","test");
    }
}


function goEditDetailArea(){
    localMethod.JI_LOG("goEditDetailArea!!!!!!");
    var element = document.getElementsByClassName("next-menu-item");

    for(var j=0;j<element.length;j++){
        localMethod.JI_LOG("innerText:"+element[j].innerText);

        if(element[j].innerText.indexOf("图文描述")!=-1){
            element[j].click();
            localMethod.getJsonData("图文描述");
        }
    }
}

function goBaseInfoArea(){
    localMethod.JI_LOG("goEditDetailArea!!!!!!");
    var element = document.getElementsByClassName("next-menu-item");

    for(var j=0;j<element.length;j++){
        localMethod.JI_LOG("innerText:"+element[j].innerText);

        if(element[j].innerText.indexOf("基础信息")!=-1){
            element[j].click();
            localMethod.getJsonData("基础信息");
        }
    }
}

function goSaleInfoArea(){
    localMethod.JI_LOG("goEditDetailArea!!!!!!");
    var element = document.getElementsByClassName("next-menu-item");

    for(var j=0;j<element.length;j++){
        localMethod.JI_LOG("innerText:"+element[j].innerText);

        if(element[j].innerText.indexOf("销售信息")!=-1){
            element[j].click();
        }
    }
}


function getPublishItemId(){
    var element = document.getElementsByClassName("item-title");
    localMethod.JI_LOG("item-title:"+element.length);
    var title = document.getElementsByClassName("offer-title ");
    localMethod.JI_LOG("title:"+title.length);

    if(element.length>0&&title.length>0){
        localMethod.JI_LOG("title:"+title[0].innerText);
        if(element.length>1){
            localMethod.errorOccur();
        } else {
            var itemA = element[0].getElementsByTagName("a");
            var href = itemA[0].getAttribute("href");
            var itemId = href.split("id=")[1];
            localMethod.getJsonData(itemId+"###"+title[0].innerText);
        }
    } else {
        localMethod.errorOccur();
    }
}


function clickElementsByClassNamePosition(className,position){
    localMethod.JI_LOG("!!!!!!");
    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    if(element.length>0){
        localMethod.JI_LOG(className+":"+element[position].value);
        element[position].click();
        localMethod.afterClick();
    }
}


function clickChildElementByTagName(parentName,parentPosition,childTagName,position){
    localMethod.JI_LOG("!!!!!!");
    var parentElement = document.getElementsByClassName(parentName);
    localMethod.JI_LOG(parentName+":"+parentElement.length);
    if(parentElement.length>0){
        var childElement = parentElement[parentPosition].getElementsByTagName(childTagName);
        localMethod.JI_LOG(childTagName+":"+childElement[position].value);
        childElement[position].click();
        localMethod.getJsonData("clickChildElementByTagName");
    }
}



function timePublish(parentName,parentPosition,childTagName,position){
    localMethod.JI_LOG("!!!!!!");
    var parentElement = document.getElementsByClassName(parentName);
    localMethod.JI_LOG(parentName+":"+parentElement.length);
    if(parentElement.length>0){
        var childElement = parentElement[parentElement.length-1].getElementsByTagName(childTagName);
        localMethod.JI_LOG(childTagName+":"+childElement[position].value);
        childElement[position].click();
        localMethod.getJsonData("clickChildElementByTagName");
    }
}


function comfirMobileDetail(){
    localMethod.JI_LOG("!!!!!!");
    var head = document.getElementsByClassName("m-editor-head-left");
    localMethod.JI_LOG("head"+":"+head.length);
    var element = head[0].getElementsByClassName("next-btn next-btn-normal next-btn-medium");
    localMethod.JI_LOG("element"+":"+element.length);
    if(element.length>0){
        element[0].click();
        element[0].focus();
        localMethod.JI_LOG("element"+":"+element[0].value);
        localMethod.afterClick();
    }
}


function shuaishou(){
    var toolItems = document.getElementsByClassName("tool-item");
    localMethod.JI_LOG("toolItems:"+toolItems.length)
    for(var i=0;i<toolItems.length;i++){
        var name = toolItems[i].getElementsByClassName("tool-name");
        if(name.length>0){
            localMethod.JI_LOG(name[0].innerText);
            if(name[0].innerText.indexOf("甩手铺货")!=-1){
                toolItems[i].click();
                break
            }
        }
    }

    var checkbox = document.getElementsByClassName("isv-readme-checkbox-input");
    localMethod.JI_LOG("checkbox:"+checkbox.length)
    checkbox[0].click();
    localMethod.afterClick();
}

function shuaishouComfirm(){
    var confirm = document.getElementsByClassName("btn confirm isv-confirm");
    localMethod.JI_LOG("confirm:"+confirm.length)
    confirm[0].click();
}



function clearSku(position,inputvalue){
    editSKu(position,inputvalue);
//    var skuCheckbox = document.getElementsByClassName("sell-color-checkbox-check-wrap");
//        localMethod.JI_LOG("skuCheckbox:"+skuCheckbox.length);
//    if(skuCheckbox.length>1){
//            localMethod.JI_LOG("skuCheckbox!:"+skuCheckbox.length);
//         var cleanrBtn = skuCheckbox[0].getElementsByClassName("next-icon next-icon-select next-icon-xs");
//        cleanrBtn[0].click();
//                localMethod.JI_LOG("cleanrBtn!!:"+cleanrBtn.length);
//        clearSku(position,inputvalue);
//    } else {
//        editSKu(position,inputvalue);
//    }
}


function editSKu(position,inputvalue,className){
    localMethod.JI_LOG("!!!!!!");

    var skuSpans = document.getElementsByClassName(className);
//    var skuSpans = document.getElementsByClassName("next-input next-input-single next-input-medium clear color-dropdown-input");
    localMethod.JI_LOG("skuSpans:"+skuSpans.length);
    var skuInputs = skuSpans[position].getElementsByTagName("input");
    localMethod.JI_LOG("skuInputs:"+skuInputs.length);
    skuInputs[0].click();
    skuInputs[0].focus();
    skuInputs[0].value = inputvalue;
    var clears = skuSpans[position].getElementsByClassName("next-icon next-icon-delete-filling next-icon-medium");
    localMethod.JI_LOG("clears:"+clears.length);
    if(clears.length>0){
        clears[0].click();
    }
    localMethod.getJsonData(inputvalue);

}



function editSKuPic(position){
    localMethod.JI_LOG("!!!!!!");
    var images = document.getElementsByClassName("color-image-upload");
    var imageupload = images[position].getElementsByClassName("remove-link");
    localMethod.JI_LOG("imageupload:"+imageupload.length);
    if(imageupload.length>0){
        imageupload[0].click();
        editSKuPic(position);
    } else {
        clickElementsByClassName("next-btn next-btn-normal next-btn-medium upload-img-btn");
    }

}

function resetSkuPic(position){
    localMethod.JI_LOG("resetSku!!!!!!");
    var imageuploads = document.getElementsByClassName("color-image-upload");
    var imageupload = imageuploads[position].getElementsByClassName("remove-link");
    if(imageupload.length>0){
        imageupload[0].click();
    }
    localMethod.JI_LOG("resetSku!!!!!!"+position);
    position = position +1;
    localMethod.JI_LOG("resetSku!!!!!!"+position);
        localMethod.JI_LOG("imageuploads!!!!!!"+imageuploads.length);
    localMethod.JI_LOG("resetSku!!!!!!"+(position<imageuploads.length));
    if(position<imageuploads.length){
        localMethod.JI_LOG("resetSku!!!!!!resetSku");
        resetSkuPic(position);
    } else {
        resetSkuName();
    }

}


function resetSkuName(){
//    next-icon next-icon-delete-filling next-icon-medium
    localMethod.JI_LOG("resetSkuName!!!!!!");
    var skuClear = document.getElementsByClassName("next-icon next-icon-delete-filling next-icon-medium");
    if(skuClear.length>0){
        skuClear[0].click();
        resetSkuName();
    } else {
        localMethod.getJsonData("reset complete!!!");
    }
}


function getPicFromSpaces(){
    var overlay = document.getElementsByClassName("next-overlay-inner sell-o-simple-dialog next-position-cc");
        localMethod.JI_LOG("overlay!!!!!!"+overlay.length);
    var element1 = overlay[0].getElementsByClassName("sell-o-simple-dialog-inner o-sell-media-dialog");
        localMethod.JI_LOG("element1!!!!!!"+element1.length);
    var element2 = element1[0].getElementsByClassName("sell-o-simple-dialog-body next-dialog-body");
        localMethod.JI_LOG("element2!!!!!!"+element2.length);
    var element = element2[0].getElementsByClassName("media-img-plug");
        localMethod.JI_LOG("element!!!!!!"+element.length);
//    var element = element3[0].getElementsByClassName("no-js");
    var iframe = element[0].getElementsByTagName("iframe");
        localMethod.JI_LOG("iframe!!!!!!"+iframe.length);
        localMethod.picSpaceInputClick();
//        localMethod.JI_LOG("iframe!!!!!!"+iframe.length);
//        localMethod.JI_LOG("iframe!!!!!!"+iframe.length);
//        iframe.focus();


}




function showKeyboardAfterClick(className){
    localMethod.JI_LOG("!!!!!!");
    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    if(element.length>0){
        localMethod.JI_LOG(className+":"+element[0].value);
        element[0].click();
        element[0].focus();
    }
    localMethod.showKeyboardB4Input();
}

function showKeyboardInput(className,inputvalue){
    localMethod.JI_LOG("!!!!!!");
    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    if(element.length>0){
        localMethod.JI_LOG(className+":"+element[0].value);
        element[0].click();
        element[0].focus();
    }
    localMethod.inputContent(inputvalue);
}


function getMoblieDetail(){
    var element = document.getElementsByClassName("m-editor-head-left");
    localMethod.JI_LOG("!!!!!!"+element.length);
    if(element.length>0){
        localMethod.JI_LOG("element"+":"+element[0].value);
        element[0].click();
        element[0].focus();
    }
    localMethod.getJsonData("getMoblieDetail");
}


function setInputValue(className,inputvalue){

    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    if(element.length>0){
        var tag = element[0].getElementsByTagName("input");
        localMethod.JI_LOG("tag:"+tag.length);
        if(tag.length>0){
            tag[0].click();
            tag[0].focus();
            tag[0].value=inputvalue;
        }
    }
    localMethod.showKeyboardB4Input();
}

function setTitle(className,inputvalue){

    var element = document.getElementsByClassName(className);
    localMethod.JI_LOG(className+":"+element.length);
    if(element.length>0){
        var tag = element[0].getElementsByTagName("input");
        localMethod.JI_LOG("tag:"+tag.length);
        if(tag.length>0){
            tag[0].click();
            tag[0].focus();
//            tag[0].value=inputvalue;
        }
    }
    localMethod.clearTitle(inputvalue);
}

function checkTimingData(){
    var showTime = document.getElementsByClassName("next-date-picker next-date-picker-medium next-date-picker-show-time");
    localMethod.JI_LOG("showTime:"+showTime.length);
    if(showTime.length>0){
        var timeInput = showTime[0].getElementsByTagName("input");
        localMethod.JI_LOG("timeInput:"+showTime.length);
        if(timeInput.length>0){
            var timeValue = timeInput[0].getAttribute("value");
            localMethod.JI_LOG(timeValue);
            localMethod.getJsonData(timeValue);
        }
    } else {
        localMethod.errorOccur();
    }
}

function getSkuPrices(position,limitMoney){
    var skuMoney = document.getElementsByClassName("sell-sku-cell sell-sku-cell-money");
    localMethod.JI_LOG("skuMoney:"+skuMoney.length);
    var money = skuMoney[position].getElementsByTagName("input");
    localMethod.JI_LOG("money:"+money.length);
    var originPrice = money[0].getAttribute("value");
    if(parseFloat(limitMoney)>parseFloat(originPrice)){
        localMethod.JI_LOG("limitMoney>originPrice");
        localMethod.JI_LOG("limitMoney:"+limitMoney);
        localMethod.JI_LOG("originPrice:"+originPrice);
        setCheckSkuPrice(position,limitMoney,skuMoney.length);
    } else {
        localMethod.JI_LOG("limitMoney:"+limitMoney);
        localMethod.JI_LOG("originPrice:"+originPrice);
        position++;
        if(position<skuMoney.length){
            getSkuPrices(position,limitMoney);
        } else {
            localMethod.getJsonData("价格完成");

        }
    }
}

function setCheckSkuPrice(position,price,length){
//    sell-sku-table-wrap
    var table = document.getElementsByClassName("sell-sku-table-wrap");
    localMethod.JI_LOG("table:"+table.length);
    var skuRow = table[0].getElementsByClassName("sku-table-row");
    localMethod.JI_LOG("skuRow:"+skuRow.length);
    var skuPrice = skuRow[position].getElementsByClassName("sell-sku-cell sell-sku-cell-money");
    localMethod.JI_LOG("skuPrice:"+skuPrice.length);
    var inputPrice = skuPrice[0].getElementsByTagName("input");
    localMethod.JI_LOG("inputPrice:"+inputPrice.length);
    inputPrice[0].click();
    inputPrice[0].focus();
    inputPrice[0].value = price;
    localMethod.inputContentWithoutFinish(price);
    setTimeout(function(){
        position++;
        if(position<length){
            getSkuPrices(position,price);
        } else {
            localMethod.getJsonData("价格完成");
        }
    },2000);
}

//
function setShuaiShouTitle(){

    localMethod.JI_LOG("title:～～～");
    var title = document.getElementsByClassName("ocms-component-1688-pc-tao-shuaishou-embed-1-0-6");
    localMethod.JI_LOG("title:"+title.length);
//    localMethod.JI_LOG("title:"+title[1].innerText);

    var titlewordscount = document.getElementsByClassName("col-md-1");
    localMethod.JI_LOG("titlewordscount:"+titlewordscount.length)
    localMethod.JI_LOG("titlewordscount:"+titlewordscount[3].innerText)

//    if(title.length>0){
//        var tag = title[0].getElementsByTagName("input");
//        localMethod.JI_LOG("tag:"+tag.length);
//        if(tag.length>0){
//            tag[0].click();
//            tag[0].focus();
////            tag[0].value=inputvalue;
//        }
//    }
    localMethod.clearTitle(inputvalue);
}





function getAliDetailTitle(){
    localMethod.JI_LOG("getAliDetailTitle");
    var titlMod = document.getElementsByClassName("d-title");
//    localMethod.JI_LOG("titlMod:"+titlMod.length);
//    var title = titlMod[0].getElementsByTagName("h1");
    localMethod.JI_LOG("title:"+titlMod.length);
    localMethod.JI_LOG("innerText:"+titlMod[0].innerText);
    localMethod.JI_LOG("value:"+titlMod[0].value);
    localMethod.getJsonData(titlMod[0].innerText);
}


function getAliDetailInfo(){

    localMethod.JI_LOG("getSrcAttrByTagName");
    var sku = document.getElementsByClassName("obj-sku");
    var pricesTag = document.getElementsByClassName("price-text price-num");
    if(pricesTag.length>1){
        localMethod.getLimitPrice(pricesTag[1].innerText);
    } else {
        localMethod.getLimitPrice("-1");
    }
    if(sku.length>0){
        var expand = sku[0].getElementsByClassName("obj-expand");
        var style = expand[0].getAttribute("style");
        localMethod.JI_LOG("style:"+style);
        if(null!=style){
            if(style.length>0){
                if(style.indexOf("display")!=-1){
                    if(expand.length>0){
                        expand[0].click;
                        localMethod.JI_LOG("sku:click");
                    }
                }
            }
        }

        var style1 = document.getElementsByClassName("unit-detail-spec-operator");
        localMethod.JI_LOG("style1:"+style1.length);
        if(style1.length>0){
            getAliDetailInfoJunma(style1);
        } else {
            getAliDetailInfoNormal();
        }

    } else {
        getAliDetailInfoNoneSku();
    }


}


function getAliLimitPrice(){
    var pricesTag = document.getElementsByClassName("price-text price-num");
    if(pricesTag.length>1){
        localMethod.getLimitPrice(pricesTag[1].innerText);
    } else {
        localMethod.getLimitPrice("-1");
    }
}



function getAliDetailInfoNoneSku(){

    localMethod.JI_LOG("getAliDetailInfoNoneSku");
    var pricesTag = document.getElementsByClassName("price-text price-num");
    var urls = "~~";
    var shopName = "默认";
    var shopPrice = pricesTag[0].innerText;
    var shopCount = "99";
    localMethod.get1688details(urls,shopName,shopPrice,shopCount);
}


function getAliDetailInfoJunma(style1){
    localMethod.JI_LOG("getAliDetailInfoJunma");
    var element = document.getElementsByClassName("table-sku");
    localMethod.JI_LOG("element:"+element.length);
    var priceTag = element[0].getElementsByClassName("price")[0].getElementsByClassName("value")[0].innerText;
    var countTag = element[0].getElementsByClassName("count")[0].getElementsByClassName("value")[0].innerText;
    localMethod.JI_LOG("priceTag:"+priceTag);
    localMethod.JI_LOG("countTag:"+countTag);
    var urls = "";
    var shopName = "";
    var shopPrice = "";
    var shopCount = "";
    localMethod.JI_LOG("style1:"+style1.length);
    for(var i=0;i<style1.length;i++){
        var img = style1[i].getElementsByTagName("img");
        if(img.length>0){
            if(urls.length==0){
                urls = img[0].getAttribute("src");
                shopName = img[0].getAttribute("alt");
            } else {
                urls = urls + "###" + img[0].getAttribute("src");
                shopName = shopName + "###" + img[0].getAttribute("alt");
            }
        } else {
            var nameTag = style1[i].getElementsByClassName("text text-single-line");
            if(nameTag.length>0){
                if(urls.length==0){
                    urls = "~~";
                    shopName = nameTag[0].innerText;
                } else {
                    urls = urls + "###" + "~~";
                    shopName = shopName + "###" + nameTag[0].innerText;
                }
            }
        }
//        localMethod.JI_LOG("shopName:"+shopName);
//        localMethod.JI_LOG("urls:"+urls);
        if(shopPrice.length==0){
            shopPrice = priceTag;
        } else {
            shopPrice = shopPrice + "###" + priceTag;
        }
        if(shopCount.length==0){
            shopCount = countTag;
        } else {
            shopCount = shopCount + "###" + countTag;
        }
    }


    localMethod.get1688details(urls,shopName,shopPrice,shopCount);

}


function getAliDetailInfoNormal(){

    var element = document.getElementsByClassName("table-sku");
    localMethod.JI_LOG("table-sku"+":"+element.length);
    var urls = "";
    var shopName = "";
    var shopPrice = "";
    var shopCount = "";
    if(element.length>0){
        var tag = element[0].getElementsByClassName("name");
        var priceTag = element[0].getElementsByClassName("price");
        var countTag = element[0].getElementsByClassName("count");
        localMethod.JI_LOG("tag:"+tag.length);
        localMethod.JI_LOG("priceTag:"+priceTag.length);
        if(tag.length>0){

            for(var j=0;j<tag.length;j++){
                var imgTag = tag[j].getElementsByTagName("img");
                if(imgTag.length>0){
                    if(urls.length==0){
                        urls = imgTag[0].getAttribute("src");
                        shopName = imgTag[0].getAttribute("alt");
                    } else {
                        urls = urls + "###" + imgTag[0].getAttribute("src");
                        shopName = shopName + "###" + imgTag[0].getAttribute("alt");
                    }

                    localMethod.JI_LOG(tag[j].getAttribute("src"));
                    localMethod.JI_LOG(tag[j].getAttribute("alt"));
                } else {

                    if(urls.length==0){
                        urls = "~~";
                        shopName = tag[j].innerText;
                    } else {
                        urls = urls + "###" +"~~";
                        shopName = shopName + "###" +tag[j].innerText;
                    }
                }
            }
            for(var j=0;j<priceTag.length;j++){
                if(shopPrice.length==0){
                    shopPrice = priceTag[j].getElementsByClassName("value")[0].innerText;
                } else {
                    shopPrice = shopPrice + "###" + priceTag[j].getElementsByClassName("value")[0].innerText;
                }
                localMethod.JI_LOG(priceTag[j].getElementsByClassName("value")[0].innerText);
            }
            for(var j=0;j<countTag.length;j++){
                if(shopCount.length==0){
                    shopCount = countTag[j].getElementsByClassName("value")[0].innerText;
                } else {
                    shopCount = shopCount + "###" + countTag[j].getElementsByClassName("value")[0].innerText;
                }
                localMethod.JI_LOG(priceTag[j].getElementsByClassName("value")[0].innerText);
            }
            localMethod.get1688details(urls,shopName,shopPrice,shopCount);
        } else {
            localMethod.errorOccur();
        }
    } else {
        localMethod.errorOccur();
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


function clickAttrElementsByClassNameAndInnerText(value){
    var item = document.getElementsByClassName("sell-catProp-item");
    var unfind = true;
    localMethod.JI_LOG("item:"+item.length);
    for(var i=0;i<item.length;i++){
        var label = item[i].getElementsByClassName("label required");
        localMethod.JI_LOG("label:"+label.length);
        if(label.length>0){
            localMethod.JI_LOG("label:"+label[0].innerText);
            if(label[0].innerText.indexOf(value)!=-1){
                var nextselect = item[i].getElementsByClassName("next-select-inner");
                localMethod.JI_LOG("nextselect:"+nextselect.length);
                if(nextselect.length>0){
                    if(nextselect[0].innerText.indexOf("请选择")!=-1||value.indexOf("品牌")!=-1||value.indexOf("产地")!=-1){
                        nextselect[0].click();
                        unfind = false;
                        break;
                    }
                }
            }
        }
    }
    if(unfind){
        localMethod.errorOccur();
    } else {
        localMethod.afterClick();
    }
}

function setSkuPrice(position,price){
//    sell-sku-table-wrap
    var table = document.getElementsByClassName("sell-sku-table-wrap");
    localMethod.JI_LOG("table:"+table.length);
    var skuRow = table[0].getElementsByClassName("sku-table-row");
    localMethod.JI_LOG("skuRow:"+skuRow.length);
    var skuPrice = skuRow[position].getElementsByClassName("sell-sku-cell sell-sku-cell-money");
    localMethod.JI_LOG("skuPrice:"+skuPrice.length);
    var inputPrice = skuPrice[0].getElementsByTagName("input");
    localMethod.JI_LOG("inputPrice:"+inputPrice.length);
    inputPrice[0].click();
    inputPrice[0].focus();
//    inputPrice[0].value = price;
    localMethod.getJsonData(price);
}


function setSkuCount(position,count){
//    sell-sku-table-wrap
    var table = document.getElementsByClassName("sell-sku-table-wrap");
    localMethod.JI_LOG("table:"+table.length);
    var skuRow = table[0].getElementsByClassName("sku-table-row");
    localMethod.JI_LOG("skuRow:"+skuRow.length);
    var skuPrice = skuRow[position].getElementsByClassName("sell-sku-cell sell-sku-cell-positiveNumber");
    localMethod.JI_LOG("skuPrice:"+skuPrice.length);
    var inputPrice = skuPrice[0].getElementsByTagName("input");
    localMethod.JI_LOG("inputPrice:"+inputPrice.length);
    inputPrice[0].click();
    inputPrice[0].focus();
//    inputPrice[0].value = price;
    localMethod.getJsonData(count);
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
