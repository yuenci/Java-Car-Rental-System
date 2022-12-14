var currentMonth = new Date().getMonth();
monthList = [];
monthName = {
    0: 'Jan',
    1: 'Feb',
    2: 'Mar',
    3: 'Apr',
    4: 'May',
    5: 'Jun',
    6: 'Jul',
    7: 'Aug',
    8: 'Sept',
    9: 'Oct',
    10: 'Nov',
    11: 'Dec'
};
for (i = 0; i < 12; i++) {
    index = currentMonth - i;

    if (index < 0) {
        index = 12 + index;
    }

    monthList.unshift(monthName[index])
}


function barChart(barData) {
    var dom = document.getElementById('bar-chart');
    var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
    });
    var app = {};

    var option;

    option = {
        grid: {
            left: '5%',
            right: '5%',
            top: '15%',
            bottom: '8%',
        },
        title: {
            text: 'Monthly Income',
            left: 'center'
        },
        xAxis: {
            type: 'category',
            data: monthList,

        },
        yAxis: {
            type: 'value',
            // show: false

        },
        series: [
            {
                data: barData,
                type: 'bar',
                itemStyle: {
                    color: '#389dff'
                }
            }
        ],
        tooltip: {
            trigger: 'axis'
        },
    };

    if (option && typeof option === 'object') {
        myChart.setOption(option);
    }

    window.addEventListener('resize', myChart.resize);
}
barChart(barData);


function lineChart(lineData) {

    var dom = document.getElementById('line-chart');
    var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
    });
    var app = {};

    var option;

    option = {

        tooltip: {
            trigger: 'axis'
        },
        grid: {
            left: '5%',
            right: '5%',
            top: '15%',
            bottom: '8%',
        },
        title: {
            text: 'Payment Time',
            left: 'center'
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24']

        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                data: lineData,
                type: 'line',
                smooth: true,
                areaStyle: {},
                areaStyle: {
                    color: '#CAE943'
                },
                // line style
                lineStyle: {
                    color: '#7EB712'
                },
                itemStyle: {
                    color: '#7EB712'
                }
            }
        ]
    };

    if (option && typeof option === 'object') {
        myChart.setOption(option);
    }

    window.addEventListener('resize', myChart.resize);
}
lineChart(lineData)

function paymentChart() {
    var dom = document.getElementById('payment-chart');
    var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
    });
    var app = {};

    var option;

    option = {
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            left: '0%',
            right: '0%',
            top: '0%',
            bottom: '0%',
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: monthList,
            show: false
        },
        yAxis: {
            type: 'value',
            show: false
        },
        // hide item

        series: [
            {
                data: [820, 932, 901, 934, 1290, 1330, 820, 932, 901, 934, 1290, 1330],
                type: 'line',
                symbol: 'none',
                smooth: true,
                areaStyle: {},
                areaStyle: {
                    color: '#f7d1f7'
                },
                // line style
                lineStyle: {
                    color: '#de35de'
                },
                itemStyle: {
                    symbolSize: 0,
                }

            }
        ]
    };

    if (option && typeof option === 'object') {
        myChart.setOption(option);
    }

    window.addEventListener('resize', myChart.resize);
}
paymentChart();

function avarageChart() {
    var dom = document.getElementById('average-chart');
    var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
    });
    var app = {};

    var option;

    option = {
        grid: {
            left: '0%',
            right: '0%',
            top: '0%',
            bottom: '0%',
        },

        xAxis: {
            type: 'category',
            data: monthList,
            show: false
        },
        yAxis: {
            type: 'value',
            show: false

        },
        series: [
            {
                data: [120, 200, 150, 80, 70, 110, 130, 120, 200, 150, 80, 70, 110, 130],
                type: 'bar',
                itemStyle: {
                    color: '#389dff'
                }
            }
        ],
        tooltip: {
            trigger: 'axis'
        },
    };

    if (option && typeof option === 'object') {
        myChart.setOption(option);
    }

    window.addEventListener('resize', myChart.resize);
}
avarageChart();

function maxChart() {
    var dom = document.getElementById('max-chart');
    var myChart = echarts.init(dom, null, {
        renderer: 'canvas',
        useDirtyRect: false
    });
    var app = {};

    var option;

    option = {
        tooltip: {
            trigger: 'axis'
        },
        grid: {
            left: '0%',
            right: '0%',
            top: '0%',
            bottom: '0%',
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: monthList,
            show: false
        },
        yAxis: {
            type: 'value',
            show: false
        },
        series: [
            {
                data: [820, 932, 901, 934, 1290, 1330, 820, 932, 901, 934, 1290, 1330],
                type: 'line',
                symbol: 'none',
                smooth: true,
                areaStyle: {},
                areaStyle: {
                    color: '#d6f4f4'
                },
                // line style
                lineStyle: {
                    color: '#97e4e4'
                },
                itemStyle: {
                    color: '#97e4e4'
                }
            }
        ]
    };

    if (option && typeof option === 'object') {
        myChart.setOption(option);
    }

    window.addEventListener('resize', myChart.resize);
}
maxChart();

function addHearderData(monthOrderData) {
    let hd1 = document.getElementById('header-data-1');
    let hd2 = document.getElementById('header-data-2');
    let hd3 = document.getElementById('header-data-3');
    let hd4 = document.getElementById('header-data-4');

    hd1.innerHTML = "RM " + monthOrderData[0];
    hd2.innerHTML = "RM " + monthOrderData[1];
    hd3.innerHTML = "RM " + monthOrderData[2];
    hd4.innerHTML = "RM " + monthOrderData[3];
}

/// init data 
addHearderData(monthOrderData);