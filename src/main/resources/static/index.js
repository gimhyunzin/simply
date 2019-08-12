(function() {

    gridLinks();

    document.getElementById("convertButton").addEventListener("click", convertLink);
    document.getElementById("copyButton").addEventListener("click", copyLink);
    document.getElementById("fromData").addEventListener("keyup", clearData);

    /**
     * 데이터 비우기
     */
    function clearData() {
        document.getElementById("toData").value = "";
    }

    /**
     * 링크 복사
     */
    function copyLink() {
        var toData = document.getElementById("toData");
        if (toData.value == "") {
            alert("복사할 결과가 존재하지 않습니다.");
            return false;
        }

        toData.select();
        document.execCommand('copy');
        alert("복사가 완료되었습니다.");
    }

    /**
     * 링크 변환
     */
    function convertLink() {
        var toData = document.getElementById("toData");
        var fromData = document.getElementById("fromData");

        if (fromData.value == "") {
            alert("변환할 URL 을 입력해주세요.");
            fromData.focus();
            return false;
        }

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "simply/convert", true);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                var response = JSON.parse(xhr.responseText);

                if (xhr.status !== 200) {
                    toData.value = "";

                    alert(response.message);
                    fromData.focus();
                    return;
                }

                toData.value = response.simplyUrl || "";

                gridLinks(); // 다시 그리기
            }
        };

        xhr.send(JSON.stringify({url: fromData.value}));
    }

    /**
     * 링크 목록 그리기
     */
    function gridLinks() {
        var linksArea = document.getElementById("linksArea");

        var xhr = new XMLHttpRequest();
        xhr.open("GET", "simply/links", true);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                var response = JSON.parse(xhr.responseText);

                if (xhr.status !== 200) {
                    linksArea.innerHTML = "<tr><td colspan='3' class='al-center'>조회 결과가 존재하지 않습니다.</td></tr>";
                    alert(response.message);
                    return;
                }

                if (0 === response.length) {
                    linksArea.innerHTML = "<tr><td colspan='3' class='al-center'>조회 결과가 존재하지 않습니다.</td></tr>";
                    return;
                }

                var links = "";
                for (var idx in response) {
                    if (!response.hasOwnProperty(idx)) continue;
                    var link = response[idx] || {};
                    var originUrl = link.originUrl || "";
                    var simplyUrl = link.simplyUrl || "";

                    links += "<tr>";
                    links += "<td>" + (response.length - Number(idx)) + "</td>";
                    links += "<td><a href='" + originUrl + "'>" + originUrl + "</a></td>";
                    links += "<td><a href='" + simplyUrl + "'>" + simplyUrl + "</a></td>";
                    links += "</tr>";
                }

                linksArea.innerHTML = links;
            }
        };

        xhr.send();
    }

})();