
onmessage=function (obj){
    var xhr = new XMLHttpRequest();
    var name_new = obj.data;
    xhr.open('POST','http://localhost:8080/phoneBook/updateAllRecords?name='+name, true);
    xhr.send();
    if (xhr.status != 200) {
        postMessage(JSON.stringify(name_new));
    } else {
        postMessage(JSON.stringify(name_new));
    }
}