var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name--1146707516",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "225000",
        "ok": "45190",
        "ko": "179810"
    },
    "minResponseTime": {
        "total": "1",
        "ok": "1",
        "ko": "10"
    },
    "maxResponseTime": {
        "total": "91751",
        "ok": "50094",
        "ko": "91751"
    },
    "meanResponseTime": {
        "total": "34558",
        "ok": "14532",
        "ko": "39591"
    },
    "standardDeviation": {
        "total": "23892",
        "ok": "14722",
        "ko": "23102"
    },
    "percentiles1": {
        "total": "31103",
        "ok": "11100",
        "ko": "38299"
    },
    "percentiles2": {
        "total": "51972",
        "ok": "25094",
        "ko": "58331"
    },
    "percentiles3": {
        "total": "77330",
        "ok": "42638",
        "ko": "78888"
    },
    "percentiles4": {
        "total": "84648",
        "ok": "46409",
        "ko": "85167"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 16017,
    "percentage": 7.118666666666666
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 41,
    "percentage": 0.01822222222222222
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 29132,
    "percentage": 12.947555555555557
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 179810,
    "percentage": 79.91555555555556
},
    "meanNumberOfRequestsPerSecond": {
        "total": "326.09",
        "ok": "65.49",
        "ko": "260.59"
    }
},
contents: {
"req_post--create-to--1559986832": {
        type: "REQUEST",
        name: "POST [create todo] /todos",
path: "POST [create todo] /todos",
pathFormatted: "req_post--create-to--1559986832",
stats: {
    "name": "POST [create todo] /todos",
    "numberOfRequests": {
        "total": "225000",
        "ok": "45190",
        "ko": "179810"
    },
    "minResponseTime": {
        "total": "1",
        "ok": "1",
        "ko": "10"
    },
    "maxResponseTime": {
        "total": "91751",
        "ok": "50094",
        "ko": "91751"
    },
    "meanResponseTime": {
        "total": "34558",
        "ok": "14532",
        "ko": "39591"
    },
    "standardDeviation": {
        "total": "23892",
        "ok": "14722",
        "ko": "23102"
    },
    "percentiles1": {
        "total": "31102",
        "ok": "11060",
        "ko": "38314"
    },
    "percentiles2": {
        "total": "51960",
        "ok": "25075",
        "ko": "58369"
    },
    "percentiles3": {
        "total": "77333",
        "ok": "42638",
        "ko": "78894"
    },
    "percentiles4": {
        "total": "84633",
        "ok": "46409",
        "ko": "85167"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 16017,
    "percentage": 7.118666666666666
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 41,
    "percentage": 0.01822222222222222
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 29132,
    "percentage": 12.947555555555557
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 179810,
    "percentage": 79.91555555555556
},
    "meanNumberOfRequestsPerSecond": {
        "total": "326.09",
        "ok": "65.49",
        "ko": "260.59"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
